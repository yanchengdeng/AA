package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/8  11:16
 * Email: yanchengdeng@gmail.com
 * Describle: 活动详情
 */
public class ActivityDetailActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_join)
    Button btnJoin;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ivRight.setVisibility(View.VISIBLE);
        tvTittle.setText(getString(R.string.tab_activity));
    }

    @OnClick({R.id.back, R.id.iv_right, R.id.btn_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right:
                new ShareAction(ActivityDetailActivity.this)
                        .withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener)
                        .open();
                break;
            case R.id.btn_join:
                openActivity(HotelDetailActivity.class);
                break;
        }
    }


    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调，可以用来处理等待框，或相关的文字提示

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort(platform.toString());
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

            ToastUtils.showShort(platform.toString()+t.getLocalizedMessage()+t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
