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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import apartment.wisdom.com.R;
import apartment.wisdom.com.events.ResetPasswordSuccessEvent;
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
                etVertifyCode.setText(String.format(getString(R.string.seconds_later_restart), ((int) l / 1000)));
                etVertifyCode.setClickable(false);
                etVertifyCode.setTextColor(getResources().getColor(R.color.activity_info));
            }

            @Override
            public void onFinish() {
                etVertifyCode.setClickable(true);
                etVertifyCode.setText(getString(R.string.send_msg));
                etVertifyCode.setTextColor(getResources().getColor(R.color.colorPrimary));
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
                countDownTimer.cancel();
                break;
            case R.id.bt_next_resetpw:
                if (TextUtils.isEmpty(etNewMobile.getEditableText().toString())){
                    ToastUtils.showShort(R.string.no_phone);
                }else if (RegexUtils.isMobileSimple(etNewMobile.getEditableText().toString())){
                    if (TextUtils.isEmpty(etVertifyCode.getEditableText().toString().trim())) {
                        ToastUtils.showShort(R.string.no_code);
                    }else{
                        if (etVertifyCode.getEditableText().toString().trim().length()==6){
                            openActivity(ForgetPasswordTwoActivity.class);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ResetPasswordSuccessEvent event) {
        finish();
    }
}
