package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/18  01:27
 * Email: yanchengdeng@gmail.com
 * Describle: 动态密码第二步
 */
public class DyanicLoginTwoActivity extends BaseActivity {

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
    @BindView(R.id.tv_mobile_num)
    TextView tvMobileNum;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_verify_code)
    TextView tvVerifyCode;
    @BindView(R.id.image_login)
    TextView imageLogin;
    @BindView(R.id.tv_dynamic_phone)
    TextView tvDynamicPhone;

    private String phone;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyanic_login_two);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.dynaic_password_login));
        phone = getIntent().getStringExtra(Constants.PASS_STRING);
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                tvVerifyCode.setText(String.format(getString(R.string.seconds_later_restart), ((int) l / 1000)));
                tvVerifyCode.setClickable(false);
                tvVerifyCode.setTextColor(getResources().getColor(R.color.activity_info));
            }

            @Override
            public void onFinish() {
                tvVerifyCode.setClickable(true);
                tvVerifyCode.setText(getString(R.string.send_msg));
                tvVerifyCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                countDownTimer.cancel();
            }
        };

        sendCode();

        if (!TextUtils.isEmpty(phone)) {
            tvDynamicPhone.setText(phone);
        }
    }

    //获取动态密码
    private void sendCode() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobilePhone", phone);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "getCheckCode"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        countDownTimer.start();
                        ToastUtils.showShort(R.string.code_success);
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    @OnClick({R.id.back, R.id.tv_verify_code, R.id.image_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_verify_code:
                sendCode();
                break;
            case R.id.image_login:
                if (TextUtils.isEmpty(etVerificationCode.getEditableText().toString())) {
                    ToastUtils.showShort(R.string.code_no);
                } else {
                    if (etVerificationCode.getEditableText().toString().trim().length() == 6) {
                        doLoginDynamic(etVerificationCode.getEditableText().toString().trim());
                    } else {
                        ToastUtils.showShort(R.string.regex_code);
                    }
                }
                break;
        }
    }

    private void doLoginDynamic(String code) {
        mSVProgressHUD.showWithStatus("登陆中...", SVProgressHUD.SVProgressHUDMaskType.Clear);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("mobilePhone", phone);
            data.put("validateCode",code);
            OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                    .cacheMode(CacheMode.NO_CACHE)
                    .params("data", ParamsUtils.getParams(data, "dynamicLogin"))
                    .execute(new NewsCallback<AAResponse<UserInfo>>() {
                        @Override
                        public void onSuccess(Response<AAResponse<UserInfo>> response) {
                            mSVProgressHUD.dismiss();
                            SPUtils.getInstance().put(Constants.LOGIN_USER_PHONE, phone);
                            LoginUtils.setLoginStatus(true);
                            UserInfo userInfo = response.body().data;
                            if (userInfo==null){
                                return;
                            }
                            SPUtils.getInstance().put(Constants.USER_INFO,new Gson().toJson(userInfo));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new LoginSuccessEvent());
                                    ToastUtils.showShort(R.string.login_success);
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
}
