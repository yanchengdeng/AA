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
import apartment.wisdom.com.activities.BalanceDetailActivity;
import apartment.wisdom.com.adapters.BalanceDetailListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CusumListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

//消费明细
public class BalanceDetailFragment extends LazyFragment {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private BalanceDetailListAdapter adapter;

    private List<CusumListInfo.CusumItem> balanceDetailInfos = new ArrayList<>();
    private View noDateView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_commend_list);
        ButterKnife.bind(this, getContentView());
        adapter = new BalanceDetailListAdapter(R.layout.adapter_item_balance, balanceDetailInfos);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(adapter);
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无消费记录");

        getBalenceDetail();


    }

    private void getBalenceDetail() {
        ((BalanceDetailActivity) getActivity()).mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<CusumListInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "cardConsumeDetail"))
                .execute(new NewsCallback<AAResponse<CusumListInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<CusumListInfo>> response) {
                        ((BalanceDetailActivity) getActivity()).mSVProgressHUD.dismiss();
                        CusumListInfo cusumListInfo = response.body().data;
                        if (cusumListInfo != null && cusumListInfo.consumeList != null && !cusumListInfo.consumeList.isEmpty()) {
                            adapter.setNewData(cusumListInfo.consumeList);
                        } else {
                            adapter.setNewData(null);
                            adapter.setEmptyView(noDateView);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<CusumListInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        ((BalanceDetailActivity) getActivity()).mSVProgressHUD.dismiss();
                        adapter.setNewData(null);
                        adapter.setEmptyView(noDateView);
                    }
                });
    }
}
