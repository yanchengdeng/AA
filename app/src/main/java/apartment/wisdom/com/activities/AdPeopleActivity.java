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

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
*
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

    public static final int RESULT_ADD_SUCCESS = 0x110;
    public static final int RESULT_UPDATE_SUCCESS= 0x111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_people);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTittle.setText(R.string.edit_people_info);
            tvRight.setText(R.string.delet);
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvTittle.setText(getString(R.string.add_person));
        }
    }

    @OnClick({R.id.back, R.id.ray_change_card, R.id.tv_save,R.id.tv_right})
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
                ToastUtils.showShort(R.string.delet_success);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      finish();
                    }
                },1500);
                break;
        }
    }

    private void saveCommonInfo() {
        if (TextUtils.isEmpty(etPassgerName.getEditableText().toString().trim())) {
            ToastUtils.showShort(R.string.name_not_empty);
        } else {
            if (TextUtils.isEmpty(etPassgerPhone.getEditableText().toString().trim())) {
                ToastUtils.showShort(R.string.phone_not_empty);
            } else {
                if (!TextUtils.isEmpty(etPassgerEmail.getEditableText().toString().trim()) && RegexUtils.isEmail(etPassgerEmail.getEditableText().toString().trim())) {
                    ToastUtils.showShort(R.string.email_not_regex);
                    return;
                }
                Intent intent = new Intent(mContext, CommonInfoActivity.class);
                if (bundle!=null){
                    setResult(RESULT_UPDATE_SUCCESS, intent);
                }else {
                    setResult(RESULT_ADD_SUCCESS, intent);
                }
                finish();
            }
        }
    }

    private void showSelectCard() {
        final String[] stringItems = {getString(R.string.people_card), getString(R.string.people_passport)};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, tvSave);
        dialog.isTitleShow(false).show();
        dialog.setTitle(getString(R.string.select_card_type));

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvPassagercerType.setText(stringItems[position]);
            }
        });
    }
}
