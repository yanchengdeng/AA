package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/15  20:23
 * Email: yanchengdeng@gmail.com
 * Describle: 关于
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_loge)
    ImageView ivLoge;
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.about_app));
        tvVersionCode.setText("V"+AppUtils.getAppVersionName());
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
