package apartment.wisdom.com.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.HistoryHotelActivity;
import apartment.wisdom.com.activities.HotelOrderDetailActivity;
import apartment.wisdom.com.activities.LoginActivity;
import apartment.wisdom.com.activities.MainActivity;
import apartment.wisdom.com.adapters.AssistantListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CancleOrderInfo;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.beans.HotelOrderList;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginOutSuccessEvent;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author: 邓言诚  Create at : 17/8/7  16:53
 * Email: yanchengdeng@gmail.com
 * Describle: 旅行助手
 */
public class AssistantFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_order_hotel)
    TextView tvOrderHotel;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_trip_history)
    ImageView ivTripHistory;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.iv_book_hotel)
    TextView ivBookHotel;
    @BindView(R.id.tv_history_trip)
    TextView tvHistoryTrip;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.ll_no_trip_container)
    ScrollView llNoTripContainer;

    private AssistantListAdapter assistantListAdapter;

    private List<HotelOrderItem> assistantItemInfoList = new ArrayList<>();
    private View noDateView;
    private int pageNum = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_assistant, container, false);
        ButterKnife.bind(this, view);
        tvTittle.setText(getString(R.string.tab_assistant));

        swipeLayout.setOnRefreshListener(this);
        assistantListAdapter = new AssistantListAdapter(getActivity(), R.layout.item_trip_assistant_card, assistantItemInfoList);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无行程记录");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(assistantListAdapter);
        if (LoginUtils.getLoginStatus()) {
            swipeLayout.setVisibility(View.VISIBLE);
            llNoTripContainer.setVisibility(View.GONE);
            getTripList();
        } else {
            swipeLayout.setVisibility(View.GONE);
            llNoTripContainer.setVisibility(View.VISIBLE);
        }
        EventBus.getDefault().register(this);
        assistantListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ((BaseActivity) getActivity()).openActivity(HotelDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PASS_STRING, assistantListAdapter.getData().get(position).orderNo);
                ((BaseActivity) getActivity()).openActivity(HotelOrderDetailActivity.class, bundle);
            }
        });

        assistantListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_cancle_order) {
                    getCancleInfo(position);
                } else if (view.getId() == R.id.btn_app_open_door) {
                    ToastUtils.showShort(R.string.do_later);
                } else if (view.getId() == R.id.btn_auto_out) {
                    autoCheckout(position);
                }else if (view.getId()==R.id.btn_check_in_code){
                    showCheckInCode(assistantListAdapter.getData().get(position).checkInNo);
                }
            }
        });

        return view;
    }

    //弹出取卡码
    private void showCheckInCode(String checkInNo) {
        final NormalDialog dialog = new NormalDialog(getActivity());
        dialog.content("您的入住码："+checkInNo)//
                .btnNum(1)
                .btnText("知道了")//
                .show();

        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        });
    }

    //自助退房
    private void autoCheckout(final int position) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", assistantListAdapter.getData().get(position).orderNo);
        OkGo.<AAResponse<HotelOrderList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "checkOutRoom"))
                .execute(new NewsCallback<AAResponse<HotelOrderList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HotelOrderList>> response) {
                        assistantListAdapter.remove(position);
                        if (assistantListAdapter.getData().isEmpty()) {
                            swipeLayout.setVisibility(View.GONE);
                            llNoTripContainer.setVisibility(View.VISIBLE);
                        }
                        ((BaseActivity) getActivity()).mSVProgressHUD.showSuccessWithStatus("已自助退房", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    }

                    @Override
                    public void onError(Response<AAResponse<HotelOrderList>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }


    private void getCancleInfo(final  int canclePostiont) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", assistantListAdapter.getData().get(canclePostiont).orderNo);
//        data.put("refundReason", getActivity().getString(R.string.cancle_reason));
        OkGo.<AAResponse<CancleOrderInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "reqCancelOrder"))
                .execute(new NewsCallback<AAResponse<CancleOrderInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<CancleOrderInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        CancleOrderInfo cancleOrderInfo = response.body().data;
                        showCancleDialog(cancleOrderInfo.returnMsg,canclePostiont);
                    }

                    @Override
                    public void onError(Response<AAResponse<CancleOrderInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }


    private void showCancleDialog(String returnMsg, final int canclePostiont) {
        final NormalDialog dialog = new NormalDialog(getActivity());
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#ffffff"))//
                .cornerRadius(5)//
                .content(returnMsg)//
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#222222"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#222222"), Color.parseColor("#ff0000"))//
                .widthScale(0.85f)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        doCancleOrder(canclePostiont);
                    }
                });
    }

    private void getTripList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        data.put("pageNum", pageNum);
        data.put("pageSize", Constants.PAGE_SIZE);
        OkGo.<AAResponse<HotelOrderList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "getCurrTrip"))
                .execute(new NewsCallback<AAResponse<HotelOrderList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HotelOrderList>> response) {
                        LogUtils.w("dyc", response.body());
                        HotelOrderList hotelOrderList = response.body().data;
                        if (hotelOrderList != null) {
                            if (hotelOrderList.orderList.isEmpty()) {
                                assistantListAdapter.setNewData(null);
                                assistantListAdapter.setEmptyView(noDateView);
                                assistantListAdapter.loadMoreComplete();
                                assistantListAdapter.loadMoreEnd(true);
                            } else {
                                if (pageNum == 1) {
                                    assistantListAdapter.setNewData(hotelOrderList.orderList);
                                } else {
                                    assistantListAdapter.addData(hotelOrderList.orderList);
                                }
                                pageNum++;
                            }
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<HotelOrderList>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        assistantListAdapter.setNewData(null);
                        assistantListAdapter.setEmptyView(noDateView);
                    }
                });
    }

    //取消订单
    private void doCancleOrder(final int canclePostiont) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", assistantListAdapter.getData().get(canclePostiont).orderNo);
//        data.put("refundReason", getActivity().getString(R.string.cancle_reason));
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "confirmCancelOrder"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        assistantListAdapter.remove(canclePostiont);
                        if (assistantListAdapter.getData().isEmpty()) {
                            swipeLayout.setVisibility(View.GONE);
                            llNoTripContainer.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof LoginSuccessEvent ||event instanceof PayOrRechargeSuccess) {
            swipeLayout.setVisibility(View.VISIBLE);
            llNoTripContainer.setVisibility(View.GONE);
            pageNum = 1;
            assistantListAdapter.getData().clear();
            getTripList();
        } else if (event instanceof LoginOutSuccessEvent) {
            swipeLayout.setVisibility(View.GONE);
            llNoTripContainer.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.tv_order_hotel, R.id.iv_trip_history, R.id.iv_book_hotel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_book_hotel:
            case R.id.tv_order_hotel:
                if (LoginUtils.getLoginStatus()) {
                    ((MainActivity) getActivity()).viewpager.setCurrentItem(0);
                } else {
                    ((MainActivity) getActivity()).openActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_trip_history:
                if (LoginUtils.getLoginStatus()) {
                    ((BaseActivity) getActivity()).openActivity(HistoryHotelActivity.class);
                } else {
                    ((MainActivity) getActivity()).openActivity(LoginActivity.class);
                }
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        pageNum = 1;
        assistantListAdapter.getData().clear();
        getTripList();
    }

    @Override
    public void onLoadMoreRequested() {
        getTripList();

    }
}
