package apartment.wisdom.com.adapters;


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
import apartment.wisdom.com.enums.OrderEvaluteStatus;
import apartment.wisdom.com.utils.CalendarUtils;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 历史适配器
 */
public class HistoryListAdapter extends BaseQuickAdapter<HotelOrderItem, BaseViewHolder> {
    public HistoryListAdapter(@LayoutRes int layoutResId, @Nullable List<HotelOrderItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelOrderItem item) {
        helper.addOnClickListener(R.id.btn_trip_history_comment);
        helper.addOnClickListener(R.id.btn_trip_history_delete);
        ((TextView)(helper.getView(R.id.tv_trip_order_date))).setText(item.checkInTime);
        ((TextView)(helper.getView(R.id.tv_trip_hotel_name))).setText(item.storeName);
        ((TextView)(helper.getView(R.id.tv_trip_hotel_address))).setText(""+item.address);
        Glide.with(mContext).load(item.storeImage).into((ImageView) helper.getView(R.id.iv_trip_hotel_img));
        ((TextView)(helper.getView(R.id.tv_start_month_week))).setText(CalendarUtils.getInstant().getOrderMonthAndWeek(item.checkInTime));
        ((TextView)(helper.getView(R.id.tv_start_day))).setText(CalendarUtils.getInstant().getDay(item.checkInTime));
        ((TextView)(helper.getView(R.id.tv_sleep_num))).setText("共"+ TimeUtils.getFitTimeSpan(item.checkInTime,item.checkOutTime,new SimpleDateFormat("yyyy-MM-dd"),1).replace("天","")+"晚");
        ((TextView)(helper.getView(R.id.tv_end_month_week))).setText(CalendarUtils.getInstant().getOrderMonthAndWeek(item.checkOutTime));
        ((TextView)(helper.getView(R.id.tv_end_day))).setText(CalendarUtils.getInstant().getDay(item.checkOutTime));
        ((TextView)(helper.getView(R.id.tv_trip_room_type))).setText(item.roomType);
        ((TextView)(helper.getView(R.id.tv_trip_arrive_time))).setText(item.lastCheckInTime);

        if (item.evaluateStatus!=null && item.evaluateStatus.equals(OrderEvaluteStatus.ORDER_EVALUTE_STATUS_NO.getType())){
            helper.getView(R.id.btn_trip_history_comment).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.btn_trip_history_comment).setVisibility(View.GONE);
        }

    }
}
