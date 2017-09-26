package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditCardActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.et_card)
    EditText etCard;
    @BindView(R.id.bt_save)
    TextView btSave;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.et_detail_address)
    TextView etDetailAddress;
    @BindView(R.id.tv_passagercer_type)
    TextView tvPassagercerType;
    @BindView(R.id.ray_change_card)
    RelativeLayout rayChangeCard;
    private String idType = "0";
    String[] stringItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.id_card));
        stringItems = new String[]{getString(R.string.people_card), getString(R.string.student_card), getString(R.string.people_passport)};
    }

    @OnClick({R.id.back, R.id.tv_passagercer_type, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_passagercer_type:
                showSelectCard();
                break;
            case R.id.bt_save:
                if (!TextUtils.isEmpty(etCard.getEditableText().toString().trim())) {
                    Intent intent = new Intent(mContext, MeInfoActivity.class);
                    intent.putExtra(Constants.PASS_STRING, etCard.getEditableText().toString());
                    intent.putExtra(Constants.SELECT_CARD_TYPE, idType);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(getString(R.string.no_card_num));
                }
                break;
        }
    }


    //证件类型：0-身份证，1-学生证，2-其他证件
    private void showSelectCard() {
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, btSave);
        dialog.isTitleShow(false).show();
        dialog.setTitle(getString(R.string.select_card_type));

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvPassagercerType.setText(stringItems[position]);
                idType = String.valueOf(position);
                dialog.dismiss();
            }
        });
    }
}
