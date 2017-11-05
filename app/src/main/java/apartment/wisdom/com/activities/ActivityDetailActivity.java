package apartment.wisdom.com.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.HomeAdInfoList;
import apartment.wisdom.com.commons.Constants;
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
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;
    @BindView(R.id.iv_empty_icon)
    ImageView ivEmptyIcon;
    @BindView(R.id.tv_empty_tips)
    TextView tvEmptyTips;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    private HomeAdInfoList homeAdInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ivRight.setVisibility(View.VISIBLE);
        tvTittle.setText(getString(R.string.tab_activity));
        homeAdInfoList = (HomeAdInfoList) getIntent().getSerializableExtra(Constants.PASS_OBJECT);


        if (!TextUtils.isEmpty(homeAdInfoList.activityTitle)) {
            tvTittle.setText(homeAdInfoList.activityTitle);
        }
        ivRight.setVisibility(View.GONE);

        webView.loadUrl(homeAdInfoList.activityUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        mSVProgressHUD.showWithStatus("加载中...");
        tvEmptyTips.setText("重新点击加载");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mSVProgressHUD.dismiss();
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                mSVProgressHUD.dismiss();
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                mSVProgressHUD.dismiss();
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSVProgressHUD.dismiss();
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_right,R.id.rl_no_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right:
                new ShareAction(ActivityDetailActivity.this)
                        .withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener)
                        .open();
                break;
            case R.id.rl_no_data:
                webView.reload();
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

            ToastUtils.showShort(platform.toString() + t.getLocalizedMessage() + t.getMessage());
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
