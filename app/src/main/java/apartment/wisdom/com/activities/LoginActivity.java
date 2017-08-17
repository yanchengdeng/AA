package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;

import apartment.wisdom.com.R;
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

    }

    @OnClick({R.id.back, R.id.tv_change_pwstate, R.id.img_nomal_login, R.id.tv_dynamic_login, R.id.tv_forget_pw, R.id.tv_now_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_change_pwstate:
                if(isOpen){
                    //密文
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isOpen = false;
                }else{
                    //明文
                    etLoginPwd.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    isOpen = true;
                }
                imageHidePw.setSelected(isOpen);
                break;
            case R.id.img_nomal_login:
                mSVProgressHUD.showSuccessWithStatus(getString(R.string.login_success), SVProgressHUD.SVProgressHUDMaskType.Clear);
                LoginUtils.setLoginStatus(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1500);
                break;
            case R.id.tv_dynamic_login:

                break;
            case R.id.tv_forget_pw:
                break;
            case R.id.tv_now_register:
                break;
        }
    }
}
