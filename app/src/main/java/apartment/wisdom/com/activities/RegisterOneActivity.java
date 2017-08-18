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
import apartment.wisdom.com.events.RegisterSuccessEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/18  01:39
 * Email: yanchengdeng@gmail.com
 * Describle: 注册 第一步
 */
public class RegisterOneActivity extends BaseActivity {

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
    @BindView(R.id.et_reg_phone)
    EditText etRegPhone;
    @BindView(R.id.tv_name_register)
    TextView tvNameRegister;
    @BindView(R.id.et_reg_name)
    EditText etRegName;
    @BindView(R.id.bt_register_next)
    TextView btRegisterNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvTittle.setText("注册1/3");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RegisterSuccessEvent event) {
        finish();
    }

    @OnClick({R.id.back, R.id.bt_register_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_register_next:
                checkParamas(etRegPhone.getEditableText().toString(),etRegName.getEditableText().toString());
                break;
        }
    }

    private void checkParamas(String phone, String name) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(R.string.no_phone);
        }else if(RegexUtils.isMobileSimple(phone)){
            if (TextUtils.isEmpty(name)){
                ToastUtils.showShort(R.string.no_name);
            }else{
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PASS_NMAE,name);
                bundle.putString(Constants.PASS_STRING,phone);
                openActivity(RegisterTwoActivity.class,bundle);
            }
        }else{
            ToastUtils.showShort(R.string.phone_regex);
        }
    }
}
