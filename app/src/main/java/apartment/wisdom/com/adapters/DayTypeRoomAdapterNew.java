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
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.widget.base.BaseDialog;
import com.jude.rollviewpager.RollPagerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import butterknife.BindView;
import butterknife.ButterKnife;

public class DayTypeRoomAdapterNew extends BaseAdapter {
    private Context mContext;
    private List<DayGroupHotel> dayGroupHotels;

    public DayTypeRoomAdapterNew(Context mContext, List<DayGroupHotel> dayGroupHotels) {
        this.mContext = mContext;
        this.dayGroupHotels = dayGroupHotels;
    }

    @Override
    public int getCount() {
        return dayGroupHotels.size();
    }

    @Override
    public Object getItem(int i) {
        return dayGroupHotels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_hotel_day_group_item_new, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final DayGroupHotel item = dayGroupHotels.get(i);
        if (!TextUtils.isEmpty(item.name)) {
            viewHolder.tvHotelName.setText(item.name);
        }
        if (!TextUtils.isEmpty(item.roomNum)) {
            viewHolder.tvRoomLeft.setText(String.format(mContext.getString(R.string.left_room_tips), new Object[]{item.roomNum}));
        }

        if (!TextUtils.isEmpty(item.discountPrice)) {
            viewHolder.tvNormalRoomPrice.setText(item.discountPrice);
        }

        if (!TextUtils.isEmpty(item.roomNum)) {
            if (item.roomNum.equals("0")) {
                viewHolder.tvNormalRoomOrder.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small_gray));
            } else {
                viewHolder.tvNormalRoomOrder.setBackground(mContext.getResources().getDrawable(R.drawable.normal_submit_btn_small));
            }
        }
        viewHolder.tvNormalRoomOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.roomNum.equals("0")) {
                    return;
                }

                Calendar calendarstandIn = Calendar.getInstance();
                calendarstandIn.set(Calendar.YEAR, item.standIn.getYear());
                calendarstandIn.set(Calendar.MONTH, item.standIn.getMonth());
                calendarstandIn.set(Calendar.DAY_OF_MONTH, item.standIn.getDay());

                Calendar calendarNow = Calendar.getInstance();
                calendarNow.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                calendarNow.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH) + 1);
                calendarNow.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                long days = TimeUtils.getTimeSpanByNow(calendarstandIn.get(Calendar.YEAR) + "-" + calendarstandIn.get(Calendar.MONTH) + "-" + calendarstandIn.get(Calendar.DAY_OF_MONTH), new SimpleDateFormat("yyyy-MM-dd"), TimeConstants.DAY);



                if ((item.roomTypeCode.equals("0002")|| item.roomTypeCode.equals("0003"))&&Constants.IS_SELECT_ZERO_TIME&&LoginUtils.isZeroTime()){
                    ToastUtils.showShort("凌晨房模式不能入住精品房和豪华房");
                    return;
                }

                LogUtils.w("dyc", days);
                if (days < item.bespeakDays) {
                    ToastUtils.showShort(item.name + "需要提前" + item.bespeakDays + "天预订，请重新选择入住时间");
                    return;
                }
                if (LoginUtils.getLoginStatus()) {
                    RoomListInfo.HourRoom hourRoom = new RoomListInfo.HourRoom();
                    hourRoom.roomPrice = item.discountPrice;
                    hourRoom.roomTypeId = item.roomTypeId;
                    hourRoom.storeId = item.storeId;
                    hourRoom.roomTypeName = item.hotelName;
                    hourRoom.standIn = CalendarUtils.getInstant().getDateForamte(item.standIn);
                    hourRoom.standInSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(item.standIn);
                    hourRoom.standOut = CalendarUtils.getInstant().getDateForamte(item.standOut);
                    hourRoom.standOutSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(item.standOut);
                    hourRoom.differDays = item.differDays;
                    hourRoom.selectType = item.selectType;
                    hourRoom.roomDeposit = item.roomDeposit;
                    hourRoom.roomRisePrice = item.roomRisePrice;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.PASS_OBJECT, hourRoom);
                    ((BaseActivity) mContext).openActivity(PreOrderActivity.class, bundle);
                } else {
                    ((BaseActivity) mContext).openActivity(LoginActivity.class);
                }
            }
        });

        viewHolder.hoteItemUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailsDialog(item);
            }
        });
        return view;
    }

    //酒店详情对话框
    private void showDetailsDialog(DayGroupHotel item) {
        RoomDetailDialog roomDetailDialog = new RoomDetailDialog(mContext, item);
        roomDetailDialog.show();
    }

    private class RoomDetailDialog extends BaseDialog<RoomDetailDialog> {
        private DayGroupHotel item;

        public RoomDetailDialog(Context context, DayGroupHotel item) {
            super(context);
            this.item = item;
        }


        @Override
        public View onCreateView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_room_detail_layout, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            TextView tvname = (TextView) view.findViewById(R.id.tv_room_name);
            tvname.setText(item.hotelName);
            TextView tvroomBrief = (TextView) view.findViewById(R.id.tv_room_brief);
            if (!TextUtils.isEmpty(item.roomTypeRemark)) {
                tvroomBrief.setText(item.roomTypeRemark);
            }
            tvname.setText(item.hotelName);
            TextView tvMoney = (TextView) view.findViewById(R.id.tv_pay_money);
            tvMoney.setText(item.discountPrice);
            LinearLayout.LayoutParams rollPageParams = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - 100, (int) ((ScreenUtils.getScreenWidth() - 100)*(3.0/4)));
            RollPagerView pager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
            pager.setLayoutParams(rollPageParams);
            pager.setAdapter(new GalleryPagerAdapter(item.roomTypeImageList));
            TextView tvPay = (TextView) view.findViewById(R.id.tv_sure);
            if (!TextUtils.isEmpty(item.roomNum)) {
                if (item.roomNum.equals("0")) {
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
                        RoomListInfo.HourRoom hourRoom = new RoomListInfo.HourRoom();
                        hourRoom.roomPrice = item.discountPrice;
                        hourRoom.roomTypeId = item.roomTypeId;
                        hourRoom.storeId = item.storeId;
                        hourRoom.roomTypeName = item.hotelName;
                        hourRoom.standIn = CalendarUtils.getInstant().getDateForamte(item.standIn);
                        hourRoom.standInSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(item.standIn);
                        hourRoom.standOut = CalendarUtils.getInstant().getDateForamte(item.standOut);
                        hourRoom.standOutSimple = CalendarUtils.getInstant().getDateForamteChinesMMdd(item.standOut);
                        hourRoom.differDays = item.differDays;
                        hourRoom.selectType = item.selectType;
                        hourRoom.roomDeposit = item.roomDeposit;
                        hourRoom.roomRisePrice = item.roomRisePrice;
                        Bundle bundle = new Bundle();
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

    static class ViewHolder {
        @BindView(R.id.tv_hotel_name)
        TextView tvHotelName;
        @BindView(R.id.tv_room_left)
        TextView tvRoomLeft;
        @BindView(R.id.tv_normal_room_price)
        TextView tvNormalRoomPrice;
        @BindView(R.id.tv_normal_room_order)
        TextView tvNormalRoomOrder;
        @BindView(R.id.rl_hotel_day_ui)
        View hoteItemUI;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
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
}
