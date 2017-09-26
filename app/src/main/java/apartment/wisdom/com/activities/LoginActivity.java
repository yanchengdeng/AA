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

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.events.RegisterSuccessEvent;
import apartment.wisdom.com.events.ResetPasswordSuccessEvent;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
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
                    ToastUtils.showShort(R.string.no_phone_card);
                } else {
                    if (RegexUtils.isMobileSimple(etLoginName.getEditableText().toString().trim()) || isUserCardNo(etLoginName.getEditableText().toString().trim())) {
                        if (TextUtils.isEmpty(etLoginPwd.getEditableText().toString())) {
                            ToastUtils.showShort(R.string.no_password);
                        } else {
                            if (etLoginPwd.getEditableText().toString().trim().length() < 4 || etLoginPwd.getEditableText().toString().trim().length() > 20) {
                                ToastUtils.showShort(R.string.regex_password);
                            } else {

                                doLogin(etLoginName.getEditableText().toString().trim(), etLoginPwd.getEditableText().toString());
                            }
                        }
                    } else {
                        ToastUtils.showShort(R.string.phone_card_regex);
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

    private void doLogin(String phone, String password) {
        mSVProgressHUD.showWithStatus("登陆中...", SVProgressHUD.SVProgressHUDMaskType.Clear);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", phone);
        data.put("pwd", password);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)       //上拉不需要缓存
                .params("data", ParamsUtils.getParams(data, "login"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        UserInfo userInfo = response.body().data;
                        mSVProgressHUD.dismiss();
                        if (userInfo==null){
                            return;
                        }
                        SPUtils.getInstance().put(Constants.USER_INFO,new Gson().toJson(userInfo));
                        LoginUtils.setLoginStatus(true);
                        SPUtils.getInstance().put(Constants.LOGIN_USER_PHONE, etLoginName.getEditableText().toString().trim());
                        ToastUtils.showShort(R.string.login_success);
                        EventBus.getDefault().post(new LoginSuccessEvent());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1500);
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        mSVProgressHUD.dismiss();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof RegisterSuccessEvent || event instanceof ResetPasswordSuccessEvent) {
            String phone = SPUtils.getInstance().getString(Constants.LOGIN_USER_PHONE);
            if (!TextUtils.isEmpty(phone)) {
                etLoginName.setText(phone);
            }
        } else if (event instanceof LoginSuccessEvent) {
            finish();
        }
    }


    private boolean isUserCardNo(String card) {
        String regex = "^[M]\\d{9}";

        return card.matches(regex);
    }
}
