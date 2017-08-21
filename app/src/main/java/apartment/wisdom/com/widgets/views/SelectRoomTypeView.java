package apartment.wisdom.com.widgets.views;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import apartment.wisdom.com.R;

public class SelectRoomTypeView extends LinearLayout {
    private Context context;
    public View rlDay,rlHour,lineDay,lineHour;
    private TextView tvDay,tvHour;

    public SelectRoomTypeView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_select_room_type, this);
        rlDay = view.findViewById(R.id.rl_day);
        rlHour = view.findViewById(R.id.rl_hour);
        lineDay = view.findViewById(R.id.tv_type_day_line);
        lineHour = view.findViewById(R.id.tv_type_hour_line);
        tvDay = (TextView) view.findViewById(R.id.tv_type_day);
        tvHour = (TextView)view.findViewById(R.id.tv_type_hour);
    }

    public SelectRoomTypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_select_room_type, this); rlDay = view.findViewById(R.id.rl_day);
        rlHour = view.findViewById(R.id.rl_hour);
        lineDay = view.findViewById(R.id.tv_type_day_line);
        lineHour = view.findViewById(R.id.tv_type_hour_line);
        tvDay = (TextView) view.findViewById(R.id.tv_type_day);
        tvHour = (TextView)view.findViewById(R.id.tv_type_hour);

    }

    public void setSelectDay(){
        lineDay.setVisibility(VISIBLE);
        lineHour.setVisibility(INVISIBLE);
        tvDay.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        tvHour.setTextColor(context.getResources().getColor(R.color.activity_tv));

    }

    public void setSelectHour(){
        lineDay.setVisibility(INVISIBLE);
        lineHour.setVisibility(VISIBLE);
        tvDay.setTextColor(context.getResources().getColor(R.color.activity_tv));
        tvHour.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }
}
