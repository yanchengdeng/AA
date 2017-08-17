package apartment.wisdom.com.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.popup.base.BasePopup;

import apartment.wisdom.com.R;
import apartment.wisdom.com.widgets.views.BottomPriceView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static apartment.wisdom.com.R.id.tv_arrive_time;

/**
 * Author: 邓言诚  Create at : 17/8/15  00:31
 * Email: yanchengdeng@gmail.com
 * Describle: 预订界面
 */
public class PreOrderActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_hotel_room_type)
    TextView tvHotelRoomType;
    @BindView(R.id.tv_stand_in_date)
    TextView tvStandInDate;
    @BindView(R.id.tv_leave_date)
    TextView tvLeaveDate;
    @BindView(R.id.tv_hotel_order_days)
    TextView tvHotelOrderDays;
    @BindView(R.id.tv_hotel_order_room_count)
    TextView tvHotelOrderRoomCount;
    @BindView(R.id.ll_room_count)
    LinearLayout llRoomCount;
    @BindView(R.id.tv_hotel_user_name)
    TextView tvHotelUserName;
    @BindView(R.id.iv_add_user)
    ImageView ivAddUser;
    @BindView(R.id.et_user_tel)
    EditText etUserTel;
    @BindView(R.id.ll_tel_verify_layout)
    LinearLayout llTelVerifyLayout;
    @BindView(tv_arrive_time)
    TextView tvArriveTime;
    @BindView(R.id.ll_arrive_time)
    LinearLayout llArriveTime;
    @BindView(R.id.et_order_remarks)
    EditText etOrderRemarks;
    @BindView(R.id.tv_arrive_warn)
    TextView tvArriveWarn;
    @BindView(R.id.ll_arrive_warn)
    LinearLayout llArriveWarn;
    @BindView(R.id.ll_price_detail)
    BottomPriceView llPriceDetail;
    private PopPrcieDialog bubblePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);
        ButterKnife.bind(this);

        initBottomLisener();
    }

    private void initBottomLisener() {
        llPriceDetail.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(PayActivity.class);
            }
        });

        llPriceDetail.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showDetailInfo();
                } else {
                    if (bubblePopup != null) {
                        if (bubblePopup.isShowing()) {
                            bubblePopup.dismiss();
                        }
                    }
                }
            }
        });
    }

    //显示详细价格
    private void showDetailInfo() {
        bubblePopup = new PopPrcieDialog(mContext);
        bubblePopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                llPriceDetail.checkBox.setChecked(false);
            }
        });
        bubblePopup.anchorView(llPriceDetail)
                .gravity(Gravity.TOP)
                .show();
    }


    private class PopPrcieDialog extends BasePopup<PopPrcieDialog> {

        private TextView tvRoom, tvPrice;

        public PopPrcieDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View inflate = View.inflate(mContext, R.layout.pop_detail_price_layout, null);
            inflate.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
            tvPrice = (TextView) inflate.findViewById(R.id.tv_price);
            tvRoom = (TextView) inflate.findViewById(R.id.tv_pop_room);
            tvRoom.setText("2017-08-08(1间)");
            tvPrice.setText("188");
            return inflate;
        }


        @Override
        public void setUiBeforShow() {

        }
    }

    private void showUserSelectDialog() {
        final String[] stringItems = {"王二", "张三", "周五"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, llPriceDetail);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvHotelUserName.setText(stringItems[position]);
                dialog.dismiss();
            }
        });
    }




    private void showSelectTimeDialog() {
        final String[] stringItems = {"14:00", "15:00", "16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, llPriceDetail);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvArriveTime.setText(stringItems[position]);
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_add_user,R.id.ll_arrive_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_add_user:
                showUserSelectDialog();
                break;
            case R.id.ll_arrive_time:
                showSelectTimeDialog();
                break;

        }
    }
}
