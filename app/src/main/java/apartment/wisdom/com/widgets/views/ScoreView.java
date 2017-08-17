package apartment.wisdom.com.widgets.views;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

import apartment.wisdom.com.R;

public class ScoreView extends LinearLayout {

    private TextView tvTittle,tvScoreNum;
    private View vWeigth;

    public ScoreView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_hotel_detail_score, this);
        tvTittle = (TextView) view.findViewById(R.id.tv_score_name);
        vWeigth  = view.findViewById(R.id.v_weight);
        tvScoreNum = (TextView) view.findViewById(R.id.tv_score_num);
        setData();
    }


    public ScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_hotel_detail_score, this);
        tvTittle = (TextView) view.findViewById(R.id.tv_score_name);
        vWeigth  = view.findViewById(R.id.v_weight);
        tvScoreNum = (TextView) view.findViewById(R.id.tv_score_num);
        setData();
    }


    public void setData(){
        tvTittle.setText("环境");
        tvScoreNum.setText("4.5分");
        vWeigth.setLayoutParams(new LinearLayout.LayoutParams(0, ConvertUtils.dp2px(4),4.5f));

    }




}
