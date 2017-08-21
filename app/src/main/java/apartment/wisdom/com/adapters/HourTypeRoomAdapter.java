package apartment.wisdom.com.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.PreOrderActivity;
import apartment.wisdom.com.beans.HotelOrderItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HourTypeRoomAdapter extends BaseAdapter {
    private Context context;

    public HourTypeRoomAdapter(Context context, List<HotelOrderItem> hotelOrderItems) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
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


        viewHolder.tvNormalRoomOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) context).openActivity(PreOrderActivity.class);
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.rl_normal_room_tittle)
        RelativeLayout rlNormalRoomTittle;
        @BindView(R.id.tv_normal_discount_price)
        TextView tvNormalDiscountPrice;
        @BindView(R.id.tv_normal_room_hours)
        TextView tvNormalRoomHours;
        @BindView(R.id.tv_normal_room_order)
        TextView tvNormalRoomOrder;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
