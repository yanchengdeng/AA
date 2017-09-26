package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import apartment.wisdom.com.events.RegisterSuccessEvent;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterTwoActivity extends BaseActivity {

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
    @BindView(R.id.bt_retry_code)
    TextView btRetryCode;
    @BindView(R.id.bt_register_next)
    TextView btRegisterNext;

    private CountDownTimer countDownTimer;

    private String phone,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        ButterKnife.bind(this);
        tvTittle.setText("注册2/3");
        EventBus.getDefault().register(this);
        phone = getIntent().getStringExtra(Constants.PASS_STRING);
        name = getIntent().getStringExtra(Constants.PASS_NMAE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RegisterSuccessEvent event) {
        finish();
    }

    @OnClick({R.id.back, R.id.bt_retry_code, R.id.bt_register_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_retry_code:
                sendCode();
                break;
            case R.id.bt_register_next:

                if (TextUtils.isEmpty(etVerificationCode.getEditableText().toString())) {
                    ToastUtils.showShort(R.string.code_no);
                }else if (etVerificationCode.getEditableText().toString().trim().length()==6){
                    checkCode(etVerificationCode.getEditableText().toString());
                }else{
                    ToastUtils.showShort(R.string.regex_code);
                }
                break;
        }
    }

    //校验短信信息
    private void checkCode(String code) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobilePhone", phone);
        data.put("validateCode",code);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"checkCode"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        openActivity(RegisterThreeActivity.class,getIntent().getExtras());
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    //发送验证码
    private void sendCode() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobilePhone", phone);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"getCheckCode"))
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
}
