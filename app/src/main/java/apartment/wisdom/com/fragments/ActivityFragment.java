package apartment.wisdom.com.fragments;

import android.content.Intent;
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

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.ActivityDetailActivity;
import apartment.wisdom.com.adapters.ActivityListAdapter;
import apartment.wisdom.com.beans.ActivityInfo;
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

    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    private ActivityListAdapter activityListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this, view);
        tvTittle.setText(getString(R.string.tab_activity));
        initRecycle();
        return view;
    }

    private void initRecycle() {
        activityListAdapter = new ActivityListAdapter(R.layout.adapter_activity_layout,getActivityInfoList());
        swipeLayout.setOnRefreshListener(this);
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeLayout.setOnRefreshListener(this);
        rvList.setAdapter(activityListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        rvList.addItemDecoration(dividerItemDecoration);

        activityListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActivityFragment.this.startActivity(new Intent(getActivity(), ActivityDetailActivity.class));
            }
        });
    }


    private List<ActivityInfo> getActivityInfoList(){
        List<ActivityInfo> activityInfoList = new ArrayList<>();
        activityInfoList.add(new ActivityInfo());
        activityInfoList.add(new ActivityInfo());
        activityInfoList.add(new ActivityInfo());
        activityInfoList.add(new ActivityInfo());
        activityInfoList.add(new ActivityInfo());
        activityInfoList.add(new ActivityInfo());
        return activityInfoList;
    }


    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        activityListAdapter.setEnableLoadMore(true);

    }

    @Override
    public void onLoadMoreRequested() {


    }
}
