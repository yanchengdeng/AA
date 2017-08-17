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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.HistoryHotelActivity;
import apartment.wisdom.com.activities.LoginActivity;
import apartment.wisdom.com.activities.MainActivity;
import apartment.wisdom.com.adapters.AssistantListAdapter;
import apartment.wisdom.com.beans.AssistantItemInfo;
import apartment.wisdom.com.utils.LoginUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author: 邓言诚  Create at : 17/8/7  16:53
 * Email: yanchengdeng@gmail.com
 * Describle: 旅行助手
 */
public class AssistantFragment extends Fragment {

    @BindView(R.id.tv_order_hotel)
    TextView tvOrderHotel;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_trip_history)
    ImageView ivTripHistory;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.iv_book_hotel)
    TextView ivBookHotel;
    @BindView(R.id.tv_history_trip)
    TextView tvHistoryTrip;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.ll_no_trip_container)
    ScrollView llNoTripContainer;

    private AssistantListAdapter assistantListAdapter;

    private List<AssistantItemInfo> assistantItemInfoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_assistant, container, false);
        ButterKnife.bind(this, view);
        tvTittle.setText(getString(R.string.tab_assistant));
        assistantItemInfoList.add(new AssistantItemInfo());
        assistantItemInfoList.add(new AssistantItemInfo());
        assistantItemInfoList.add(new AssistantItemInfo());
        assistantListAdapter = new AssistantListAdapter(R.layout.item_trip_history_card, assistantItemInfoList);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(assistantListAdapter);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (LoginUtils.getLoginStatus()) {
                swipeLayout.setVisibility(View.VISIBLE);
                llNoTripContainer.setVisibility(View.GONE);
            } else {
                swipeLayout.setVisibility(View.GONE);
                llNoTripContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.tv_order_hotel, R.id.iv_trip_history, R.id.iv_book_hotel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_book_hotel:
            case R.id.tv_order_hotel:
                if (LoginUtils.getLoginStatus()) {
                    ((MainActivity) getActivity()).viewpager.setCurrentItem(0);
                } else {
                    ((MainActivity) getActivity()).openActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_trip_history:
                ((BaseActivity) getActivity()).openActivity(HistoryHotelActivity.class);
                break;

        }
    }
}
