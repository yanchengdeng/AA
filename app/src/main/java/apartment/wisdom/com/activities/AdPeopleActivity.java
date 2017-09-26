package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CustomPeopleList;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/16  00:37
 * Email: yanchengdeng@gmail.com
 * Describle: 添加常用联系旅客
 */
public class AdPeopleActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.et_passger_name)
    EditText etPassgerName;
    @BindView(R.id.et_passger_phone)
    EditText etPassgerPhone;
    @BindView(R.id.et_detail_address)
    TextView etDetailAddress;
    @BindView(R.id.tv_passagercer_type)
    TextView tvPassagercerType;
    @BindView(R.id.ray_change_card)
    RelativeLayout rayChangeCard;
    @BindView(R.id.et_passger_certinum)
    EditText etPassgerCertinum;
    @BindView(R.id.et_passger_email)
    EditText etPassgerEmail;
    @BindView(R.id.people_ll_gone)
    LinearLayout peopleLlGone;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private Bundle bundle;

    private int idType = 0;
    String[] stringItems;
    public static final int RESULT_ADD_SUCCESS = 0x110;
    public static final int RESULT_UPDATE_SUCCESS = 0x111;

    private CustomPeopleList.CustomPeopleItem customPeopleItem;
    private String checkInNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_people);
        ButterKnife.bind(this);
        stringItems = new String[]{getString(R.string.people_card), getString(R.string.student_card), getString(R.string.people_passport)};

        bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTittle.setText(R.string.edit_people_info);
            tvRight.setText(R.string.delet);
            tvRight.setVisibility(View.VISIBLE);
            customPeopleItem = (CustomPeopleList.CustomPeopleItem) bundle.getSerializable(Constants.PASS_OBJECT);
            checkInNum = customPeopleItem.checkInNo;
            if (!TextUtils.isEmpty(customPeopleItem.name)) {
                etPassgerName.setText(customPeopleItem.name);
            }
            if (!TextUtils.isEmpty(customPeopleItem.mobilePhone)) {
                etPassgerPhone.setText(customPeopleItem.mobilePhone);
            }
            if (!TextUtils.isEmpty(customPeopleItem.idType)) {
                tvPassagercerType.setText(stringItems[Integer.parseInt(customPeopleItem.idType)]);
                etPassgerCertinum.setText(customPeopleItem.checkInNo);
            }

        } else {
            tvTittle.setText(getString(R.string.add_person));
        }
    }

    @OnClick({R.id.back, R.id.ray_change_card, R.id.tv_save, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ray_change_card:
                showSelectCard();
                break;
            case R.id.tv_save:
                saveCommonInfo();
                break;
            case R.id.tv_right:
                doDelCustomeInfo();
                ToastUtils.showShort(R.string.delet_success);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
                break;
        }
    }

    //删除信息
    private void doDelCustomeInfo() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("checkInNo", checkInNum);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "delCommonInfo"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(R.string.delet_success);
                        Intent intent = new Intent(mContext, CommonInfoActivity.class);
                        setResult(RESULT_UPDATE_SUCCESS, intent);
                        finish();
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    //添加修改信息
    private void saveCommonInfo() {
        if (TextUtils.isEmpty(etPassgerName.getEditableText().toString().trim())) {
            ToastUtils.showShort(R.string.name_not_empty);
        } else {
            if (TextUtils.isEmpty(etPassgerPhone.getEditableText().toString().trim())) {
                ToastUtils.showShort(R.string.phone_not_empty);
            } else {
                if (!TextUtils.isEmpty(etPassgerEmail.getEditableText().toString().trim()) && !RegexUtils.isEmail(etPassgerEmail.getEditableText().toString().trim())) {
                    ToastUtils.showShort(R.string.email_not_regex);
                    return;
                }
                addCustomeInfo(etPassgerName.getEditableText().toString().trim(), etPassgerPhone.getEditableText().toString().trim(), etPassgerEmail.getEditableText().toString().trim());
            }
        }
    }

    //添加常用联系人
    private void addCustomeInfo(String name, String phone, String email) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        data.put("name", name);
        if (!TextUtils.isEmpty(etPassgerCertinum.getEditableText().toString().trim())) {
            data.put("idType", idType);
            data.put("idNo", etPassgerCertinum.getEditableText().toString().trim());
        }
        data.put("mobilePhone", phone);
        if (!TextUtils.isEmpty(email)) {
            data.put("email", email);
        }

        if (!TextUtils.isEmpty(checkInNum)) {
            data.put("checkInNo", checkInNum);
        }
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "saveCommonInfo"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        if (TextUtils.isEmpty(checkInNum)) {
                            ToastUtils.showShort(R.string.add_success);
                        } else {
                            ToastUtils.showShort(R.string.update_success);
                        }
                        Intent intent = new Intent(mContext, CommonInfoActivity.class);
                        if (bundle != null) {
                            setResult(RESULT_UPDATE_SUCCESS, intent);
                        } else {
                            setResult(RESULT_ADD_SUCCESS, intent);
                        }
                        finish();
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }


    //证件类型：0-身份证，1-学生证，2-其他证件
    private void showSelectCard() {
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, tvSave);
        dialog.isTitleShow(false).show();
        dialog.setTitle(getString(R.string.select_card_type));

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvPassagercerType.setText(stringItems[position]);
                idType = position;
                dialog.dismiss();
            }
        });
    }
}
