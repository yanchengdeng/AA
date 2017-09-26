package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.enums.OrderEvaluteStatus;
import apartment.wisdom.com.enums.OrderStatus;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
 *
 订单状态（0-待支付，1-待入住，2-已入住，3-退房申请(退房申请已提交，请等待查房)，
 4-已查房(物件有损坏请联系前台人员)，5-确认退房(请自助机办理退房)，
 6-退款成功，7-取消订单,9-已离店)

 * Describle: 酒店订单适配器
*/
public class HotelOrderListAdapter extends BaseQuickAdapter<HotelOrderItem,BaseViewHolder> {
    private Context context;
    public HotelOrderListAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<HotelOrderItem> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, HotelOrderItem item) {
        helper.addOnClickListener(R.id.bt_order_pre);
        helper.addOnClickListener(R.id.bt_order_delete);
        helper.addOnClickListener(R.id.bt_do_comment);
        helper.addOnClickListener(R.id.btn_cancle_order);
        ((TextView) helper.getView(R.id.tv_hotel_name)).setText(item.storeName);
        ((TextView) helper.getView(R.id.tv_room_address)).setText(""+item.address);
        ((TextView)helper.getView(R.id.tv_price)).setText(mContext.getString(R.string.unit_rmb)+item.consumeSumPrice);

        ((TextView) helper.getView(R.id.tv_room_info)).setText(item.checkInTime+" 至 "+item.checkOutTime);
        if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_CANCLE_ORDER.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_CANCLE_ORDER.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_CHECK_OUT.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_CHECK_OUT.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_TIMEOUT.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_TIMEOUT.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_DRAWBACK_SUCCESS.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_HAVE_LIVED.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_HAVE_LIVED.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_NO_PAY.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_NO_PAY.getName());
        }else if(item.orderStatus.equals(OrderStatus.ORDER_STATUS_WAITING_LIVE.getType())){
            ((TextView) helper.getView(R.id.tv_status)).setText(OrderStatus.ORDER_STATUS_WAITING_LIVE.getName());
        }


            OrderStatus.ORDER_STATUS_LEFT_HOTEL.getName();

//        ((TextView) helper.getView(R.id.tv_status)).setText(item.evaluateStatus.equals(OrderEvaluteStatus.ORDER_EVALUTE_STATUS_NO.getType())?"待评论":"已评论");

        if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_WAITING_LIVE.getType())){
            helper.getView(R.id.btn_cancle_order).setVisibility(View.VISIBLE);
            helper.getView(R.id.bt_order_pre).setVisibility(View.GONE);
            helper.getView(R.id.bt_do_comment).setVisibility(View.GONE);
            helper.getView(R.id.bt_order_delete).setVisibility(View.GONE);
        }else if (item.orderStatus.equals(OrderStatus.ORDER_STATUS_ALREADY_CHECK_ROOM.getType())||item.orderStatus.equals(OrderStatus.ORDER_STATUS_CONFIRM_OUT.getType()) ||item.orderStatus.equals(OrderStatus.ORDER_STATUS_LEFT_HOTEL.getType())){
            helper.getView(R.id.btn_cancle_order).setVisibility(View.GONE);
            helper.getView(R.id.bt_order_pre).setVisibility(View.VISIBLE);
            helper.getView(R.id.bt_order_delete).setVisibility(View.GONE);
            if (item.evaluateStatus.equals(OrderEvaluteStatus.ORDER_EVALUTE_STATUS_NO.getType())){
                helper.getView(R.id.bt_do_comment).setVisibility(View.VISIBLE);
            }else{
                helper.getView(R.id.bt_do_comment).setVisibility(View.GONE);
            }
        }else {
            helper.getView(R.id.btn_cancle_order).setVisibility(View.GONE);
            helper.getView(R.id.bt_order_pre).setVisibility(View.GONE);
            helper.getView(R.id.bt_order_delete).setVisibility(View.GONE);
            helper.getView(R.id.bt_do_comment).setVisibility(View.GONE);
        }
    }
}
