package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.PayOrderListDetailAdapter;
import apartment.wisdom.com.beans.PreOrderInfo;
import apartment.wisdom.com.commons.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//付款清单
public class PayOrderDetailListActivity extends BaseActivity {

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

    private PreOrderInfo preOrderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order_detail_list);
        ButterKnife.bind(this);
        tvTittle.setText("付款清单");
        recycle.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
        recycle.addItemDecoration(dividerItemDecoration);
        preOrderInfo = (PreOrderInfo) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
        if (preOrderInfo != null && preOrderInfo.consumeList != null) {
            recycle.setAdapter(new PayOrderListDetailAdapter(mContext, R.layout.adapter_item_pay_detail, preOrderInfo.consumeList));
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
