package apartment.wisdom.com.adapters;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tubb.calendarselector.library.FullDay;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.LoginActivity;
import apartment.wisdom.com.activities.PreOrderActivity;
import apartment.wisdom.com.beans.RoomListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DayHourRoomType;
import apartment.wisdom.com.utils.CalendarUtils;
import apartment.wisdom.com.utils.LoginUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HourTypeRoomAdapter extends BaseAdapter {
    private Context context;
    private List<RoomListInfo.HourRoom> hourRoomList;
    private String storeId;
    private int diffdays;
    private FullDay standIn,standOut;
    private String breakTime;



    public HourTypeRoomAdapter(Context mContext, List<RoomListInfo.HourRoom> hourRoomList, String storeId, FullDay stant_in, FullDay stant_out, int diffdays,String bespeakTime) {
        this.context = mContext;
        this.hourRoomList = hourRoomList;
        this.storeId = storeId;
        this.diffdays = diffdays;
        this.standIn = stant_in;
        this.standOut = stant_out;
        this.breakTime = bespeakTime;
    }

    @Override
    public int getCount() {
        return hourRoomList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_hour_room_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final RoomListInfo.HourRoom hourRoom = hourRoomList.get(i);

        if (!TextUtils.isEmpty(hourRoom.roomTypeName)) {
            viewHolder.tvHotelName.setText(hourRoom.roomTypeName);
        }

        if (!TextUtils.isEmpty(hourRoom.hourNum)) {
            viewHolder.tvNormalRoomHours.setText(hourRoom.hourNum+"小时");
        }

        if (!TextUtils.isEmpty(hourRoom.roomPrice)) {
            viewHolder.tvNormalDiscountPrice.setText(hourRoom.roomPrice);
        }

        if (!TextUtils.isEmpty(hourRoom.roomNum)) {
            viewHolder.tvRoomLeft.setText(String.format(context.getString(R.string.left_room_tips), new Object[]{hourRoom.roomNum}));
        }

        if (!TextUtils.isEmpty(hourRoom.roomNum)) {
            if (hourRoom.roomNum.equals("0")) {
                viewHolder.tvNormalRoomOrder.setBackground(context.getResources().getDrawable(R.drawable.normal_submit_btn_small_gray));
            } else {
                viewHolder.tvNormalRoomOrder.setBackground(context.getResources().getDrawable(R.drawable.normal_submit_btn_small));
            }
        }

        viewHolder.tvNormalRoomOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
                    if (hourRoom.roomNum.equals("0")) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    hourRoom.storeId = storeId;
                    hourRoom.standIn = CalendarUtils.getInstant().getDateForamte(standIn);
                    hourRoom.standInSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(standIn);
                    hourRoom.standOut = CalendarUtils.getInstant().getDateForamte(standOut);
                    hourRoom.standOutSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(standOut);
                    hourRoom.differDays = 1;
                    hourRoom.selectType = DayHourRoomType.DAY_HOUR_ROOM_TYPE_HOUR.getType();
                    hourRoom.bespeakTime = breakTime;
                    bundle.putSerializable(Constants.PASS_OBJECT, hourRoom);
                    ((BaseActivity) context).openActivity(PreOrderActivity.class, bundle);
                }else{
                    ((BaseActivity)context).openActivity(LoginActivity.class);
                }
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_hotel_name)
        TextView tvHotelName;
        @BindView(R.id.rl_normal_room_tittle)
        RelativeLayout rlNormalRoomTittle;
        @BindView(R.id.tv_normal_discount_price)
        TextView tvNormalDiscountPrice;
        @BindView(R.id.tv_normal_room_hours)
        TextView tvNormalRoomHours;
        @BindView(R.id.tv_normal_room_order)
        TextView tvNormalRoomOrder;
        @BindView(R.id.tv_room_left)
        TextView tvRoomLeft;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
