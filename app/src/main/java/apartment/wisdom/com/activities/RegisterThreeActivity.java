package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.RegisterSuccessEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterThreeActivity extends BaseActivity {

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
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.tv_mobile_yqm)
    TextView tvMobileYqm;
    @BindView(R.id.et_register_yqm)
    EditText etRegisterYqm;
    @BindView(R.id.bt_register)
    TextView btRegister;
    private String phone, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);
        ButterKnife.bind(this);
        tvTittle.setText("注册3/3");
        phone = getIntent().getStringExtra(Constants.PASS_STRING);
        name = getIntent().getStringExtra(Constants.PASS_NMAE);
    }

    @OnClick({R.id.back, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_register:
                if (TextUtils.isEmpty(etRegisterPwd.getEditableText().toString())) {
                    ToastUtils.showShort(R.string.no_password);
                } else if (etRegisterPwd.getEditableText().toString().length() > 4 && etRegisterPwd.getEditableText().toString().trim().length() < 21) {
                    ToastUtils.showShort(R.string.regiseter_success);
                    if (!TextUtils.isEmpty(phone)) {
                        SPUtils.getInstance().put(Constants.LOGIN_USER_PHONE, phone);
                    }
                    EventBus.getDefault().post(new RegisterSuccessEvent());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                } else {
                    ToastUtils.showShort(R.string.regex_password);
                }
                break;
        }
    }
}
