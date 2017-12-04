package apartment.wisdom.com.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.widget.base.BaseDialog;
import com.jude.rollviewpager.RollPagerView;
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
    private Context mContext;
    private List<RoomListInfo.HourRoom> hourRoomList;
    private String storeId;
    private int diffdays;
    private FullDay standIn, standOut;
    private String breakTime;


    public HourTypeRoomAdapter(Context mContext, List<RoomListInfo.HourRoom> hourRoomList, String storeId, FullDay stant_in, FullDay stant_out, int diffdays, String bespeakTime) {
        this.mContext = mContext;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_hour_room_item, null);
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
            viewHolder.tvNormalRoomHours.setText(hourRoom.hourNum + "小时");
        }

        if (!TextUtils.isEmpty(hourRoom.roomPrice)) {
            viewHolder.tvNormalDiscountPrice.setText(hourRoom.roomPrice);
        }

        if (!TextUtils.isEmpty(hourRoom.roomNum)) {
            viewHolder.tvRoomLeft.setText(String.format(mContext.getString(R.string.left_room_tips), new Object[]{hourRoom.roomNum}));
        }

        if (!TextUtils.isEmpty(hourRoom.roomNum)) {
            if (hourRoom.roomNum.equals("0")) {
                viewHolder.tvNormalRoomOrder.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small_gray));
            } else {
                viewHolder.tvNormalRoomOrder.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small));
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
                    ((BaseActivity) mContext).openActivity(PreOrderActivity.class, bundle);
                } else {
                    ((BaseActivity) mContext).openActivity(LoginActivity.class);
                }
            }
        });

        viewHolder.hourRoomUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailsDialog(hourRoom);
            }
        });

        return view;
    }

    //酒店详情对话框
    private void showDetailsDialog(RoomListInfo.HourRoom item) {
        RoomDetailDialog roomDetailDialog = new RoomDetailDialog(mContext, item);
        roomDetailDialog.show();
    }

    private class RoomDetailDialog extends BaseDialog<RoomDetailDialog> {
        private RoomListInfo.HourRoom hourRoom;

        public RoomDetailDialog(Context context, RoomListInfo.HourRoom item) {
            super(context);
            this.hourRoom = item;
        }


        @Override
        public View onCreateView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_room_detail_layout, null);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            view.setLayoutParams(layoutParams);
            TextView tvname = (TextView) view.findViewById(R.id.tv_room_name);
            tvname.setText(hourRoom.hotelName);
            TextView tvroomBrief = (TextView) view.findViewById(R.id.tv_room_brief);
            if (!TextUtils.isEmpty(hourRoom.roomTypeRemark)) {
                tvroomBrief.setText(hourRoom.roomTypeRemark);
            }
            tvname.setText(hourRoom.hotelName);
            TextView tvMoney = (TextView) view.findViewById(R.id.tv_pay_money);
            tvMoney.setText(hourRoom.roomPrice);
            LinearLayout.LayoutParams rollPageParams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - 100, (int) ((ScreenUtils.getScreenWidth() - 100)*(3.0/4)));
            RollPagerView pager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
            pager.setLayoutParams(rollPageParams);
            pager.setAdapter(new GalleryPagerAdapter(hourRoom.roomTypeImageList));
            TextView tvPay = (TextView) view.findViewById(R.id.tv_sure);
            if (!TextUtils.isEmpty(hourRoom.roomNum)) {
                if (hourRoom.roomNum.equals("0")) {
                    tvPay.setClickable(false);
                    tvPay.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small_gray));
                } else {
                    tvPay.setClickable(true);
                    tvPay.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small));
                }
            }
            tvPay.setOnClickListener(new View.OnClickListener() {
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
                        ((BaseActivity) mContext).openActivity(PreOrderActivity.class, bundle);
                    } else {
                        ((BaseActivity) mContext).openActivity(LoginActivity.class);
                    }
                    dismiss();
                }
            });
            view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            return view;
        }

        @Override
        public void setUiBeforShow() {

        }
    }

    public class GalleryPagerAdapter extends PagerAdapter {
        private List<RoomListInfo.RoomTypeImage> images;

        public GalleryPagerAdapter(List<RoomListInfo.RoomTypeImage> roomTypeImageList) {
            this.images = roomTypeImageList;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(mContext);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext).load(images.get(position).roomTypeImage).placeholder(R.drawable.default_error).into(item);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
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
        @BindView(R.id.ll_hour_room_ui)
        View hourRoomUI;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
