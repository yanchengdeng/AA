package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginOutSuccessEvent;
import apartment.wisdom.com.utils.LoginUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Author: 邓言诚  Create at : 17/8/15  21:54
 * Email: yanchengdeng@gmail.com
 * Describle: 我的信息
 */
public class MeInfoActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.image_person)
    ImageView imagePerson;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.people_ll)
    RelativeLayout peopleLl;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rl_usename)
    RelativeLayout rlUsename;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.rl_birth)
    RelativeLayout rlBirth;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;


    private int REQUEST_IMAGE = 0x00110;
    private int REQUEST_NAME = 0x00111;
    private int REQUEST_CARD = 0x00112;
    private int REQUEST_EMAIL = 0x00113;
    private ArrayList<String> mSelectPath;/// 相册图片选择

    private List<String> mySelects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.personal_info));
        if (LoginUtils.getLoginStatus()) {
            tvLoginOut.setVisibility(View.VISIBLE);
        }else{
            tvLoginOut.setVisibility(View.GONE);
        }
    }



    @OnClick({R.id.back, R.id.people_ll, R.id.rl_usename, R.id.rl_birth, R.id.rl_phone, R.id.rl_card, R.id.rl_email,R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.people_ll:
                openPhoto();
                break;
            case R.id.rl_usename:
                openActivity(EditNameActivity.class, REQUEST_NAME);
                break;
            case R.id.rl_birth:
                showBirthPop();
                break;
            case R.id.rl_phone:
                //TODO
                break;
            case R.id.rl_card:
                openActivity(EditCardActivity.class, REQUEST_CARD);
                break;
            case R.id.rl_email:
                openActivity(EditEmailActivity.class, REQUEST_EMAIL);
                break;
            case R.id.tv_login_out:
                ToastUtils.showShort(getString(R.string.login_out_success));
                LoginUtils.setLoginStatus(false);
                EventBus.getDefault().post(new LoginOutSuccessEvent());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1500);
                break;
        }
    }

    private void showBirthPop() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvBirth.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void openPhoto() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        if (mSelectPath != null && mSelectPath.size() > 0) {
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    Glide.with(mContext).load("file:///" + mSelectPath.get(0)).bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(imagePerson);
                }
            }
        } else if (requestCode == REQUEST_EMAIL) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(Constants.PASS_STRING);
                if (!TextUtils.isEmpty(email)) {
                    tvEmail.setText(email);
                    mSVProgressHUD.showSuccessWithStatus(getString(R.string.edit_success), SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            }
        } else if (requestCode == REQUEST_NAME) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(Constants.PASS_STRING);
                if (!TextUtils.isEmpty(name)) {
                    tvUserName.setText(name);
                    mSVProgressHUD.showSuccessWithStatus(getString(R.string.edit_success), SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            }
        } else if (requestCode == REQUEST_CARD) {
            if (resultCode == RESULT_OK) {
                String card = data.getStringExtra(Constants.PASS_STRING);
                if (!TextUtils.isEmpty(card)) {
                    tvCard.setText(card);
                    mSVProgressHUD.showSuccessWithStatus(getString(R.string.edit_success), SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            }
        }
    }
}
