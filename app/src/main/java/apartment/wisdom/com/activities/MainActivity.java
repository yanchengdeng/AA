package apartment.wisdom.com.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.VersionInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginOutSuccessEvent;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.fragments.ActivityFragment;
import apartment.wisdom.com.fragments.AssistantFragment;
import apartment.wisdom.com.fragments.HomeFragment;
import apartment.wisdom.com.fragments.MeFragment;
import apartment.wisdom.com.services.UpdateService;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邓言诚  Create at : 17/8/8  11:22
 * Email: yanchengdeng@gmail.com
 * Describle: 首页
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    public ViewPager viewpager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        initFragments();
        EventBus.getDefault().register(this);
        checkPermission();
    }



    private void checkPermission() {

        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {

                getVersionInfo();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // TODO ...
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof PayOrRechargeSuccess) {
            viewpager.setCurrentItem(2);
        } else if (event instanceof LoginOutSuccessEvent) {
            LoginUtils.setLoginStatus(false);
            SPUtils.getInstance().put(Constants.USER_INFO, "");
            openActivity(LoginActivity.class);
        }
    }


    private void initFragments() {

        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of(getString(R.string.tab_home), HomeFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.tab_activity), ActivityFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.tab_assistant), AssistantFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.tab_me), MeFragment.class));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);


        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        viewpagertab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View tabView = inflater.inflate(R.layout.custom_tab_icon, container,
                        false);
                ImageView imageView = (ImageView) tabView.findViewById(R.id.custom_tab_icon);
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.tab_home);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.tab_activity);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.tab_assistant);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.tab_me);
                        break;
                }
                return tabView;
            }
        });
        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);


    }

    private void getVersionInfo() {
        Map<String, Object> data = new HashMap<String, Object>();
        OkGo.<AAResponse<VersionInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "getNewAppVersion"))
                .execute(new NewsCallback<AAResponse<VersionInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<VersionInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        VersionInfo versionInfo = response.body().data;
                        if (versionInfo != null) {
                            if (!TextUtils.isEmpty(versionInfo.versioncode)) {
                                int currentCode = AppUtils.getAppVersionCode();
                                if (Integer.parseInt(versionInfo.versioncode)>currentCode) {
                                    if (!TextUtils.isEmpty(versionInfo.appUrl)) {
                                        showUpdateDialog(versionInfo);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<VersionInfo>> response) {
//                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }

    private void showUpdateDialog(final VersionInfo versionInfo) {

        final NormalDialog dialog = new NormalDialog(mContext);
        dialog.title("新版本更新："+versionInfo.versionname);
        dialog.titleTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.titleLineColor(getResources().getColor(R.color.colorPrimary));
        dialog.titleTextSize(18);
//        dialog.setTitle("新版本更新："+versionInfo.appVersion);
        dialog.content(TextUtils.isEmpty(versionInfo.appRemark)?"暂无版本更新信息":versionInfo.appRemark)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        Intent intent = new Intent(mContext, UpdateService.class);
                        intent.putExtra("apkUrl", versionInfo.appUrl);
                        startService(intent);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return doubleClickExist();
        }
        return super.onKeyDown(keyCode, event);
    }

    private long mExitTime;

    /****
     * 连续两次点击退出
     */
    private boolean doubleClickExist() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort(R.string.double_click_exit);
            mExitTime = System.currentTimeMillis();
            return true;
        } else {
            finish();
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }


}
