package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HotelEquitmentAdapter;
import apartment.wisdom.com.widgets.views.MyGridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelIntroduceActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.gv_enqiment)
    MyGridView gvEnqiment;
    @BindView(R.id.tv_hotel_call)
    TextView tvHotelCall;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_introduce);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.hotel_detail));
        gvEnqiment.setAdapter(new HotelEquitmentAdapter(mContext));
    }

    @OnClick({R.id.back, R.id.tv_hotel_call, R.id.iv_map, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_hotel_call:
                setTvHotelCall("021-020303030");
                break;
            case R.id.iv_map:
            case R.id.tv_address:
                openActivity(MapActivity.class);
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
