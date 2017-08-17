package apartment.wisdom.com.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import apartment.wisdom.com.R;

public class HotelEquitmentAdapter extends BaseAdapter {

    private String[] enquitments = new String[]{"空调环境","环境优美","环境优美","环境优美","环境优美","环境优美","环境优美","环境优美","环境优美","环境优美"};

    private Context context;
    public HotelEquitmentAdapter(Context context){
        this.context = context;

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
        if (view ==null){
            view = LayoutInflater.from(context).inflate(R.layout.adapter_hotel_equitment,null);
        }
        TextView textView = (TextView) view;

        textView.setText(enquitments[i]);
        return textView;
    }
}
