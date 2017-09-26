package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tubb.calendarselector.library.FullDay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HotelListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.HotelListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DayHourRoomType;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.utils.CalendarUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static apartment.wisdom.com.activities.CalendarChooseActivity.SUCCESS_SELECT_TIME;

/**
 * Author: 邓言诚  Create at : 17/8/8  15:21
 * Email: yanchengdeng@gmail.com
 * Describle:查找旅馆列表
 */
public class SearchHotalResultActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tv_city_condition)
    TextView tvCityCondition;
    @BindView(R.id.tv_time_condition)
    TextView tvTimeCondition;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private HotelListAdapter hotelListAdapter;

    private List<HotelListInfo.HotelListItem> hotelListInfos = new ArrayList<>();

    private static final int REQUEST_CITY = 0x110;
    private static final int REQEST_DATE = 0X111;

    private String area, areaStone, selectType;
    private FullDay stant_in, stant_out;
    private int diffDay;
    private View noDateView;
    private String count = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotal_result);
        ButterKnife.bind(this);
        area = getIntent().getStringExtra(Constants.PASS_STRING);
        selectType = getIntent().getStringExtra(Constants.PASS_SELECT_HOTLE_TYPE);
        stant_in = getIntent().getParcelableExtra(Constants.PASS_STAND_IN);
        stant_out = getIntent().getParcelableExtra(Constants.PASS_STAND_OUT);
        diffDay = getIntent().getIntExtra(Constants.PASS_DISTANCE_DAYS, 1);
        noDateView = getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) rvList.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无公寓");
        initTextTittle();
        initRecyle();
        getSearchData();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof PayOrRechargeSuccess) {
            finish();
        }
    }

    private void initTextTittle() {
        if (Integer.parseInt(count) > 0) {
            tvCityCondition.setText(area + "(" + count + ")");
        } else {
            tvCityCondition.setText(area);
        }
        tvTimeCondition.setText(String.format(getString(R.string.search_text_mix), new Object[]{stant_in.getMonth(), stant_in.getDay(), stant_out.getMonth(), stant_out.getDay(), diffDay}));
    }

    private void initRecyle() {

        swipeLayout.setOnRefreshListener(this);
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        hotelListAdapter = new HotelListAdapter(mContext, R.layout.adapter_hotel_layout, hotelListInfos);
        rvList.setAdapter(hotelListAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        hotelListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PASS_STRING, hotelListAdapter.getData().get(position).storeId);
                bundle.putString(Constants.PASS_SELECT_HOTLE_TYPE, selectType);
                bundle.putParcelable(Constants.PASS_STAND_IN, stant_in);
                bundle.putParcelable(Constants.PASS_STAND_OUT, stant_out);
                bundle.putString(Constants.PASS_STRING, area);
                bundle.putInt(Constants.PASS_DISTANCE_DAYS, diffDay);
                bundle.putSerializable(Constants.PASS_OBJECT, hotelListAdapter.getData().get(position));
                openActivity(HotelDetailActivity.class, bundle);
            }
        });
    }

    private void getSearchData() {
        hotelListAdapter.getData().clear();
        mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("area", area);
        //// TODO: 17/8/27
//      data.put("storeName", "鼓楼区");
        data.put("checkInTime", CalendarUtils.getInstant().getDateForamte(stant_in));
        data.put("checkOutTime", CalendarUtils.getInstant().getDateForamte(stant_out));
        data.put("checkInRoomType", selectType);
        OkGo.<AAResponse<HotelListInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "queryStore"))
                .execute(new NewsCallback<AAResponse<HotelListInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HotelListInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        mSVProgressHUD.dismiss();
                        HotelListInfo hotelListInfo = response.body().data;
                        if (hotelListInfo != null && !hotelListInfo.storeList.isEmpty()) {
                            hotelListAdapter.setNewData(hotelListInfo.storeList);
                            count = hotelListInfo.storeNum;
                        } else {
                            hotelListAdapter.setNewData(null);
                            hotelListAdapter.setEmptyView(noDateView);
                        }
                        initTextTittle();
                    }

                    @Override
                    public void onError(Response<AAResponse<HotelListInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        hotelListAdapter.setNewData(null);
                        hotelListAdapter.setEmptyView(noDateView);
                        count = "0";
                        initTextTittle();
                        mSVProgressHUD.dismiss();
                    }
                });
    }

    @OnClick({R.id.back, R.id.tv_city_condition, R.id.tv_time_condition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_city_condition:
                openActivity(CityPickedActivity.class, REQUEST_CITY);
                break;
            case R.id.tv_time_condition:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.PASS_STAND_IN, stant_in);
                bundle.putParcelable(Constants.PASS_STAND_OUT, stant_out);
                bundle.putBoolean(Constants.PASS_SELECT_HOTLE_TYPE, selectType.equals(DayHourRoomType.DAY_HOUR_ROOM_TYPE_DAY.getType()) ? false : true);
                openActivity(CalendarChooseActivity.class, bundle, REQEST_DATE);
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        hotelListAdapter.setEnableLoadMore(true);
        getSearchData();

    }

    @Override
    public void onLoadMoreRequested() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CITY) {
            if (data != null) {
                String city = data.getStringExtra(CityPickedActivity.KEY_PICKED_CITY);
                if (!TextUtils.isEmpty(city)) {
                    tvCityCondition.setText(city);
                    area = city;
                    getSearchData();
                }
            }
        } else if (requestCode == REQEST_DATE && resultCode == SUCCESS_SELECT_TIME) {
            if (data != null) {
                stant_in = data.getParcelableExtra(Constants.PASS_STAND_IN);
                stant_out = data.getParcelableExtra(Constants.PASS_STAND_OUT);
                diffDay = data.getExtras().getInt(Constants.STAND_IN_OUT_DISTANCE, 1);
                initTextTittle();
                getSearchData();
            }
        }
    }
}
