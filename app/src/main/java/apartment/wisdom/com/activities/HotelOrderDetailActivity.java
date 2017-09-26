package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.OrderDetailPriceAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.OrderDetailInfo;
import apartment.wisdom.com.beans.PreOrderInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.OrderStatus;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.widgets.views.MyListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static apartment.wisdom.com.R.id.detail_type_id;
import static apartment.wisdom.com.R.id.tv_room_price;

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
    @BindView(tv_room_price)
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
    @BindView(detail_type_id)
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
    @BindView(R.id.list_price)
    MyListView listPrice;

    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_order_detail);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.order_detail));
        orderNo = getIntent().getStringExtra(Constants.PASS_STRING);
        getOrderDetailInfo();
    }

    private void getOrderDetailInfo() {

        mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", orderNo);
        OkGo.<AAResponse<OrderDetailInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "orderInfo"))
                .execute(new NewsCallback<AAResponse<OrderDetailInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<OrderDetailInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        mSVProgressHUD.dismiss();
                        OrderDetailInfo orderDetailInfo = response.body().data;
                        if (orderDetailInfo != null) {
                            initOrderDetail(orderDetailInfo);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<OrderDetailInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        mSVProgressHUD.dismiss();
                    }
                });

    }

    private void initOrderDetail(OrderDetailInfo item) {
        if (!TextUtils.isEmpty(item.consumeSumPrice)) {
            tvDetailMoney.setText(item.consumeSumPrice);
        }

        if (!TextUtils.isEmpty(item.storeName)) {
            detailRoomTv.setText(item.storeName);
        }

//        tvRoomCount.setText("房费：" + getString(R.string.unit_rmb) + item.consumeSumPrice );
        tvRoomPrice.setText("押金：" + getString(R.string.unit_rmb) + item.roomDepositPrice +"(可退)");
        List<PreOrderInfo.CustomeItem>
            consumeList = item.consumeList;
        if (consumeList!=null && consumeList.size()>0){
            listPrice.setAdapter(new OrderDetailPriceAdapter(HotelOrderDetailActivity.this,consumeList));
        }

        if (!TextUtils.isEmpty(item.roomType)) {
            detailTypeId.setText(item.roomType + "  1房间");
        }

        if (!TextUtils.isEmpty(item.checkInTime)) {
            detailTimeTv.setText(item.checkInTime);
        }

        if (!TextUtils.isEmpty(item.checkOutTime)) {
            detailEndTv.setText(item.checkOutTime);
        }


        if (!TextUtils.isEmpty(item.orderStatus)) {
            if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_CANCLE_ORDER.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_CANCLE_ORDER.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_CHECK_OUT.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_CHECK_OUT.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_TIMEOUT.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_TIMEOUT.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_HAVE_LIVED.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_HAVE_LIVED.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_NO_PAY.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_NO_PAY.getName());
            } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_WAITING_LIVE.getType())) {
                tvOrderState.setText(OrderStatus.ORDER_STATUS_WAITING_LIVE.getName());
            }
        }

        if (!TextUtils.isEmpty(item.liveName)) {
            detailPeopleTv.setText(item.liveName);
        }

        if (!TextUtils.isEmpty(item.mobilePhone)) {
            detailPhotoTv.setText(item.mobilePhone);
        }

        if (!TextUtils.isEmpty(item.remark)) {
            detailRemarkTv.setText(item.remark);
        }

        if (!TextUtils.isEmpty(item.orderNo)) {
            detailIdTv.setText(item.orderNo);
        }

        if (!TextUtils.isEmpty(item.storeAddress)) {
            detailAddressTv.setText(item.storeAddress);
        }

        if (!TextUtils.isEmpty(item.storePhone)) {
            detailTelTv.setText(item.storePhone);
        }

        if (!TextUtils.isEmpty(item.cancelPromptInfo)) {
            detailCancleInfo.setText(Html.fromHtml(item.cancelPromptInfo).toString());
        }

        detailPayTv.setText(tvOrderState.getText().toString());


    }

    @OnClick({R.id.back, R.id.ll_hotel_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_hotel_name:
//                openActivity(HotelDetailActivity.class);
                break;
        }
    }
}
