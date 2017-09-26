package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HotelEquitmentAdapter;
import apartment.wisdom.com.beans.HotelListInfo;
import apartment.wisdom.com.commons.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//酒店详情
public class HotelIntroduceActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.gv_enqiment)
    GridView gvEnqiment;
    @BindView(R.id.tv_hotel_call)
    TextView tvHotelCall;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    private HotelListInfo.HotelListItem hotelListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_introduce);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.hotel_detail));
        hotelListItem = (HotelListInfo.HotelListItem) getIntent().getSerializableExtra(Constants.PASS_OBJECT);

        tvAddress.setText(hotelListItem.address);
        tvIntroduce.setText(hotelListItem.storeRemark);
        String[] devicesStore;
        if (!TextUtils.isEmpty(hotelListItem.storeDevice)) {
            if (hotelListItem.storeDevice.contains(" ")) {
                devicesStore = hotelListItem.storeDevice.split(" ");
            } else {
                devicesStore = new String[]{hotelListItem.storeDevice};
            }
        } else {
            devicesStore = new String[]{"暂无"};
        }

        if (!TextUtils.isEmpty(hotelListItem.coordinate) && hotelListItem.coordinate.contains(",")){
            String[] mapCoordinate = hotelListItem.coordinate.split(",");
            String mapurl = "http://api.map.baidu.com/staticimage/v2?ak="+"PvyN3Dn6UR8QIGrjPLlZBtHAYPiR5YFS"+"&mcode=70:1A:8B:0B:81:E3:BE:66:EC:12:A1:1E:00:1D:4E:90:C0:0A:84:7D;apartment.wisdom.com&center="+mapCoordinate[1]+","+mapCoordinate[0]+"&width=300&height=200&zoom=18";
            Glide.with(mContext).load(mapurl).into(ivMap);
        }
        if (!TextUtils.isEmpty(hotelListItem.phone)) {
            tvHotelCall.setText(String.format(getString(R.string.call_phone_info),new Object[]{hotelListItem.phone}));
        }

        gvEnqiment.setAdapter(new HotelEquitmentAdapter(mContext, devicesStore));
    }

    @OnClick({R.id.back, R.id.tv_hotel_call, R.id.iv_map, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_hotel_call:
                setTvHotelCall(hotelListItem.phone);
                break;
            case R.id.iv_map:
            case R.id.tv_address:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PASS_STRING, hotelListItem.coordinate);
                bundle.putString(Constants.PASS_ADDRESS, hotelListItem.address);
                openActivity(MapActivity.class, bundle);
                break;
        }
    }

    private void setTvHotelCall(final String phone) {
        final String[] stringItems = {phone};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, ivMap);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(IntentUtils.getDialIntent(phone));
                dialog.dismiss();
            }
        });
    }
}
