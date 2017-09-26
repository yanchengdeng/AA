package apartment.wisdom.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import apartment.wisdom.com.adapters.ActivityListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.HomeAdInfo;
import apartment.wisdom.com.beans.HomeAdInfoList;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邓言诚  Create at : 17/8/7  16:53
 * Email: yanchengdeng@gmail.com
 * Describle: 活动
 */
public class ActivityFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private View noDateView;

    private List<HomeAdInfoList> activityInfoList = new ArrayList<>();

    private ActivityListAdapter activityListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this, view);
        tvTittle.setText(getString(R.string.tab_activity));
        back.setVisibility(View.GONE);
        noDateView = getActivity().getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) rvList.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无活动");
        initRecycle();
        return view;
    }

    private void initRecycle() {
        activityListAdapter = new ActivityListAdapter(getActivity(), R.layout.adapter_activity_layout, activityInfoList);
        swipeLayout.setOnRefreshListener(this);
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeLayout.setOnRefreshListener(this);
        rvList.setAdapter(activityListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        rvList.addItemDecoration(dividerItemDecoration);
        getActivityInfoList();

    }


    private void getActivityInfoList() {
        Map<String, Object> data = new HashMap<String, Object>();
        OkGo.<AAResponse<HomeAdInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.DEFAULT)
                .params("data", ParamsUtils.getParams(data, "getActivity"))
                .execute(new NewsCallback<AAResponse<HomeAdInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HomeAdInfo>> response) {
                        HomeAdInfo homeAdInfos = response.body().data;
                        if (!homeAdInfos.activityList.isEmpty()) {
                            activityListAdapter.setNewData(homeAdInfos.activityList);
                        } else {
                            activityListAdapter.setNewData(null);
                            activityListAdapter.setEmptyView(noDateView);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<HomeAdInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        activityListAdapter.setNewData(null);
                        activityListAdapter.setEmptyView(noDateView);
                    }
                });
    }


    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        activityListAdapter.setEnableLoadMore(true);
        getActivityInfoList();

    }

    @Override
    public void onLoadMoreRequested() {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
