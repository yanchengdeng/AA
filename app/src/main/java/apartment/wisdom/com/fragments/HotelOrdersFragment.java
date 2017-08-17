package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.DoCommendActivity;
import apartment.wisdom.com.activities.HotelDetailActivity;
import apartment.wisdom.com.activities.HotelOrderDetailActivity;
import apartment.wisdom.com.adapters.HotelOrderListAdapter;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
*
* Author: 邓言诚  Create at : 17/8/16  00:58
* Email: yanchengdeng@gmail.com
* Describle: 酒店订单
*/
public class HotelOrdersFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener{


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private HotelOrderListAdapter adapter;

    private List<HotelOrderItem> HotelOrderItems;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        HotelOrderItems = new ArrayList<>();
        HotelOrderItems.add(new HotelOrderItem());
        HotelOrderItems.add(new HotelOrderItem());
        HotelOrderItems.add(new HotelOrderItem());
        HotelOrderItems.add(new HotelOrderItem());
        HotelOrderItems.add(new HotelOrderItem());
        adapter = new HotelOrderListAdapter(R.layout.adapter_hotel_order_item, HotelOrderItems);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(adapter);
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((BaseActivity)getActivity()).openActivity(HotelOrderDetailActivity.class);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()== R.id.bt_order_pre) {
                    ((BaseActivity)getActivity()).openActivity(HotelDetailActivity.class);
                }else if (view.getId()==R.id.bt_order_delete){
                    adapter.remove(position);
                    adapter.notifyItemRemoved(position);
                }else if(view.getId()==R.id.bt_do_comment){
                    ((BaseActivity)getActivity()).openActivity(DoCommendActivity.class);
                }
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
