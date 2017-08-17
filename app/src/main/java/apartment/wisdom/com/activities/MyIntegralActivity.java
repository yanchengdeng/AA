package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/15  20:11
 * Email: yanchengdeng@gmail.com
 * Describle:  我的优惠劵
 */
public class MyIntegralActivity extends BaseActivity {

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
    @BindView(R.id.tv_use_integral)
    TextView tvUseIntegral;
    @BindView(R.id.tv_total_integral)
    TextView tvTotalIntegral;
    @BindView(R.id.tv_cool_integral)
    TextView tvCoolIntegral;
    @BindView(R.id.image_arrow_right)
    ImageView imageArrowRight;
    @BindView(R.id.lay_integral_detail)
    LinearLayout layIntegralDetail;
    @BindView(R.id.image_arrow_rights)
    ImageView imageArrowRights;
    @BindView(R.id.lay_exchange_record)
    LinearLayout layExchangeRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.integral));
    }

    @OnClick({R.id.back, R.id.lay_integral_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.lay_integral_detail:
                openActivity(IntegralDetailActivity.class);
                break;
        }
    }
}
