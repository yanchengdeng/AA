package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.DoCommendAlreadyEvnet;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
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
    private HotelOrderItem hotelOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_commend);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.hotel_comment));
        hotelOrderItem = (HotelOrderItem) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
        if (!TextUtils.isEmpty(hotelOrderItem.storeName)){
            tvHotelName.setText(hotelOrderItem.storeName);
        }
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
                doCommend(tvCommentContent.getEditableText().toString().trim());
                break;
        }
    }

    private void doCommend(String trim) {
        if (TextUtils.isEmpty(trim)){
            ToastUtils.showShort(R.string.no_commend_to_submit);
            return;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", hotelOrderItem.orderNo);
        data.put("roomHealthScore", appStarHealth.getRating());
        data.put("environmentScore", appStarEnv.getRating());
        data.put("hotelScore",appStarHotelser.getRating());
        data.put("deviceScore",appStarInstaser.getRating());
        data.put("customerEvaluate",trim);

        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"tripReview"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        LogUtils.w("dyc",response.body());
                        ToastUtils.showShort("已提交");
                        EventBus.getDefault().post(new DoCommendAlreadyEvnet(hotelOrderItem.orderNo));
                        finish();
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

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
                dialog.dismiss();
            }
        });
    }
}
