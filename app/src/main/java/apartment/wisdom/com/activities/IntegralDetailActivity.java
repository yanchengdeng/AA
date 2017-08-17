package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.IntegralListAdapter;
import apartment.wisdom.com.beans.IntegralItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/16  16:44
 * Email: yanchengdeng@gmail.com
 * Describle: 积分详情
 */
public class IntegralDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_total_count)
    TextView tvTotalCount;
    @BindView(R.id.lv_item_integral)
    RecyclerView lvItemIntegral;

    private IntegralListAdapter integralListAdapter;

    private List<IntegralItem> integralItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_detail);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.integral_detail));
        integralItems.add(new IntegralItem());
        integralItems.add(new IntegralItem());
        integralItems.add(new IntegralItem());
        integralItems.add(new IntegralItem());
        lvItemIntegral.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
        lvItemIntegral.addItemDecoration(dividerItemDecoration);
        integralListAdapter = new IntegralListAdapter(R.layout.adapter_item_myintegral,integralItems);
        lvItemIntegral.setAdapter(integralListAdapter);

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
