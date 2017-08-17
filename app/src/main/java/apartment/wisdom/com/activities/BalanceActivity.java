package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/15  19:50
 * Email: yanchengdeng@gmail.com
 * Describle:  余额
 */
public class BalanceActivity extends BaseActivity {

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
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_balance_recharge)
    TextView tvBalanceRecharge;
    @BindView(R.id.tv_balance_gift)
    TextView tvBalanceGift;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_actvity);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.money_package));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.detail));

    }

    @OnClick({R.id.back, R.id.tv_right,R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                openActivity(BalanceDetailActivity.class);
                break;
            case R.id.tv_recharge:
                openActivity(RechagerActivity.class);
                break;
        }
    }
}
