package apartment.wisdom.com.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginOutSuccessEvent;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.umeng.socialize.utils.DeviceConfig.context;


/**
 * Author: 邓言诚  Create at : 17/8/15  21:54
 * Email: yanchengdeng@gmail.com
 * Describle: 我的信息
 */
@RuntimePermissions
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
    private String idType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.personal_info));
        if (LoginUtils.getLoginStatus()) {
            tvLoginOut.setVisibility(View.VISIBLE);
        } else {
            tvLoginOut.setVisibility(View.GONE);
        }
        initData();
    }

    private void initData() {
        UserInfo userInfo = LoginUtils.getUserInfo();
        if (userInfo!=null  && !TextUtils.isEmpty(userInfo.token)){
            if (!TextUtils.isEmpty(userInfo.headImage)) {
                Glide.with(mContext).load(userInfo.headImage).bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(imagePerson);
            }
            if (!TextUtils.isEmpty(userInfo.name)) {
                tvUserName.setText(userInfo.name);
            }
            if (!TextUtils.isEmpty(userInfo.birthdate)) {
                tvBirth.setText(userInfo.birthdate);
            }
            if (!TextUtils.isEmpty(userInfo.mobilePhone)) {
                tvPhone.setText(userInfo.mobilePhone);
            }

            if (!TextUtils.isEmpty(userInfo.idNo)) {
                tvCard.setText(userInfo.idNo);
            }

            if (!TextUtils.isEmpty(userInfo.email)) {
                tvEmail.setText(userInfo.email);
            }
        }
    }


    @OnClick({R.id.back, R.id.people_ll, R.id.rl_usename, R.id.rl_birth, R.id.rl_phone, R.id.rl_card, R.id.rl_email, R.id.tv_login_out})
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
                MeInfoActivityPermissionsDispatcher.showCameraWithCheck(this);
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
                SPUtils.getInstance().put(Constants.USER_INFO, "");
                EventBus.getDefault().post(new LoginOutSuccessEvent());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
                break;
        }
    }


    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showCamera() {
        openPhoto();
    }

    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
//        ToastUtils.showShort("解释为啥需要权限");
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
//        ToastUtils.showShort("拒绝权限");
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        final MaterialDialog dialog = new MaterialDialog(mContext);
        dialog.content(
                "如需要使用该功能，请打开相机及文件读写权限")//
                .btnText("取消", "确定")//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        try {
                            Uri packageURI = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }
                }
        );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MeInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showBirthPop() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvBirth.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")));
                doUploadInfo("","",tvBirth.getText().toString().trim(),"","","");
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
                    getSmallFile( mSelectPath.get(0));
                }
            }
        } else if (requestCode == REQUEST_EMAIL) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(Constants.PASS_STRING);
                if (!TextUtils.isEmpty(email)) {
                    tvEmail.setText(email);
                    doUploadInfo("","","","","",email);
                }
            }
        } else if (requestCode == REQUEST_NAME) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(Constants.PASS_STRING);
                if (!TextUtils.isEmpty(name)) {
                    tvUserName.setText(name);
                    doUploadInfo("",name,"","","","");
                }
            }
        } else if (requestCode == REQUEST_CARD) {
            if (resultCode == RESULT_OK) {
                String card = data.getStringExtra(Constants.PASS_STRING);
                idType = data.getStringExtra(Constants.SELECT_CARD_TYPE);
                if (!TextUtils.isEmpty(card)) {
                    tvCard.setText(card);
                    doUploadInfo("","","","",card,"");
                }
            }
        }
    }

    private void getSmallFile(String fileOld) {
        mSVProgressHUD.showWithStatus("上传中...");
            Luban.with(mContext)
                    .load(new File(fileOld))                     //传人要压缩的图片
                    .ignoreBy(30)
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            getHeadBitmap(file.getPath());
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            mSVProgressHUD.dismiss();
                        }
                    }).launch();
    }

    private void getHeadBitmap(String yourUrl) {
        Glide.with(this)
                .load(yourUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap myBitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (myBitmap != null) {

                            Bitmap samllBitmap = ImageUtils.scale(myBitmap,100,100);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            samllBitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            doUploadInfo(encoded,"","","","","");
                        }
                    }
                });
    }

    /**
     * 上传更新信息
     * @param encoded
     * @param name
     * @param birth
     * @param phone
     * @param cardNo
     * @param email
     */
    private void doUploadInfo(String encoded,String name ,String birth,String phone,String cardNo,String email) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("cardNo",LoginUtils.getUserInfo().cardNo);
        if (!TextUtils.isEmpty(encoded)){
            data.put("headImage", encoded);
        }
        if (!TextUtils.isEmpty(name)) {
            data.put("name",name);
        }

        if (!TextUtils.isEmpty(birth)) {
            data.put("birthDate",birth);
        }

        if (!TextUtils.isEmpty(phone)) {
            data.put("mobilePhone",phone);
        }

        if (!TextUtils.isEmpty(cardNo)) {
            data.put("idNo",cardNo);
                data.put("idType",idType);
        }

        if (!TextUtils.isEmpty(email)) {
            data.put("email",email);
        }

        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "saveUser"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        mSVProgressHUD.dismiss();
                        ToastUtils.showShort("更新成功");
                        UserInfo userInfo = response.body().data;
                        if (userInfo!=null){
                            SPUtils.getInstance().put(Constants.USER_INFO,new Gson().toJson(userInfo));
                            EventBus.getDefault().post(new LoginSuccessEvent());
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        mSVProgressHUD.dismiss();
                    }
                });
    }
}
