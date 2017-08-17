package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.TicketListAdapter;
import apartment.wisdom.com.beans.TicketInfo;
import apartment.wisdom.com.commons.Constants;
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

    private List<TicketInfo> hotelCommendItems;


    private Bundle bundle;
    private boolean isEmpty;

    private View noDateView;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView)noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无可用优惠劵");
        bundle = getArguments();
        if (null != bundle) {
            isEmpty = bundle.getBoolean(Constants.PASS_STRING, false);
        }
        hotelCommendItems = new ArrayList<>();
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        adapter = new TicketListAdapter(R.layout.adapter_ticket_item, hotelCommendItems);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(adapter);

        if (isEmpty) {
            adapter.setNewData(null);
            adapter.setEmptyView(noDateView);
        }else{
            adapter.setNewData(hotelCommendItems);
        }
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {
        if (adapter.getData().size() < 10) {
            adapter.loadMoreEnd(false);
            adapter.loadMoreComplete();
        }

    }
}
