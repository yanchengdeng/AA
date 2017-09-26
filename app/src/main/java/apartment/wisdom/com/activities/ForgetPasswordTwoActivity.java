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
import apartment.wisdom.com.events.ResetPasswordSuccessEvent;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
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
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_two);
        ButterKnife.bind(this);
        tvTittle.setText("重置密码2/2");
        phone = getIntent().getStringExtra(Constants.PASS_STRING);
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
                } else if (etNewPassword.getEditableText().toString().trim().length() > 4 && etNewPassword.getEditableText().toString().trim().length() < 21) {
                    doResetPwd(etNewPassword.getEditableText().toString().trim());
                } else {
                    ToastUtils.showShort(R.string.regex_password);
                }
                break;
        }
    }


    //重置密码
    private void doResetPwd(String password) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("mobilePhone", phone);
        data.put("pwd", password);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "resetPwd"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(R.string.reset_password_success);
                        EventBus.getDefault().post(new ResetPasswordSuccessEvent());
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
                    }
                });
    }
}
