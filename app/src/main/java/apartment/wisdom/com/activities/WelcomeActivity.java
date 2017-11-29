package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.SplashImageInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends FragmentActivity {

    @BindView(R.id.iv_ad_bg)
    ImageView ivAdBg;
    @BindView(R.id.tv_second_jump)
    TextView tvSecondJump;
    @BindView(R.id.guide_ui)
    RelativeLayout guideUi;
    @BindView(R.id.roll_view_pager)
    RollPagerView pager;
    @BindView(R.id.btnHome)
    Button btnHome;

    private CountDownTimer countDownTimer;

    private GalleryPagerAdapter adapter;
    private int[] images = {
            R.mipmap.loading_1,
            R.mipmap.loading_2,
            R.mipmap.loading_3,
            R.mipmap.loading_4,
            R.mipmap.loading_5
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        countDownTimer = new CountDownTimer(5100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSecondJump.setText(String.format("%s 跳过", String.valueOf((int) (millisUntilFinished / 1000))));
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        };

        boolean firstTimeUse = SPUtils.getInstance().getBoolean(Constants.FRIST_OPEN_APP, true);
        if (firstTimeUse) {
            pager.setVisibility(View.VISIBLE);
            initGuideGallery();
        } else {
            guideUi.setVisibility(View.VISIBLE);
            pager.setVisibility(View.GONE);
            initLaunchLogo();
        }
    }

    private void getAdImage() {
        Map<String, Object> data = new HashMap<String, Object>();
        OkGo.<AAResponse<SplashImageInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"getStartUpAd"))
                .execute(new NewsCallback<AAResponse<SplashImageInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<SplashImageInfo>> response) {
                        SplashImageInfo imageInfo = response.body().data;
                        if (!TextUtils.isEmpty(imageInfo.homeAdImage)){
                            SPUtils.getInstance().put(Constants.AD_IMAGE,imageInfo.homeAdImage);
                            Glide.with(WelcomeActivity.this).load(imageInfo.homeAdImage).into(ivAdBg);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<SplashImageInfo>> response) {
                      requestCacheImage();
                    }
                });
    }

    //缓存图片加载
    private void requestCacheImage() {
        String string = SPUtils.getInstance().getString(Constants.AD_IMAGE);
        if (!TextUtils.isEmpty(string)){
            Glide.with(this).load(string).into(ivAdBg);
        }
    }

    private void initGuideGallery() {

        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        btnHome = (Button) findViewById(R.id.btnHome);
        adapter = new GalleryPagerAdapter();
        pager.setAdapter(adapter);
        pager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btnHome.setVisibility(View.VISIBLE);
                    btnHome.startAnimation(fadeIn);
                } else {
                    btnHome.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(WelcomeActivity.this);
            item.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()));
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            item.setAdjustViewBounds(true);
            item.setImageResource(images[position]);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void initLaunchLogo() {
        getMainAd();
    }

    private void getMainAd() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                countDownTimer.start();
                tvSecondJump.setVisibility(View.VISIBLE);
                requestCacheImage();
                getAdImage();
            }
        },1500);

    }


    @OnClick({R.id.tv_second_jump, R.id.btnHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_second_jump:
            case R.id.btnHome:
                tvSecondJump.setClickable(false);
                tvSecondJump.setVisibility(View.GONE);
                btnHome.setClickable(false);
                btnHome.setVisibility(View.GONE);
                SPUtils.getInstance().put(Constants.FRIST_OPEN_APP, false);
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
