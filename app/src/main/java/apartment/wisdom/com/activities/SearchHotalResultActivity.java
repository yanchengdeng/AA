package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HotelListAdapter;
import apartment.wisdom.com.beans.HotelListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.RefreshSwiperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/8  15:21
 * Email: yanchengdeng@gmail.com
 * Describle:查找旅馆列表
 */
public class SearchHotalResultActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener  {

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

    private  List<HotelListInfo>   hotelListInfos = new ArrayList<>();

    private static final int REQUEST_CITY = 0x110;
    private static final int REQEST_DATE = 0X111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotal_result);
        ButterKnife.bind(this);
        initRecyle();
    }

    private void initRecyle() {
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        hotelListInfos.add(new HotelListInfo());
        swipeLayout.setOnRefreshListener(this);
        RefreshSwiperUtils.setRecleColor(swipeLayout);
        hotelListAdapter = new HotelListAdapter(R.layout.adapter_hotel_layout,hotelListInfos);
        rvList.setAdapter(hotelListAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        hotelListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openActivity(HotelDetailActivity.class);
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
                openActivity(CityPickedActivity.class,REQUEST_CITY);
                break;
            case R.id.tv_time_condition:
                openActivity(CalendarChooseActivity.class,REQEST_DATE);
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        hotelListAdapter.setEnableLoadMore(true);

    }

    @Override
    public void onLoadMoreRequested() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CITY){
            if (data!=null){
                String city = data.getStringExtra(Constants.PASS_STRING);
                if (!TextUtils.isEmpty(city)){
                    tvCityCondition.setText(city);
                }
            }
        }else if (requestCode==REQEST_DATE){

        }
    }
}
