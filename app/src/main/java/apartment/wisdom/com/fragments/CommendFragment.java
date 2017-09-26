package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.CommendListActivity;
import apartment.wisdom.com.adapters.CommendListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CommendListInfo;
import apartment.wisdom.com.beans.HotelCommendItem;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邓言诚  Create at : 17/8/16  14:47
 * Email: yanchengdeng@gmail.com
 * Describle: 评论
 */
public class CommendFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private CommendListAdapter commendListAdapter;

    private List<HotelCommendItem> hotelCommendItems;
    private Bundle bundle;
    private String storeId;
    private int pageNum = 1;
    private View noDateView;
    private String type;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        hotelCommendItems = new ArrayList<>();

        bundle = getArguments();
        storeId = bundle.getString(Constants.PASS_STRING);
        type = bundle.getString(Constants.SELECT_CARD_TYPE);
        commendListAdapter = new CommendListAdapter(R.layout.item_hotel_comment, hotelCommendItems);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无评价");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(commendListAdapter);

        RefreshSwiperUtils.setRecleColor(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                commendListAdapter.getData().clear();
                getCommends();

            }
        });
        getCommends();

    }

    private void getCommends() {
        if (pageNum == 1)
            ((CommendListActivity) getActivity()).mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("storeId", storeId);
        data.put("pageNum", pageNum);
        data.put("pageSize", Constants.PAGE_SIZE);
        data.put("evaluateType", type);
        OkGo.<AAResponse<CommendListInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "storeEvaluate"))
                .execute(new NewsCallback<AAResponse<CommendListInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<CommendListInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        ((CommendListActivity) getActivity()).mSVProgressHUD.dismiss();
                        swipeLayout.setRefreshing(false);
                        commendListAdapter.loadMoreComplete();
                        CommendListInfo commendListInfo = response.body().data;
                        if (commendListInfo != null && commendListInfo.customerList.size() > 0) {
                            if (pageNum == 1) {
                                commendListAdapter.setNewData(commendListInfo.customerList);
                                if (type == "0") {
                                    setMainData(commendListInfo);
                                }
                            } else {
                                commendListAdapter.addData(commendListInfo.customerList);
                            }
                            pageNum++;
                        } else {
                            if (commendListAdapter.getData().isEmpty()) {
                                commendListAdapter.setNewData(null);
                                commendListAdapter.setEmptyView(noDateView);
                            }
                            commendListAdapter.loadMoreEnd();
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<CommendListInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        ((CommendListActivity) getActivity()).mSVProgressHUD.dismiss();
                        if (commendListAdapter.getData().isEmpty()) {
                            commendListAdapter.setNewData(null);
                            commendListAdapter.setEmptyView(noDateView);
                        }
                        commendListAdapter.loadMoreEnd();
                        commendListAdapter.loadMoreComplete();
                    }
                });


    }

    private void setMainData(CommendListInfo roomListInfo) {
        if (!TextUtils.isEmpty(roomListInfo.storeScore)) {
            ((CommendListActivity) getActivity()).tvScore.setText(roomListInfo.storeScore);
        }

        if (!TextUtils.isEmpty(roomListInfo.storeRoomHealthScore)) {
            ((CommendListActivity) getActivity()).tvScoreWs.setData("卫生", roomListInfo.storeRoomHealthScore);
        }
        if (!TextUtils.isEmpty(roomListInfo.storeEnvironmentScore)) {
            ((CommendListActivity) getActivity()).tvScoreHj.setData("环境", roomListInfo.storeEnvironmentScore);
        }

        if (!TextUtils.isEmpty(roomListInfo.storeHotelScore)) {
            ((CommendListActivity) getActivity()).tvScoreFw.setData("服务", roomListInfo.storeHotelScore);
        }
        if (!TextUtils.isEmpty(roomListInfo.storeDeviceScore)) {
            ((CommendListActivity) getActivity()).tvScoreXjb.setData("性价比", roomListInfo.storeDeviceScore);
        }

        if (!TextUtils.isEmpty(roomListInfo.storePercent)) {
            ((CommendListActivity) getActivity()).tvScoreRate.setText(String.format(getString(R.string.score_rate), new Object[]{roomListInfo.storePercent}));
        }
    }


    @Override
    public void onLoadMoreRequested() {
        getCommends();
    }
}
