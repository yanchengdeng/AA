package apartment.wisdom.com.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import apartment.wisdom.com.activities.DoCommendActivity;
import apartment.wisdom.com.activities.HotelOrderDetailActivity;
import apartment.wisdom.com.activities.HotelOrderListActivity;
import apartment.wisdom.com.adapters.HotelOrderListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CancleOrderInfo;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.beans.HotelOrderList;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.DoCommendAlreadyEvnet;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邓言诚  Create at : 17/8/16  00:58
 * Email: yanchengdeng@gmail.com
 * Describle: 酒店订单
 */
public class HotelOrdersFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private HotelOrderListAdapter hotelOrderListAdapter;

    private List<HotelOrderItem> HotelOrderItems = new ArrayList<>();
    private int pageNum = 1;
    private View noDateView;
    private String orderType;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        HotelOrderItems = new ArrayList<>();
        orderType = getArguments().getString(Constants.PASS_STRING);
        hotelOrderListAdapter = new HotelOrderListAdapter(getActivity(), R.layout.adapter_hotel_order_item, HotelOrderItems);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(hotelOrderListAdapter);
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        EventBus.getDefault().register(this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                hotelOrderListAdapter.getData().clear();
                getOrders();
            }
        });


        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无订单");
        hotelOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PASS_STRING, hotelOrderListAdapter.getData().get(position).orderNo);
                ((BaseActivity) getActivity()).openActivity(HotelOrderDetailActivity.class, bundle);
            }
        });

        hotelOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.bt_order_pre) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.PASS_OBJECT, HotelOrderItems.get(position));
//                    ((BaseActivity) getActivity()).openActivity(HotelDetailActivity.class,bundle);
                } else if (view.getId() == R.id.bt_order_delete) {
                    adapter.remove(position);
                    adapter.notifyItemRemoved(position);
                } else if (view.getId() == R.id.bt_do_comment) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.PASS_OBJECT, hotelOrderListAdapter.getData().get(position));
                    ((BaseActivity) getActivity()).openActivity(DoCommendActivity.class, bundle);
                } else if (view.getId() == R.id.btn_cancle_order) {
                    getCancleInfo(position);
//                    showCancleDialog(position);
                }
            }
        });

        getOrders();
    }


    private void getCancleInfo(final  int canclePostiont) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", HotelOrderItems.get(canclePostiont).orderNo);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DoCommendAlreadyEvnet event) {
        pageNum = 1;
        hotelOrderListAdapter.getData().clear();
        getOrders();

    }

    private void showCancleDialog(String msg,final int canclePostiont) {
        final NormalDialog dialog = new NormalDialog(getActivity());
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#ffffff"))//
                .cornerRadius(5)//
                .content(msg)//
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

    //取消订单
    private void doCancleOrder(final int canclePostiont) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", HotelOrderItems.get(canclePostiont).orderNo);
//        data.put("refundReason", getActivity().getString(R.string.cancle_reason));
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "confirmCancelOrder"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        hotelOrderListAdapter.notifyItemRemoved(canclePostiont);
                        HotelOrderItems.remove(canclePostiont);
                        if (HotelOrderItems.isEmpty()) {
                            hotelOrderListAdapter.setNewData(null);
                            hotelOrderListAdapter.setEmptyView(noDateView);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }


    private void getOrders() {
        if (pageNum == 1)
            ((HotelOrderListActivity) getActivity()).mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        data.put("pageNum", pageNum);
        data.put("pageSize", Constants.PAGE_SIZE);
        data.put("orderType", orderType);
        OkGo.<AAResponse<HotelOrderList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "storeOrder"))
                .execute(new NewsCallback<AAResponse<HotelOrderList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HotelOrderList>> response) {
                        LogUtils.w("dyc", response.body());
                        ((HotelOrderListActivity) getActivity()).mSVProgressHUD.dismiss();
                        swipeLayout.setRefreshing(false);
                        hotelOrderListAdapter.loadMoreComplete();
                        HotelOrderList hotelOrderList = response.body().data;
                        if (hotelOrderList != null && hotelOrderList.orderList.size() > 0) {
                            if (pageNum == 1) {
                                hotelOrderListAdapter.setNewData(hotelOrderList.orderList);
                            } else {
                                hotelOrderListAdapter.addData(hotelOrderList.orderList);
                            }
                            pageNum++;
                        } else {
                            if (hotelOrderListAdapter.getData().isEmpty()) {
                                hotelOrderListAdapter.setNewData(null);
                                hotelOrderListAdapter.setEmptyView(noDateView);
                            }
                            hotelOrderListAdapter.loadMoreEnd();
                        }

                        HotelOrderItems = hotelOrderListAdapter.getData();

                    }

                    @Override
                    public void onError(Response<AAResponse<HotelOrderList>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        if (hotelOrderListAdapter.getData().isEmpty()) {
                            hotelOrderListAdapter.setNewData(null);
                            hotelOrderListAdapter.setEmptyView(noDateView);
                        }
                        hotelOrderListAdapter.loadMoreEnd();
                        hotelOrderListAdapter.loadMoreComplete();
                        ((HotelOrderListActivity) getActivity()).mSVProgressHUD.dismiss();
                    }
                });


    }

    @Override
    public void onLoadMoreRequested() {
        getOrders();
    }
}
