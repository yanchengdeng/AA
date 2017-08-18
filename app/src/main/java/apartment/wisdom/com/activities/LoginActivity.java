package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.events.RegisterSuccessEvent;
import apartment.wisdom.com.events.ResetPasswordSuccessEvent;
import apartment.wisdom.com.utils.LoginUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/17  15:17
 * Email: yanchengdeng@gmail.com
 * Describle: 登陆
 */
public class LoginActivity extends BaseActivity {

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
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.tv_pw)
    TextView tvPw;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.image_hide_pw)
    ImageView imageHidePw;
    @BindView(R.id.tv_change_pwstate)
    LinearLayout tvChangePwstate;
    @BindView(R.id.img_nomal_login)
    TextView imgNomalLogin;
    @BindView(R.id.tv_dynamic_login)
    TextView tvDynamicLogin;
    @BindView(R.id.tv_forget_pw)
    TextView tvForgetPw;
    @BindView(R.id.tv_now_register)
    TextView tvNowRegister;

    private boolean isOpen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        imageHidePw.setSelected(isOpen);
        tvTittle.setText(getString(R.string.login));
        EventBus.getDefault().register(this);

    }

    @OnClick({R.id.back, R.id.tv_change_pwstate, R.id.img_nomal_login, R.id.tv_dynamic_login, R.id.tv_forget_pw, R.id.tv_now_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_change_pwstate:
                if (isOpen) {
                    //密文
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isOpen = false;
                } else {
                    //明文
                    etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isOpen = true;
                }
                imageHidePw.setSelected(!isOpen);
                break;
            case R.id.img_nomal_login:
                if (TextUtils.isEmpty(etLoginName.getEditableText().toString().trim())) {
                    ToastUtils.showShort(R.string.no_phone);
                } else {
                    if (RegexUtils.isMobileSimple(etLoginName.getEditableText().toString().trim())) {
                        if (TextUtils.isEmpty(etLoginPwd.getEditableText().toString())) {
                            ToastUtils.showShort(R.string.no_password);
                        } else {
                            if (etLoginPwd.getEditableText().toString().length() < 4 || etLoginPwd.getEditableText().toString().length() > 20) {
                                ToastUtils.showShort(R.string.regex_password);
                            } else {
                                ToastUtils.showShort(R.string.login_success);
                                LoginUtils.setLoginStatus(true);
                                SPUtils.getInstance().put(Constants.LOGIN_USER_PHONE,etLoginName.getEditableText().toString().trim());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new LoginSuccessEvent());
                                    }
                                }, 1500);
                            }
                        }
                    } else {
                        ToastUtils.showShort(R.string.phone_regex);
                    }
                }
                break;
            case R.id.tv_dynamic_login:
                openActivity(DynamicLoginActivity.class);
                break;
            case R.id.tv_forget_pw:
                openActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tv_now_register:
                openActivity(RegisterOneActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof RegisterSuccessEvent || event instanceof ResetPasswordSuccessEvent){
            String phone = SPUtils.getInstance().getString(Constants.LOGIN_USER_PHONE);
            if (!TextUtils.isEmpty(phone)) {
                etLoginName.setText(phone);
            }
        }else if (event instanceof LoginSuccessEvent){
            finish();
        }
    }
}
