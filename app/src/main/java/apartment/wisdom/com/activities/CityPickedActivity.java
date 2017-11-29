package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.util.List;

import apartment.wisdom.com.AAApplication;
import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.city.CityListAdapter;
import apartment.wisdom.com.adapters.city.ResultListAdapter;
import apartment.wisdom.com.beans.City;
import apartment.wisdom.com.beans.LocateState;
import apartment.wisdom.com.db.DBManager;
import apartment.wisdom.com.services.LocationService;
import apartment.wisdom.com.utils.StringUtils;
import apartment.wisdom.com.widgets.views.SideLetterBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/8  22:52
 * Email: yanchengdeng@gmail.com
 * Describle: 选择城市
 */
public class CityPickedActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.listview_all_city)
    ListView listviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView tvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar sideLetterBar;
    @BindView(R.id.listview_search_result)
    ListView listviewSearchResult;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    public static final String KEY_PICKED_CITY = "picked_city";
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;
    private LocationService locationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picked);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        locationService = ((AAApplication) getApplicationContext()).locationService;
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getOption());
        locationService.start();// 定位SDK
    }

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                locationService.start();// 定位SDK
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        listviewAllCity.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        sideLetterBar.setOverlay(overlay);
        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                listviewAllCity.setSelection(position);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    ivSearchClear.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    listviewSearchResult.setVisibility(View.GONE);
                } else {
                    ivSearchClear.setVisibility(View.VISIBLE);
                    listviewSearchResult.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        listviewSearchResult.setAdapter(mResultAdapter);
        listviewSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());
            }
        });
    }

    private void back(String city){
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {


        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(bdLocation.getCity())) {
                            String city = bdLocation.getCity();
                            String district = bdLocation.getDistrict();
                            String location = StringUtils.extractLocation(city, district);
                            if (bdLocation.getCity().contains(getString(R.string.city_single))) {
                                city = bdLocation.getCity().replace(getString(R.string.city_single), "");
                            }
                            mCityAdapter.updateLocateState(LocateState.SUCCESS, city);
                            locationService.stop();
                        }
                    }
                });
            }else{
                //定位失败
                mCityAdapter.updateLocateState(LocateState.FAILED, null);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /***
         * Stop location service
         */
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @OnClick({R.id.back, R.id.iv_search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                ivSearchClear.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                listviewSearchResult.setVisibility(View.GONE);
                break;
        }
    }
}
