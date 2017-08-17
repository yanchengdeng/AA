package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNameActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.bt_save)
    TextView btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.name));
    }

    @OnClick({R.id.back, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_save:
                if (!TextUtils.isEmpty(etName.getEditableText().toString().trim())){
                    Intent intent = new Intent(mContext,MeInfoActivity.class);
                    intent.putExtra(Constants.PASS_STRING,etName.getEditableText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    ToastUtils.showShort(getString(R.string.name_not_empty));
                }
                break;
        }
    }
}
