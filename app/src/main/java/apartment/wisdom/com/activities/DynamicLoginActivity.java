package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.events.RegisterSuccessEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/18  01:22
 * Email: yanchengdeng@gmail.com
 * Describle: 动态登陆
 */
public class DynamicLoginActivity extends BaseActivity {

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
    @BindView(R.id.et_dynamicphone)
    EditText etDynamicphone;
    @BindView(R.id.img_get_dypw)
    TextView imgGetDypw;
    @BindView(R.id.tv_nomal_login)
    TextView tvNomalLogin;
    @BindView(R.id.tv_register_start)
    TextView tvRegisterStart;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginSuccessEvent event) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_login);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.dynaic_password_login));
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.back, R.id.img_get_dypw, R.id.tv_nomal_login, R.id.tv_register_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.img_get_dypw:
                if (TextUtils.isEmpty(etDynamicphone.getEditableText().toString().trim())){
                    ToastUtils.showShort(R.string.no_phone);
                }else{
                    if (RegexUtils.isMobileSimple(etDynamicphone.getEditableText().toString().trim())){
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.PASS_STRING,etDynamicphone.getEditableText().toString());
                        openActivity(DyanicLoginTwoActivity.class,bundle);
                    }else{
                        ToastUtils.showShort(R.string.phone_regex);
                    }
                }
                break;
            case R.id.tv_nomal_login:
                finish();
                break;
            case R.id.tv_register_start:
                openActivity(RegisterOneActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RegisterSuccessEvent event) {
        finish();
    }
}