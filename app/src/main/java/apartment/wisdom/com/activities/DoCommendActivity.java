package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/16  13:47
 * Email: yanchengdeng@gmail.com
 * Describle: 点评功能
 */
public class DoCommendActivity extends BaseActivity {

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
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_tour_type)
    TextView tvTourType;
    @BindView(R.id.image_arrow_right)
    ImageView imageArrowRight;
    @BindView(R.id.lay_tour_type)
    RelativeLayout layTourType;
    @BindView(R.id.app_star_health)
    RatingBar appStarHealth;
    @BindView(R.id.app_star_env)
    RatingBar appStarEnv;
    @BindView(R.id.app_star_hotelser)
    RatingBar appStarHotelser;
    @BindView(R.id.app_star_instaser)
    RatingBar appStarInstaser;
    @BindView(R.id.tv_comtent_count)
    TextView tvComtentCount;
    @BindView(R.id.tv_comment_content)
    EditText tvCommentContent;
    @BindView(R.id.lay_submit)
    LinearLayout laySubmit;
    @BindView(R.id.bt_submit_comment)
    TextView btSubmitComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_commend);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.hotel_comment));
        tvCommentContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (50 - editable.toString().length() >= 0) {
                    tvComtentCount.setText(editable.toString().length() + "/50");
                } else if (editable.toString().length() > 50) {
                    tvComtentCount.setText("50/50");
                }

            }
        });
    }

    @OnClick({R.id.back, R.id.lay_tour_type, R.id.bt_submit_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.lay_tour_type:
                openSelect();
                break;
            case R.id.bt_submit_comment:
                mSVProgressHUD.showSuccessWithStatus("已提交", SVProgressHUD.SVProgressHUDMaskType.Clear);
                break;
        }
    }

    private void openSelect() {

        final String[] stringItems = {"商务出差", "朋友出游", "情侣出游"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, btSubmitComment);
        dialog.isTitleShow(false).show();
        dialog.setTitle("出游类型");

        dialog.setOnOperItemClickL(new OnOperItemClickL() {

            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvTourType.setText(stringItems[position]);
            }
        });
    }
}
