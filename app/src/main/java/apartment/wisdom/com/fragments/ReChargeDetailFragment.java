package apartment.wisdom.com.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.ChargeDetailListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.RechargeListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

//充值明细
public class ReChargeDetailFragment extends LazyFragment {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private ChargeDetailListAdapter adapter;

    private List<RechargeListInfo.RechargeItem> rechargeItemList = new ArrayList<>();
    private View noDateView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        adapter = new ChargeDetailListAdapter(R.layout.adapter_item_balance, rechargeItemList);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(adapter);
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无充值记录");

        getchargeDetail();


    }

    private void getchargeDetail() {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<RechargeListInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "cardRechargeDetail"))
                .execute(new NewsCallback<AAResponse<RechargeListInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<RechargeListInfo>> response) {
                        RechargeListInfo rechargeListInfo = response.body().data;
                        if (rechargeListInfo != null && rechargeListInfo.rechargeList != null && !rechargeListInfo.rechargeList.isEmpty()) {
                            adapter.setNewData(rechargeListInfo.rechargeList);
                        } else {
                            adapter.setNewData(null);
                            adapter.setEmptyView(noDateView);
                        }

                    }

                    @Override
                    public void onError(Response<AAResponse<RechargeListInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        adapter.setNewData(null);
                        adapter.setEmptyView(noDateView);
                    }
                });
    }
}
