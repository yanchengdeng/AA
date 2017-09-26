package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import apartment.wisdom.com.activities.TicketListActivity;
import apartment.wisdom.com.adapters.TicketListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.TicketInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邓言诚  Create at : 17/8/16  14:47
 * Email: yanchengdeng@gmail.com
 * Describle: 优惠劵
 */
public class TicketListFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private TicketListAdapter adapter;

    private List<TicketInfo.TicketInfoItem> hotelCommendItems = new ArrayList<>();


    private Bundle bundle;
    private String ticketType;

    private View noDateView;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无可用优惠劵");
        bundle = getArguments();
        ticketType = bundle.getString(Constants.PASS_STRING);
        adapter = new TicketListAdapter(R.layout.adapter_ticket_item, hotelCommendItems);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(adapter);
        getTicketList();


        RefreshSwiperUtils.setRecleColor(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTicketList();
            }
        });
    }


    //获取优惠劵
    private void getTicketList() {
        ( (TicketListActivity)getActivity()).mSVProgressHUD.showInfoWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<TicketInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "getCoupon"))
                .execute(new NewsCallback<AAResponse<TicketInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<TicketInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        ( (TicketListActivity)getActivity()).mSVProgressHUD.dismiss();
                        swipeLayout.setRefreshing(false);
                        TicketInfo ticketInfo = response.body().data;
                        if (ticketInfo != null && !ticketInfo.couponList.isEmpty()) {
                            if (filterTicketsByType(ticketInfo.couponList).isEmpty()) {
                                adapter.setNewData(null);
                                adapter.setEmptyView(noDateView);
                            } else {
                                adapter.setNewData(filterTicketsByType(ticketInfo.couponList));
                            }
                        } else {
                            adapter.setNewData(null);
                            adapter.setEmptyView(noDateView);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<TicketInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        ( (TicketListActivity)getActivity()).mSVProgressHUD.dismiss();
                        adapter.setNewData(null);
                        adapter.setEmptyView(noDateView);
                    }
                });
    }

    private List<TicketInfo.TicketInfoItem> filterTicketsByType(List<TicketInfo.TicketInfoItem> couponList) {
        List<TicketInfo.TicketInfoItem> infoItems = new ArrayList<>();
        for (TicketInfo.TicketInfoItem item : couponList) {
            if (item.useStatus.equals(ticketType)) {
                infoItems.add(item);
            }
        }
        return infoItems;
    }

    @Override
    public void onLoadMoreRequested() {
        if (adapter.getData().size() < 10) {
            adapter.loadMoreEnd(false);
            adapter.loadMoreComplete();
        }

    }
}
