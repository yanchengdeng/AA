package apartment.wisdom.com.widgets.views;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;

/**
*
* Author: 邓言诚  Create at : 17/8/15  15:13
* Email: yanchengdeng@gmail.com
* Describle: 订购UI 底部价格控件
*/
public class BottomPriceView extends LinearLayout {

    private Context context;

    private TextView tvPrice ;
    public CheckBox checkBox;
    public Button btnOrder;
    public BottomPriceView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_order_bottom, this);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        checkBox = (CheckBox) view.findViewById(R.id.ck_detail_check);
        btnOrder  = (Button) view.findViewById(R.id.btn_order);
    }

    public BottomPriceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_order_bottom, this);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        checkBox = (CheckBox) view.findViewById(R.id.ck_detail_check);
        btnOrder  = (Button) view.findViewById(R.id.btn_order);
    }

    public void setPriceVlaue(String price){
        tvPrice.setText(price);
    }
}
