package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import apartment.wisdom.com.AAApplication;
import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.services.LocationService;
import apartment.wisdom.com.utils.AMapUtil;
import apartment.wisdom.com.utils.CoodinateCovertor;
import apartment.wisdom.com.utils.LngLat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author: 邓言诚  Create at : 17/8/14  23:26
 * Email: yanchengdeng@gmail.com
 * Describle:  地图显示
 */
public class MapActivity extends BaseActivity {

    @BindView(R.id.bmapView)
    com.baidu.mapapi.map.MapView bmapView;
    @BindView(R.id.iv_map_line)
    ImageView ivMapLine;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    LatLng point;
    private LatLng locaiton;
    private LocationService locationService;
    private String coordinate;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        coordinate = getIntent().getStringExtra(Constants.PASS_STRING);
        address = getIntent().getStringExtra(Constants.PASS_ADDRESS);
        ButterKnife.bind(this);


        //119.313287,26.112893
        //定义Maker坐标点
        if (TextUtils.isEmpty(coordinate)) {
            finish();
            return;
        }

        String[] pointLatLng = coordinate.split(",");

        point = new LatLng(Double.parseDouble(pointLatLng[0]), Double.parseDouble(pointLatLng[1]));
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.detail_map_pin_big);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        bmapView.getMap().addOverlay(option);


        //创建InfoWindow展示的view
        TextView button = new TextView(getApplicationContext());
        button.setTextColor(getResources().getColor(R.color.white));
        button.setText(address);
        button.setBackground(getResources().getDrawable(R.drawable.map_location_bg));

//定义用于显示该InfoWindow的坐标点
//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(button, point, -120);
//显示InfoWindow
        bmapView.getMap().showInfoWindow(mInfoWindow);


        //已到目标位置
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(point)
                .zoom(18)
                .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
        bmapView.getMap().setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void onStart() {
        super.onStart();
        locationService = ((AAApplication) getApplicationContext()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getOption());
        locationService.start();// 定位SDK
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
                locaiton = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            }
        }
    };

    @OnClick({R.id.iv_map_line, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_map_line:
                goNav();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void goNav() {
        final String[] stringItems = {getString(R.string.use_baidu_nav), getString(R.string.use_gaode_nav)};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, bmapView);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //百度导航
                    if (AMapUtil.isInstallByRead("com.baidu.BaiduMap")) {
                        if (locaiton != null) {
                            AMapUtil.getToNaviBaidu(mContext, address, locaiton, point.latitude, point.longitude);
                        } else {
                            ToastUtils.showShort(getString(R.string.check_location_is_right));
                        }
                    } else {
                        ToastUtils.showShort(R.string.not_install_baidumap);
//                        Snackbar snackbar = Snackbar.make(ivMapLine, getString(R.string.not_install_baidumap), Toast.LENGTH_SHORT);
//                        ColoredSnackbar.alert(snackbar).show();
                    }

                } else if (position == 1) {
                    //高德导航
                    if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {

                        LngLat gaode = CoodinateCovertor.bd_decrypt(new LngLat(point.longitude, point.latitude));
                        AMapUtil.goToNaviActivity(mContext, "amap", address, String.valueOf(gaode.getLantitude()), String.valueOf(gaode.getLongitude()), "0", "4");
                    } else {
//                        Snackbar snackbar = Snackbar.make(ivMapLine, getString(R.string.not_install_gaodemap), Toast.LENGTH_SHORT);
//                        ColoredSnackbar.alert(snackbar).show();
                        ToastUtils.showShort(R.string.not_install_gaodemap);
                    }

                }
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();
        /***
         * Stop location service
         */
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }
}
