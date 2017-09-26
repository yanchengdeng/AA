package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.enums.OrderStatus;
import apartment.wisdom.com.utils.CalendarUtils;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 旅行助手适配器
 */
public class AssistantListAdapter extends BaseQuickAdapter<HotelOrderItem, BaseViewHolder> {
    private Context context;
    public AssistantListAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<HotelOrderItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelOrderItem item) {
        helper.addOnClickListener(R.id.btn_cancle_order);
        helper.addOnClickListener(R.id.btn_app_open_door);
        helper.addOnClickListener(R.id.btn_auto_out);
        helper.addOnClickListener(R.id.btn_check_in_code);
        if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_WAITING_LIVE.getType())){
            helper.getView(R.id.btn_auto_out).setVisibility(View.GONE);
            helper.getView(R.id.btn_app_open_door).setVisibility(View.GONE);
            helper.getView(R.id.btn_cancle_order).setVisibility(View.VISIBLE);
            helper.getView(R.id.btn_check_in_code).setVisibility(View.VISIBLE);
        }else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_HAVE_LIVED.getType())){
            helper.getView(R.id.btn_auto_out).setVisibility(View.VISIBLE);
            helper.getView(R.id.btn_app_open_door).setVisibility(View.GONE);
            helper.getView(R.id.btn_cancle_order).setVisibility(View.GONE);
            helper.getView(R.id.btn_check_in_code).setVisibility(View.GONE);
        }

        ((TextView)(helper.getView(R.id.tv_trip_order_date))).setText(item.checkInTime);
        ((TextView)(helper.getView(R.id.tv_trip_hotel_name))).setText(item.storeName);
//        if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_WAITING_LIVE.getType())){
//            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_WAITING_LIVE.getName());
//        }else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getType())){
//            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getName());
//        }else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getType())){
//            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getName());
//        }else{
//            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_CHECK_OUT.getName());
//        }


        if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_CANCLE_ORDER.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_CANCLE_ORDER.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_CHECK_OUT.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_CHECK_OUT.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_TIMEOUT.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_TIMEOUT.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_HAVE_LIVED.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_HAVE_LIVED.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_NO_PAY.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_NO_PAY.getName());
        } else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_WAITING_LIVE.getType())) {
            ((TextView)(helper.getView(R.id.tv_trip_pay_state))).setText(OrderStatus.ORDER_STATUS_WAITING_LIVE.getName());
        }
        
        
        
        
        

        ((TextView)(helper.getView(R.id.tv_trip_hotel_address))).setText(""+item.address);
        Glide.with(context).load(item.storeImage).into((ImageView) helper.getView(R.id.iv_trip_hotel_img));
        ((TextView)(helper.getView(R.id.tv_start_month_week))).setText(CalendarUtils.getInstant().getOrderMonthAndWeek(item.checkInTime));
        ((TextView)(helper.getView(R.id.tv_start_day))).setText(CalendarUtils.getInstant().getDay(item.checkInTime));
        ((TextView)(helper.getView(R.id.tv_sleep_num))).setText("共"+TimeUtils.getFitTimeSpan(item.checkInTime,item.checkOutTime,new SimpleDateFormat("yyyy-MM-dd"),1).replace("天","")+"晚");
        ((TextView)(helper.getView(R.id.tv_end_month_week))).setText(CalendarUtils.getInstant().getOrderMonthAndWeek(item.checkOutTime));
        ((TextView)(helper.getView(R.id.tv_end_day))).setText(CalendarUtils.getInstant().getDay(item.checkOutTime));
        ((TextView)(helper.getView(R.id.tv_trip_room_type))).setText(item.roomType);
        ((TextView)(helper.getView(R.id.tv_trip_arrive_time))).setText(item.lastCheckInTime);



    }
}
