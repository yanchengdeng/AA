package apartment.wisdom.com.adapters;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.LoginActivity;
import apartment.wisdom.com.activities.PreOrderActivity;
import apartment.wisdom.com.beans.DayGroupHotel;
import apartment.wisdom.com.beans.RoomListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.CalendarUtils;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.widgets.views.PriceShowView;

import static apartment.wisdom.com.R.id.tv_normal_discount_price;
import static apartment.wisdom.com.R.id.tv_normal_room_price;

//天房适配器
public class DayTypeRoomAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<DayGroupHotel> dayGroupHotels;

    public DayTypeRoomAdapter(Context mContext, List<DayGroupHotel> dayGroupHotels) {
        this.mContext = mContext;
        this.dayGroupHotels = dayGroupHotels;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getGroupCount() {
        return dayGroupHotels.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return dayGroupHotels.get(i).items.size();
    }

    @Override
    public Object getGroup(int i) {
        return dayGroupHotels.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return dayGroupHotels.get(i).items.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpand, View view, ViewGroup viewGroup) {
        ViewHolderGroup viewHolderGroup;
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_hotel_day_group_item, null);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.tvname = (TextView) view.findViewById(R.id.tv_hotel_name);
            viewHolderGroup.tvLeftRoom = (TextView) view.findViewById(R.id.tv_room_left);
            viewHolderGroup.priceShowView = (PriceShowView) view.findViewById(R.id.tv_price_content);
            view.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) view.getTag();
        }

        DayGroupHotel item = dayGroupHotels.get(i);
        if (!TextUtils.isEmpty(item.name)) {
            viewHolderGroup.tvname.setText(item.name);
        }
        if (!TextUtils.isEmpty(item.roomNum)) {
            viewHolderGroup.tvLeftRoom.setText(String.format(mContext.getString(R.string.left_room_tips), new Object[]{item.roomNum}));
        }
        if (isExpand) {
            viewHolderGroup.priceShowView.setShowStatus(isExpand, item.price);
        } else {
            viewHolderGroup.priceShowView.setShowStatus(isExpand, item.discountPrice);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderChild viewHolderChild;
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_hotel_day_child_item, null);
            viewHolderChild = new ViewHolderChild();
            viewHolderChild.tvPrice = (TextView) view.findViewById(tv_normal_room_price);
            viewHolderChild.tvDiscountPrice = (TextView) view.findViewById(tv_normal_discount_price);
            viewHolderChild.tvOrder = (TextView) view.findViewById(R.id.tv_normal_room_order);
            viewHolderChild.tvDepsit = (TextView) view.findViewById(R.id.tv_price_change);
            view.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) view.getTag();
        }

        final DayGroupHotel.DayGroupHotelItem childItem = dayGroupHotels.get(i).items.get(i1);

        if (!TextUtils.isEmpty(childItem.discountPrice)) {
            viewHolderChild.tvDiscountPrice.setText(childItem.discountPrice);
        }
        if (!TextUtils.isEmpty(childItem.roomPirce)) {
            viewHolderChild.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolderChild.tvPrice.setText(String.format(mContext.getString(R.string.hotel_child_price_tips), new Object[]{childItem.roomPirce}));
        }
        if (!TextUtils.isEmpty(childItem.roomNum)) {
            if (childItem.roomNum.equals("0")) {
                viewHolderChild.tvOrder.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small_gray));
            } else {
                viewHolderChild.tvOrder.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small));
            }
        }

        if (!TextUtils.isEmpty(childItem.roomDeposit)) {
            viewHolderChild.tvDepsit.setText("押金：" + childItem.roomDeposit + (TextUtils.isEmpty(childItem.roomRisePrice) ? "" : "\n涨价：" + childItem.roomRisePrice));
        }

        viewHolderChild.tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childItem.roomNum.equals("0")) {
                    return;
                }
                if (LoginUtils.getLoginStatus()) {
                    RoomListInfo.HourRoom hourRoom = new RoomListInfo.HourRoom();
                    hourRoom.roomPrice = childItem.discountPrice;
                    hourRoom.roomTypeId = childItem.roomTypeId;
                    hourRoom.storeId = childItem.storeId;
                    hourRoom.roomTypeName = childItem.hotelName;
                    hourRoom.standIn = CalendarUtils.getInstant().getDateForamte(childItem.standIn);
                    hourRoom.standInSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(childItem.standIn);
                    hourRoom.standOut = CalendarUtils.getInstant().getDateForamte(childItem.standOut);
                    hourRoom.standOutSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(childItem.standOut);
                    hourRoom.differDays = childItem.differDays;
                    hourRoom.selectType = childItem.selectType;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.PASS_OBJECT, hourRoom);
                    ((BaseActivity) mContext).openActivity(PreOrderActivity.class, bundle);
                } else {
                    ((BaseActivity) mContext).openActivity(LoginActivity.class);
                }
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class ViewHolderGroup {
        TextView tvname;
        TextView tvLeftRoom;
        PriceShowView priceShowView;

    }

    static class ViewHolderChild {
        TextView tvPrice, tvDiscountPrice, tvOrder, tvDepsit;
    }
}
