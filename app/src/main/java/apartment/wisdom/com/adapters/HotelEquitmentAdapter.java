package apartment.wisdom.com.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import apartment.wisdom.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelEquitmentAdapter extends BaseAdapter {

    private String[] enquitments;
    private Context context;

    public HotelEquitmentAdapter(Context context, String[] enquitments) {
        this.context = context;
        this.enquitments = enquitments;
    }

    @Override
    public int getCount() {
        return enquitments.length;
    }

    @Override
    public Object getItem(int i) {
        return enquitments[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_hotel_equitment, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvDevices.setText(enquitments[i]);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_devices)
        TextView tvDevices;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
