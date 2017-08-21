package apartment.wisdom.com.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.dialog.widget.popup.base.BasePopup;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.CustomeNeedListAdapter;
import apartment.wisdom.com.beans.CustomeType;
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
    private CustomeNeedDialog custemDialg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.fill_order));
        initBottomLisener();
        initCustomeNeedDialog();
    }

    //套餐配置
    private void initCustomeNeedDialog() {
        custemDialg = new CustomeNeedDialog(mContext);
        custemDialg.setTitle("可选择您套餐");
        custemDialg.show();
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



    private class CustomeNeedDialog extends BaseDialog<CustomeNeedDialog> {

        public CustomeNeedDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {

            CustomeNeedListAdapter customeNeedListAdapter = new CustomeNeedListAdapter(mContext,R.layout.dialog_custome_need_item,testData());
            View inflate = View.inflate(mContext, R.layout.dialog_custome_need_layout, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth()-150, (int)(ScreenUtils.getScreenHeight()*0.75));
            params.gravity= Gravity.CENTER_HORIZONTAL;
            inflate.setLayoutParams(params);
            RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(customeNeedListAdapter);
            View headView = getLayoutInflater().inflate(R.layout.dialog_custome_need_layout_head, (ViewGroup) recyclerView.getParent(), false);
            customeNeedListAdapter.addHeaderView(headView);
            inflate.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            inflate.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            return inflate;
        }

        @Override
        public void setUiBeforShow() {

        }

    }

    private List<CustomeType> testData() {
        List<CustomeType>  customeTypes = new ArrayList<>();
        CustomeType customeType = new CustomeType();
        customeType.setName("早餐");
        customeType.setType(1);
        List<CustomeType.CustomTypeItem> customTypeItems = new ArrayList<>();
        CustomeType.CustomTypeItem item = new CustomeType.CustomTypeItem();
        item.setName("中餐");
        item.setPic("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=571094688,4029099390&fm=26&gp=0.jpg");
        customTypeItems.add(item);
        customTypeItems.add(item);
        customTypeItems.add(item);
        customeType.setCustomTypeItems(customTypeItems);

        //
        List<CustomeType.CustomTypeItem> customTypeItems1 = new ArrayList<>();
        CustomeType customeType1 = new CustomeType();
        customeType1.setName("五件套样式");
        CustomeType.CustomTypeItem item1 = new CustomeType.CustomTypeItem();
        item1.setName("紫色");
        item1.setPic("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3786141440,2933190617&fm=26&gp=0.jpg");
        customTypeItems1.add(item1);
        customTypeItems1.add(item1);
        customTypeItems1.add(item1);
        customTypeItems1.add(item1);
        customeType1.setCustomTypeItems(customTypeItems1);

        //
        CustomeType customeType2 = new CustomeType();
        customeType2.setName("香气");

        List<CustomeType.CustomTypeItem> customTypeItems2 = new ArrayList<>();
        CustomeType.CustomTypeItem item2 = new CustomeType.CustomTypeItem();
        item2.setName("薰衣草");
        item2.setPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503171411656&di=528c6a4d0a4aa556bbf6fc02266c0bcf&imgtype=0&src=http%3A%2F%2Fimgs.bzw315.com%2FUploadFiles%2FVersion2%2F0%2F20160414%2F201604141131122854.png");
        customTypeItems2.add(item2);
        customTypeItems2.add(item2);
        customTypeItems2.add(item2);
        customTypeItems2.add(item2);
        customeType2.setCustomTypeItems(customTypeItems2);
        //

        CustomeType customeType3 = new CustomeType();
        customeType3.setName("房间布置");
        List<CustomeType.CustomTypeItem> customTypeItems3 = new ArrayList<>();
        CustomeType.CustomTypeItem item3 = new CustomeType.CustomTypeItem();
        item3.setName("气球");
        item3.setPic("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1515296545,2012236509&fm=26&gp=0.jpg");
        customTypeItems3.add(item3);
        customTypeItems3.add(item3);
        customeType3.setCustomTypeItems(customTypeItems3);

        //
        CustomeType customeType4 = new CustomeType();
        customeType4.setName("酒水");
        List<CustomeType.CustomTypeItem> customTypeItems4 = new ArrayList<>();
        CustomeType.CustomTypeItem item4 = new CustomeType.CustomTypeItem();
        item4.setName("威士忌");
        item4.setPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503171590137&di=3dce2176be04f9947297e6b41fcfd03b&imgtype=0&src=http%3A%2F%2Fpic62.nipic.com%2Ffile%2F20150320%2F2531170_141503092000_2.jpg");
        customTypeItems4.add(item4);
        customTypeItems4.add(item4);
        customTypeItems4.add(item4);
        customTypeItems4.add(item4);
        customeType4.setCustomTypeItems(customTypeItems4);


        //
        customeTypes.add(customeType);
        customeTypes.add(customeType1);
        customeTypes.add(customeType2);
        customeTypes.add(customeType3);
        customeTypes.add(customeType4);
        return customeTypes;
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
        final String[] stringItems = {"14:00前", "15:00前", "16:00前", "17:00前", "18:00前", "19:00前", "20:00前", "21:00前", "22:00前", "23:00前"};
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

    @OnClick({R.id.back, R.id.iv_add_user, R.id.ll_arrive_time})
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
