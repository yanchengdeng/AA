package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HistoryListAdapter;
import apartment.wisdom.com.beans.AssistantItemInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/17  15:10
 * Email: yanchengdeng@gmail.com
 * Describle:  历史记录
 */
public class HistoryHotelActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private View noDateView;
    private List<AssistantItemInfo>  assistantItemInfos = new ArrayList<>();
    private HistoryListAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.trip_history));
        assistantItemInfos.add(new AssistantItemInfo(false));
        assistantItemInfos.add(new AssistantItemInfo(false));
        assistantItemInfos.add(new AssistantItemInfo(false));
        historyListAdapter = new HistoryListAdapter(R.layout.item_trip_history_card, assistantItemInfos);
        noDateView = getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView)noDateView.findViewById(R.id.tv_empty_tips)).setText("您没有历史行程哦~");
        ((ImageView)noDateView.findViewById(R.id.iv_empty_icon)).setImageResource(R.mipmap.trip_blank);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(historyListAdapter);

        historyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openActivity(HotelDetailActivity.class);
            }
        });
        historyListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.btn_trip_history_comment){
                    openActivity(DoCommendActivity.class);

                }else if(view.getId()==R.id.btn_trip_history_delete){
                    historyListAdapter.remove(position);
                    if (historyListAdapter.getData().isEmpty()){
                        historyListAdapter.setNewData(null);
                        historyListAdapter.setEmptyView(noDateView);
                    }
                }
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
