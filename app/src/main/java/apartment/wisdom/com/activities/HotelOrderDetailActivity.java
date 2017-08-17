package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/16  13:37
 * Email: yanchengdeng@gmail.com
 * Describle: 酒店订单详情
 */
public class HotelOrderDetailActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.detail_room_tv)
    TextView detailRoomTv;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_detail_money)
    TextView tvDetailMoney;
    @BindView(R.id.tv_choose_by_self)
    TextView tvChooseBySelf;
    @BindView(R.id.tv_room_count)
    TextView tvRoomCount;
    @BindView(R.id.tv_room_price)
    TextView tvRoomPrice;
    @BindView(R.id.iv_show_more)
    ImageView ivShowMore;
    @BindView(R.id.detail_delay_tv)
    TextView detailDelayTv;
    @BindView(R.id.detail_exit_tv)
    TextView detailExitTv;
    @BindView(R.id.ll_all_day_show)
    LinearLayout llAllDayShow;
    @BindView(R.id.detail_name_tv)
    TextView detailNameTv;
    @BindView(R.id.detail_type_id)
    TextView detailTypeId;
    @BindView(R.id.ll_hotel_name)
    LinearLayout llHotelName;
    @BindView(R.id.detail_time_tv)
    TextView detailTimeTv;
    @BindView(R.id.detail_end_tv)
    TextView detailEndTv;
    @BindView(R.id.detail_day_tv)
    TextView detailDayTv;
    @BindView(R.id.detail_end_ll)
    LinearLayout detailEndLl;
    @BindView(R.id.detail_pay_tv)
    TextView detailPayTv;
    @BindView(R.id.iv_pay)
    ImageView ivPay;
    @BindView(R.id.tv_room_names)
    TextView tvRoomNames;
    @BindView(R.id.ll_room_list)
    LinearLayout llRoomList;
    @BindView(R.id.continue_view)
    View continueView;
    @BindView(R.id.detail_people_tv)
    TextView detailPeopleTv;
    @BindView(R.id.detail_photo_tv)
    TextView detailPhotoTv;
    @BindView(R.id.detail_title_tv)
    TextView detailTitleTv;
    @BindView(R.id.detail_remark_tv)
    TextView detailRemarkTv;
    @BindView(R.id.detail_id_tv)
    TextView detailIdTv;
    @BindView(R.id.detail_address_tv)
    TextView detailAddressTv;
    @BindView(R.id.detail_tel_tv)
    TextView detailTelTv;
    @BindView(R.id.detail_cancle_info)
    TextView detailCancleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_order_detail);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.order_detail));
    }

    @OnClick({R.id.back, R.id.ll_hotel_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_hotel_name:
                openActivity(HotelDetailActivity.class);
                break;
        }
    }
}
