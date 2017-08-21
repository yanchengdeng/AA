package apartment.wisdom.com.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
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
import com.jude.rollviewpager.RollPagerView;
import com.tubb.calendarselector.library.FullDay;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.AAApplication;
import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.CalendarChooseActivity;
import apartment.wisdom.com.activities.CityPickedActivity;
import apartment.wisdom.com.activities.SearchHotalResultActivity;
import apartment.wisdom.com.adapters.GalleryPagerAdapter;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.services.LocationService;
import apartment.wisdom.com.utils.CalendarUtils;
import apartment.wisdom.com.widgets.views.SelectRoomTypeView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static apartment.wisdom.com.activities.CalendarChooseActivity.SUCCESS_SELECT_TIME;

/**
 * Author: 邓言诚  Create at : 17/8/7  16:53
 * Email: yanchengdeng@gmail.com
 * Describle: 首页
 */
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
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.select_room_type)
    SelectRoomTypeView selectRoomType;
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
        startLocationService();
        stant_in = CalendarUtils.getInstant().getDefalutStandIn();
        stant_out = CalendarUtils.getInstant().getDefaulStandOut();

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
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
        List<String> adInfoses = new ArrayList<>();
        adInfoses.add(Constants.JZ_PIC);
        adInfoses.add(Constants.JZ_PIC_1);
        adInfoses.add(Constants.JZ_PIC_2);
        rollViewPager.setAdapter(new GalleryPagerAdapter(adInfoses, getActivity()));
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
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_SELECT_DATE);
                break;
            case R.id.tv_go_location:
                locationService.start();
                locationService.registerListener(locationListener);
                break;
            case R.id.tv_choose_condition:
                break;
            case R.id.bt_search_hotel:
                ((BaseActivity) context).openActivity(SearchHotalResultActivity.class);
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
}

