package apartment.wisdom.com.widgets.views;


import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;

/**
 * Author: 邓言诚  Create at : 17/8/14  14:44
 * Email: yanchengdeng@gmail.com
 * Describle:  价格显示 自定义表
 */
public class PriceShowView extends LinearLayout {

    private TextView tvPrice,tvPriceTips;
    private ImageView ivArrow;
    private Context context;



    public PriceShowView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_price_common_wrap_diy, this);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvPriceTips = (TextView)view.findViewById(R.id.tv_tips);
        ivArrow = (ImageView)view.findViewById(R.id.iv_arrow);
    }

    public PriceShowView(Context context,AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_price_common_wrap_diy, this);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvPriceTips = (TextView)view.findViewById(R.id.tv_tips);
        ivArrow = (ImageView)view.findViewById(R.id.iv_arrow);

    }


    /**
     * 设置价格显示状态
     * @param isClick false 正常显示   true   按下显示模板
     */
    public void  setShowStatus(boolean isClick){
        if (!isClick){
            tvPrice.getPaint().setFlags(0);
            tvPriceTips.setText(context.getString(R.string.tag_start));
            tvPriceTips.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            ivArrow.setImageResource(R.mipmap.arrow_right);
        }else{
            tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
            tvPriceTips.setText(context.getString(R.string.door_price));
            tvPriceTips.setTextColor(context.getResources().getColor(R.color.activity_info));
            ivArrow.setImageResource(R.mipmap.arrow_down);
        }
    }
}
