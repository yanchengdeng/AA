package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import apartment.wisdom.com.events.ResetPasswordSuccessEvent;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/18  01:49
 * Email: yanchengdeng@gmail.com
 * Describle: 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {

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
    @BindView(R.id.et_new_mobile)
    EditText etNewMobile;
    @BindView(R.id.tv_mobile_num)
    TextView tvMobileNum;
    @BindView(R.id.et_vertify_code)
    EditText etVertifyCode;
    @BindView(R.id.bt_retry_code)
    TextView btRetryCode;
    @BindView(R.id.bt_next_resetpw)
    TextView btNextResetpw;

    private CountDownTimer countDownTimer;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        tvTittle.setText("重置密码1/2");
        EventBus.getDefault().register(this);
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                btRetryCode.setText(String.format(getString(R.string.seconds_later_restart), ((int) l / 1000)));
                btRetryCode.setClickable(false);
                btRetryCode.setTextColor(getResources().getColor(R.color.activity_info));
            }

            @Override
            public void onFinish() {
                btRetryCode.setClickable(true);
                btRetryCode.setText(getString(R.string.send_msg));
                btRetryCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                countDownTimer.cancel();
            }
        };
        
    }

    @OnClick({R.id.back, R.id.bt_retry_code, R.id.bt_next_resetpw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_retry_code:
                if (TextUtils.isEmpty(etNewMobile.getEditableText().toString().trim())){
                    ToastUtils.showShort(R.string.no_phone);
                }else if (RegexUtils.isMobileSimple(etNewMobile.getEditableText().toString().trim())){
                    sendCode(etNewMobile.getEditableText().toString().trim());
                }else{
                    ToastUtils.showShort(R.string.phone_regex);
                }

                break;
            case R.id.bt_next_resetpw:
                if (TextUtils.isEmpty(etNewMobile.getEditableText().toString().trim())){
                    ToastUtils.showShort(R.string.no_phone);
                }else if (RegexUtils.isMobileSimple(etNewMobile.getEditableText().toString())){
                    if (TextUtils.isEmpty(etVertifyCode.getEditableText().toString().trim())) {
                        ToastUtils.showShort(R.string.no_code);
                    }else{
                        if (etVertifyCode.getEditableText().toString().trim().length()==6){
                            checkCode(etVertifyCode.getEditableText().toString().trim());
                        }else{
                            ToastUtils.showShort(R.string.regex_code);
                        }
                    }
                }else{
                    ToastUtils.showShort(R.string.phone_regex);
                }

                break;
        }
    }


    //发送验证码
    private void sendCode(final String phone) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobilePhone", phone);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"getCheckCode"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        countDownTimer.start();
                        phoneNum = phone;
                        ToastUtils.showShort(R.string.code_success);
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    //校验短信信息
    private void checkCode(String code) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobilePhone", phoneNum);
        data.put("validateCode",code);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"checkCode"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.PASS_STRING,phoneNum);
                        openActivity(ForgetPasswordTwoActivity.class,bundle);
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ResetPasswordSuccessEvent event) {
        finish();
    }
}
