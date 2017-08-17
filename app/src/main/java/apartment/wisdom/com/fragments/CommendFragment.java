package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.CommendListAdapter;
import apartment.wisdom.com.beans.HotelCommendItem;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
*
* Author: 邓言诚  Create at : 17/8/16  14:47
* Email: yanchengdeng@gmail.com
* Describle: 评论
*/
public class CommendFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener{


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private CommendListAdapter adapter;

    private List<HotelCommendItem> hotelCommendItems;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        hotelCommendItems = new ArrayList<>();
        hotelCommendItems.add(new HotelCommendItem());
        hotelCommendItems.add(new HotelCommendItem());
        hotelCommendItems.add(new HotelCommendItem());
        hotelCommendItems.add(new HotelCommendItem());
        hotelCommendItems.add(new HotelCommendItem());
        adapter = new CommendListAdapter(R.layout.item_hotel_comment, hotelCommendItems);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(adapter);

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
        if (adapter.getData().size()<10) {
            adapter.loadMoreEnd(false);
            adapter.loadMoreComplete();
        }

    }
}
