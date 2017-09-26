package apartment.wisdom.com.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.PreOrderInfo;

public class OrderDetailPriceAdapter extends BaseAdapter {
    private Context context;
    private List<PreOrderInfo.CustomeItem> consumeList;

    public OrderDetailPriceAdapter(Context context, List<PreOrderInfo.CustomeItem> consumeList) {
        this.context = context;
        this.consumeList = consumeList;
    }

    @Override
    public int getCount() {
        return consumeList.size();
    }

    @Override
    public Object getItem(int i) {
        return consumeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_order_detail_price, null);
        }


        PreOrderInfo.CustomeItem customeItem = consumeList.get(i);
        TextView textView = (TextView) view;

        textView.setText(customeItem.consumeName+"  " + context.getString(R.string.unit_rmb) + customeItem.consumePrice + " x " + customeItem.consumeNum + "");
        return view;
    }
}
