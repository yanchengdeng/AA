package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.BalanceDetailListAdapter;
import apartment.wisdom.com.beans.BalanceDetailInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BalanceDetailFragment extends LazyFragment {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;

    private BalanceDetailListAdapter adapter;

    private List<BalanceDetailInfo> balanceDetailInfos = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());

        balanceDetailInfos.add(new BalanceDetailInfo());
        balanceDetailInfos.add(new BalanceDetailInfo());
        balanceDetailInfos.add(new BalanceDetailInfo());
        balanceDetailInfos.add(new BalanceDetailInfo());

        balanceDetailInfos.add(new BalanceDetailInfo());
        adapter = new BalanceDetailListAdapter(R.layout.adapter_item_balance,balanceDetailInfos);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(adapter);


    }




}
