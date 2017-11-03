package apartment.wisdom.com.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.rollviewpager.RollPagerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tubb.calendarselector.library.FullDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.AAApplication;
import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.CalendarChooseActivity;
import apartment.wisdom.com.activities.CityPickedActivity;
import apartment.wisdom.com.activities.SearchHotalResultActivity;
import apartment.wisdom.com.adapters.GalleryPagerAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.HomeAdInfo;
import apartment.wisdom.com.beans.HomeAdInfoList;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DayHourRoomType;
import apartment.wisdom.com.services.LocationService;
import apartment.wisdom.com.utils.CalendarUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.widgets.views.SelectRoomTypeView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;
import static apartment.wisdom.com.activities.CalendarChooseActivity.SUCCESS_SELECT_TIME;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Author: 邓言诚  Create at : 17/8/7  16:53
 * Email: yanchengdeng@gmail.com
 * Describle: 首页
 */
@RuntimePermissions
public class HomeFragment extends Fragment {


    @BindView(R.id.roll_view_pager)
    RollPagerView rollViewPager;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_go_location)
    TextView tvGoLocation;
    @BindView(R.id.tv_stay_in_date)
    TextView tvStayInDate;
    @BindView(R.id.tv_stay_in_week)
    TextView tvStayInWeek;
    @BindView(R.id.rl_stay_in)
    RelativeLayout rlStayIn;
    @BindView(R.id.tv_stay_days)
    TextView tvStayDays;
    @BindView(R.id.tv_stay_out_date)
    TextView tvStayOutDate;
    @BindView(R.id.tv_stay_out_week)
    TextView tvStayOutWeek;
    @BindView(R.id.rl_stay_out)
    RelativeLayout rlStayOut;
    @BindView(R.id.tv_choose_condition)
    TextView tvChooseCondition;
    @BindView(R.id.bt_search_hotel)
    Button btSearchHotel;
    @BindView(R.id.iv_jifen)
    ImageView ivJifen;
    @BindView(R.id.select_room_type)
    SelectRoomTypeView selectRoomType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context context;
    private LocationService locationService;
    private BDLocationListener locationListener;

    private static final int REQUEST_CODE_PICK_CITY = 0x1111;

    private FullDay stant_in, stant_out;
    private int diffdays = 1;

    private static final int REQUEST_SELECT_DATE = 0x1112;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocatioPermission();
        stant_in = CalendarUtils.getInstant().getDefalutStandIn();
        stant_out = CalendarUtils.getInstant().getDefaulStandOut();

    }

    private void checkLocatioPermission() {
        HomeFragmentPermissionsDispatcher.showLocationWithCheck(this);
    }


    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showNeverAskForCamera() {
        final MaterialDialog dialog = new MaterialDialog(getActivity());
        dialog.content(
                "如需要使用该功能，请打开定位权限")//
                .btnText("取消", "确定")//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        try {
                            Uri packageURI = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        HomeFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showLocation() {
        startLocationService();
    }

    private void startLocationService() {
        locationService = ((AAApplication) context.getApplicationContext()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationListener = getmListener();
        locationService.registerListener(locationListener);
        //注册监听
        locationService.setLocationOption(locationService.getOption());
        locationService.start();// 定位SDK
    }

    private BDLocationListener getmListener() {
        BDLocationListener mListener = new BDLocationListener() {


            @Override
            public void onReceiveLocation(final BDLocation bdLocation) {
                if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(bdLocation.getCity())) {
                                String city = bdLocation.getCity();
                                if (bdLocation.getCity().contains(getString(R.string.city_single))) {
                                    city = bdLocation.getCity().replace(getString(R.string.city_single), "");
                                }
                                tvLocation.setText(city);
                                stopLocation();
                            }
                        }
                    });
                }
            }
        };
        return mListener;
    }


    @Override
    public void onStop() {
        super.onStop();
        /***
         * Stop location service
         */
        stopLocation();
    }


    private void stopLocation() {
        if (locationService != null && locationListener != null) {
            locationService.unregisterListener(locationListener); //注销掉监听
            locationService.stop();
        }
    }
    protected ImmersionBar mImmersionBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ImmersionBar.setTitleBar(getActivity(), toolbar);
//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.statusBarColorTransformEnable(false)
//                .navigationBarColor(R.color.transparent)
//                .init();
        toolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                , ContextCompat.getColor(context, R.color.colorPrimary), 0.2f));
        initData();
        getAd();
        return view;
    }

    private void initData() {
        tvStayDays.setText(String.format(getString(R.string.total_night), diffdays));
        tvStayInDate.setText(String.format(getString(R.string.month_day), new Object[]{stant_in.getMonth(), stant_in.getDay()}));
        tvStayOutDate.setText(String.format(getString(R.string.month_day), new Object[]{stant_out.getMonth(), stant_out.getDay()}));
        tvStayInWeek.setText(String.valueOf(stant_in.getDay()));
        tvStayOutWeek.setText(String.valueOf(stant_out.getDay()));
        tvStayInWeek.setText(CalendarUtils.getInstant().getWeekNameByDate(stant_in));
        tvStayOutWeek.setText(CalendarUtils.getInstant().getWeekNameByDate(stant_out));

        selectRoomType.rlDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRoomType.setSelectDay();
                tvStayDays.setVisibility(View.VISIBLE);
                rlStayOut.setVisibility(View.VISIBLE);
            }
        });

        selectRoomType.rlHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRoomType.setSelectHour();
                tvStayDays.setVisibility(View.GONE);
                rlStayOut.setVisibility(View.GONE);
            }
        });

    }


    private void getAd() {

        Map<String, Object> data = new HashMap<String, Object>();
        OkGo.<AAResponse<HomeAdInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .params("data", ParamsUtils.getParams(data, "getActivity"))
                .execute(new NewsCallback<AAResponse<HomeAdInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HomeAdInfo>> response) {
                        HomeAdInfo homeAdInfos = response.body().data;
                        if (!homeAdInfos.activityList.isEmpty()) {
                            initAdIinfo(homeAdInfos.activityList);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<HomeAdInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    private void initAdIinfo(List<HomeAdInfoList> homeAdInfos) {
        rollViewPager.setAdapter(new GalleryPagerAdapter(homeAdInfos, getActivity()));
    }


    @OnClick({R.id.tv_location, R.id.rl_stay_in, R.id.rl_stay_out, R.id.tv_go_location, R.id.tv_choose_condition, R.id.bt_search_hotel, R.id.iv_jifen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                startActivityForResult(new Intent(context, CityPickedActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            case R.id.rl_stay_in:
            case R.id.rl_stay_out:
                Intent intent = new Intent(getActivity(), CalendarChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.PASS_STAND_IN, stant_in);
                bundle.putParcelable(Constants.PASS_STAND_OUT, stant_out);
                bundle.putBoolean(Constants.PASS_SELECT_HOTLE_TYPE, selectRoomType.getSelectType().equals(DayHourRoomType.DAY_HOUR_ROOM_TYPE_DAY.getType()) ? false : true);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_SELECT_DATE);
                break;
            case R.id.tv_go_location:
                if (locationService != null) {
                    locationService.start();
                    locationService.registerListener(locationListener);
                } else {
                    HomeFragmentPermissionsDispatcher.showLocationWithCheck(this);
                }
                break;
            case R.id.tv_choose_condition:
                break;
            case R.id.bt_search_hotel:
                Bundle bundleHotel = new Bundle();
                bundleHotel.putParcelable(Constants.PASS_STAND_IN, stant_in);
                bundleHotel.putParcelable(Constants.PASS_STAND_OUT, stant_out);
                bundleHotel.putString(Constants.PASS_STRING, tvLocation.getText().toString());
                bundleHotel.putString(Constants.PASS_SELECT_HOTLE_TYPE, selectRoomType.getSelectType());
                bundleHotel.putInt(Constants.PASS_DISTANCE_DAYS, diffdays);
                ((BaseActivity) context).openActivity(SearchHotalResultActivity.class, bundleHotel);
                break;
            case R.id.iv_jifen:
                ToastUtils.showShort(getString(R.string.do_later));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickedActivity.KEY_PICKED_CITY);
                tvLocation.setText(city);
            }
        } else if (requestCode == REQUEST_SELECT_DATE && resultCode == SUCCESS_SELECT_TIME) {
            if (data != null) {
                stant_in = data.getParcelableExtra(Constants.PASS_STAND_IN);
                stant_out = data.getParcelableExtra(Constants.PASS_STAND_OUT);
                diffdays = data.getExtras().getInt(Constants.STAND_IN_OUT_DISTANCE, 1);
                initData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

