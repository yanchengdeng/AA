package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import apartment.wisdom.com.R;
import apartment.wisdom.com.events.ResetPasswordSuccessEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordTwoActivity extends BaseActivity {

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
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.bt_reset_password)
    TextView btResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_two);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.bt_reset_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_reset_password:
                if (TextUtils.isEmpty(etNewPassword.getEditableText().toString().trim())) {
                    ToastUtils.showShort(R.string.no_password);
                }else if (etNewPassword.getEditableText().toString().trim().length()>4 && etNewPassword.getEditableText().toString().trim().length()<21){
                    ToastUtils.showShort(R.string.reset_password_success);
                    EventBus.getDefault().post(new ResetPasswordSuccessEvent());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },1500);
                }else{
                    ToastUtils.showShort(R.string.regex_password);
                }
                break;
        }
    }
}
