package apartment.wisdom.com.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.CustomeNeedListAdapter;
import apartment.wisdom.com.adapters.PreOrderDIYSelectAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CustomPeopleList;
import apartment.wisdom.com.beans.CustomeDIY;
import apartment.wisdom.com.beans.CustomeType;
import apartment.wisdom.com.beans.DIYSaveInfo;
import apartment.wisdom.com.beans.PreOrderInfo;
import apartment.wisdom.com.beans.RoomListInfo;
import apartment.wisdom.com.beans.TimeArrays;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DIYType;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.widgets.views.BottomPriceView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static apartment.wisdom.com.R.id.recycle;

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
    EditText tvHotelUserName;
    @BindView(R.id.iv_add_user)
    ImageView ivAddUser;
    @BindView(R.id.et_user_tel)
    EditText etUserTel;
    @BindView(R.id.ll_tel_verify_layout)
    LinearLayout llTelVerifyLayout;
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
    @BindView(R.id.tv_leave_tips)
    TextView tvLeaveTips;
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;
    @BindView(R.id.ll_pesonal_custome)
    LinearLayout llPesonalCustome;
    private PopPrcieDialog bubblePopup;
    private CustomeNeedDialog custemDialg;
    private RoomListInfo.HourRoom hotelRoom;
    private  List<CustomeType> customeTypes;
    private List<CustomPeopleList.CustomPeopleItem> peopleItemInfos = new ArrayList<>();
    String[] arriveTimes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.fill_order));
        hotelRoom = (RoomListInfo.HourRoom) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
        if (!TextUtils.isEmpty(hotelRoom.hotelName)) {
            tvHotelName.setText(hotelRoom.hotelName);
        }
        if (!TextUtils.isEmpty(hotelRoom.roomTypeName)) {
            tvHotelRoomType.setText(hotelRoom.roomTypeName);
        }
        if (!TextUtils.isEmpty(hotelRoom.selectType)) {
            //天房
            if (hotelRoom.selectType.equals("0")) {
                tvStandInDate.setText(hotelRoom.standInSimple);
                tvLeaveDate.setText(hotelRoom.standOutSimple);
                tvHotelOrderDays.setText(String.valueOf(hotelRoom.differDays + "晚"));
            } else {
                tvStandInDate.setText(hotelRoom.standInSimple);
                tvLeaveDate.setText(hotelRoom.bespeakTime);
                tvLeaveTips.setVisibility(View.GONE);
                tvHotelOrderDays.setVisibility(View.GONE);
            }
        }

        if (TextUtils.isEmpty(hotelRoom.roomRisePrice)) {

            llPriceDetail.tvPrice.setText(String.format("%.2f", Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays + Float.parseFloat(hotelRoom.roomDeposit)));
        }else{
            llPriceDetail.tvPrice.setText(String.format("%.2f", Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays + Float.parseFloat(hotelRoom.roomDeposit)+Float.parseFloat(hotelRoom.roomRisePrice)));

        }
        initBottomLisener();
        getNeetDIY();
        getOrderTime();

        EventBus.getDefault().register(this);
    }

    private void getOrderTime() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("checkInRoomType",  hotelRoom.selectType);
        data.put("checkInTime",hotelRoom.standIn);
        OkGo.<AAResponse<TimeArrays>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"getTimeSolt"))
                .execute(new NewsCallback<AAResponse<TimeArrays>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<TimeArrays>> response) {
                        LogUtils.w("dyc",response.body());
                        TimeArrays timeArrays = response.body().data;
                        if (timeArrays!=null&& timeArrays.dateArray!=null && timeArrays.dateArray.length>0){
                            arriveTimes  = new String[timeArrays.dateArray.length];
                            for (int i = 0;i<timeArrays.dateArray.length;i++){
                                arriveTimes[i] = timeArrays.dateArray[i]+"前";
                            }
                            tvArriveTime.setText(arriveTimes[0]);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<TimeArrays>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof PayOrRechargeSuccess) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomeList();
    }

    //获取常用联系人
    private void getCustomeList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<CustomPeopleList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "queryCommonInfo"))
                .execute(new NewsCallback<AAResponse<CustomPeopleList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<CustomPeopleList>> response) {
                        CustomPeopleList customPeopleList = response.body().data;
                        if (customPeopleList != null && !customPeopleList.contacList.isEmpty()) {
                            peopleItemInfos = customPeopleList.contacList;
                            tvHotelUserName.setText(peopleItemInfos.get(0).name);
                            etUserTel.setText(peopleItemInfos.get(0).mobilePhone);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<CustomPeopleList>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }


    //套餐配置
    private void initCustomeNeedDialog() {
        custemDialg = new CustomeNeedDialog(mContext);
        custemDialg.setTitle("您可选择的套餐");
//        custemDialg.isTitleShow(false);
        custemDialg.show();
        custemDialg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                float selectMoney =0;
                if (LoginUtils.getDIY(customeTypes) != null && LoginUtils.getDIY(customeTypes) .size() > 0) {
                    for (DIYSaveInfo item:LoginUtils.getDIY(customeTypes) ){
                        selectMoney+=Float.parseFloat(item.getMoney())*item.getNum();
                    }
                }
                if (TextUtils.isEmpty(hotelRoom.roomRisePrice)) {
                    llPriceDetail.tvPrice.setText(String.format("%.2f", Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays + Float.parseFloat(hotelRoom.roomDeposit) + selectMoney));
                }else {
                    llPriceDetail.tvPrice.setText(String.format("%.2f", Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays + Float.parseFloat(hotelRoom.roomDeposit) +Float.parseFloat(hotelRoom.roomRisePrice)+ selectMoney));
                }
            }
        });
    }

    private void initBottomLisener() {
        llPriceDetail.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prePay();
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


    //生成订单

    /**
     * storeId
     * roomTypeId
     * cardNo
     * checkInRoomType
     * couponId
     * name
     * mobilePhone
     * checkInTime
     * checkOutTime
     * arriveTime
     * remark
     * templateId
     * breakfastId
     * breakfastNum
     * fivePieceId
     * aromaId
     * roomLayoutId
     * wineId
     */
    private void prePay() {
        if (TextUtils.isEmpty(etUserTel.getEditableText().toString().trim())) {
            ToastUtils.showShort(R.string.no_phone);
            return;
        }
        if (!RegexUtils.isMobileSimple(etUserTel.getEditableText().toString())) {
            ToastUtils.showShort(R.string.phone_regex);
            return;
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("storeId", hotelRoom.storeId);
        data.put("roomTypeId", hotelRoom.roomTypeId);
        data.put("cardNo", LoginUtils.getUserInfo().cardNo);
        data.put("checkInRoomType", hotelRoom.selectType);
        data.put("checkInTime", hotelRoom.standIn);
        data.put("name", tvHotelUserName.getText().toString());
        data.put("mobilePhone", etUserTel.getEditableText().toString().trim());

        data.put("checkOutTime", hotelRoom.standOut);
        data.put("arriveTime", hotelRoom.standIn + " " + tvArriveTime.getText().toString().replace("前", ""));
        if (!TextUtils.isEmpty(etOrderRemarks.getEditableText().toString())) {
            data.put("remark", etOrderRemarks.getEditableText().toString());
        }

        if (LoginUtils.hasSelectedByType(customeTypes,DIYType.DIY_TYPE_BRAKFAST.getType())) {
            data.put("breakfastId", LoginUtils.getSelectIdsByType(customeTypes, DIYType.DIY_TYPE_BRAKFAST.getType()));
            data.put("breakfastNum", LoginUtils.getSelectCountsByType(customeTypes, DIYType.DIY_TYPE_BRAKFAST.getType()));
        }

        if (LoginUtils.hasSelectedByType(customeTypes, DIYType.DIY_TYPE_FIVE_PIECES.getType())) {
            data.put("fivePieceId", LoginUtils.getSelectIdsByType(customeTypes, DIYType.DIY_TYPE_FIVE_PIECES.getType()));
        }

        if (LoginUtils.hasSelectedByType(customeTypes, DIYType.DIY_TYPE_ARMOS.getType())) {
            data.put("aromaId", LoginUtils.getSelectIdsByType(customeTypes, DIYType.DIY_TYPE_ARMOS.getType()));
        }
        if (LoginUtils.hasSelectedByType(customeTypes, DIYType.DIY_TYPE_ROOM_LAYOUT.getType())) {
            data.put("roomLayoutId", LoginUtils.getSelectIdsByType(customeTypes, DIYType.DIY_TYPE_ROOM_LAYOUT.getType()));
        }
        if (LoginUtils.hasSelectedByType(customeTypes, DIYType.DIY_TYPE_WINE.getType())) {
            data.put("wineId", LoginUtils.getSelectIdsByType(customeTypes, DIYType.DIY_TYPE_WINE.getType()));
            data.put("wineNum", LoginUtils.getSelectCountsByType(customeTypes, DIYType.DIY_TYPE_WINE.getType()));
        }

        OkGo.<AAResponse<PreOrderInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "submitOrder"))
                .execute(new NewsCallback<AAResponse<PreOrderInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<PreOrderInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        PreOrderInfo preOrderInfo = response.body().data;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.PASS_OBJECT, preOrderInfo);
                        bundle.putString(Constants.PASS_STRING, hotelRoom.storeId);
                        bundle.putString(Constants.SELECT_CARD_TYPE, hotelRoom.roomTypeId);
                        openActivity(PayActivity.class, bundle);

                    }

                    @Override
                    public void onError(Response<AAResponse<PreOrderInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
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

        private TextView tvRoom, tvPrice, tvyj,tvJJ;
        private RecyclerView recyclerView;
        private View lljj;

        public PopPrcieDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View inflate = View.inflate(mContext, R.layout.pop_detail_price_layout, null);
            inflate.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
            tvPrice = (TextView) inflate.findViewById(R.id.tv_price);
            tvyj = (TextView) inflate.findViewById(R.id.tv_price_yj);
            tvRoom = (TextView) inflate.findViewById(R.id.tv_pop_room);
            tvJJ = (TextView) inflate.findViewById(R.id.tv_price_jj);
            lljj = inflate.findViewById(R.id.ll_jj);
            recyclerView = (RecyclerView) inflate.findViewById(recycle);
            if (hotelRoom.selectType.equals("0")) {
                if (hotelRoom.differDays == 1) {
                    tvRoom.setText(hotelRoom.standIn + " (" + hotelRoom.differDays + "晚)");
                } else {
                    tvRoom.setText(hotelRoom.standIn + " 至 " + hotelRoom.standOut + " (" + hotelRoom.differDays + "晚)");
                }
                tvPrice.setText(String.valueOf(Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays));
            } else {
                tvRoom.setText(hotelRoom.standIn + " (" + hotelRoom.differDays + "晚)");
                tvPrice.setText(hotelRoom.roomPrice);
            }

            if (!TextUtils.isEmpty(hotelRoom.roomDeposit)) {
                tvyj.setText(hotelRoom.roomDeposit);
            }

            if (TextUtils.isEmpty(hotelRoom.roomRisePrice)){
                lljj.setVisibility(View.GONE);
            }else{
                lljj.setVisibility(View.VISIBLE);
                tvJJ.setText(hotelRoom.roomRisePrice);
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(PreOrderActivity.this));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(PreOrderActivity.this, DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
            recyclerView.addItemDecoration(dividerItemDecoration);

            List<DIYSaveInfo> diySaveInfos = LoginUtils.getDIY(customeTypes);
            if (diySaveInfos != null && diySaveInfos.size() > 0) {
                List<PreOrderInfo.CustomeItem> customeItems = new ArrayList<>();
                for (DIYSaveInfo item : diySaveInfos) {
                    PreOrderInfo.CustomeItem customTypeItem = new PreOrderInfo.CustomeItem();
                    customTypeItem.consumeName = item.getSelectName();
                    customTypeItem.consumePrice = item.getMoney();
                    customTypeItem.type = item.getType();
                    customTypeItem.consumeNum= item.getNum();
                    customTypeItem.typeName = item.getTypeName();
                    customeItems.add(customTypeItem);
                }
                recyclerView.setAdapter(new PreOrderDIYSelectAdapter(mContext, R.layout.adapter_item_prepay_info, customeItems));
                recyclerView.setVisibility(View.VISIBLE);

            } else {
                recyclerView.setVisibility(View.GONE);
            }
            return inflate;
        }


        @Override
        public void setUiBeforShow() {

        }
    }


    private class CustomeNeedDialog extends BaseDialog<CustomeNeedDialog> {


        public CustomeNeedDialog(Context mContext) {
            super(mContext);
        }

        @Override
        public View onCreateView() {

            final CustomeNeedListAdapter customeNeedListAdapter = new CustomeNeedListAdapter(mContext, R.layout.dialog_custome_need_item, customeTypes);
            View inflate = View.inflate(mContext, R.layout.dialog_custome_need_layout, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - 50, (int) (ScreenUtils.getScreenHeight() * 0.85));
            params.gravity = Gravity.CENTER_HORIZONTAL;
            inflate.setLayoutParams(params);
            RecyclerView recyclerView = (RecyclerView) inflate.findViewById(recycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(customeNeedListAdapter);
            View headView = getLayoutInflater().inflate(R.layout.dialog_custome_need_layout_head, (ViewGroup) recyclerView.getParent(), false);
            customeNeedListAdapter.addHeaderView(headView);
            inflate.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (CustomeType item : customeTypes) {
                        for (CustomeType.CustomTypeItem customTypeItem : item.getCustomTypeItems()) {
                            customTypeItem.setSelect(false);
                            customTypeItem.setNum(1);
                        }
                    }
                    customeNeedListAdapter.setNewData(customeTypes);
                    customeNeedListAdapter.notifyDataSetChanged();
                    ToastUtils.showShort("重置完成");
                    if (TextUtils.isEmpty(hotelRoom.roomRisePrice)){
                        llPriceDetail.tvPrice.setText(String.format("%.2f",Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays+Float.parseFloat(hotelRoom.roomDeposit)));

                    }else{
                        llPriceDetail.tvPrice.setText(String.format("%.2f",Float.parseFloat(hotelRoom.roomPrice) * hotelRoom.differDays+Float.parseFloat(hotelRoom.roomDeposit)+Float.parseFloat(hotelRoom.roomRisePrice)));

                    }
                }
            });

            inflate.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    custemDialg.dismiss();
                }
            });
            return inflate;
        }

        @Override
        public void setUiBeforShow() {

        }

    }

    CustomeDIY customeDIY;


    //获取必须套餐配件
    private void getNeetDIY() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("roomTypeId", hotelRoom.roomTypeId);
        data.put("checkInRoomType", hotelRoom.selectType);
        OkGo.<AAResponse<CustomeDIY>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "getSelfConfig"))
                .execute(new NewsCallback<AAResponse<CustomeDIY>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<CustomeDIY>> response) {
                        customeDIY = response.body().data;
                        if (customeDIY != null && customeDIY.breakfastList != null && !customeDIY.breakfastList.isEmpty()) {
                            paserDataCustomeDIY(customeDIY);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<CustomeDIY>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }

    private void paserDataCustomeDIY(CustomeDIY customeDIY) {
        customeTypes = new ArrayList<>();
        if (customeDIY.breakfastList != null && !customeDIY.breakfastList.isEmpty()) {
            CustomeType breakfast = new CustomeType();
            breakfast.setName("早餐");
            breakfast.setType(DIYType.DIY_TYPE_BRAKFAST.getType());
            breakfast.setCustomTypeItems(customeDIY.breakfastList);
            for (CustomeType.CustomTypeItem item:customeDIY.breakfastList){
                item.setSupportMultSelect(true);
                item.setDiy_type(DIYType.DIY_TYPE_BRAKFAST.getType());
                item.setType_name(breakfast.getName());
            }
            customeTypes.add(breakfast);
        }
        if (customeDIY.fivePieceList != null && !customeDIY.fivePieceList.isEmpty()) {
            CustomeType fivePiece = new CustomeType();
            fivePiece.setName("五件套样式");
            fivePiece.setType(DIYType.DIY_TYPE_FIVE_PIECES.getType());
            fivePiece.setCustomTypeItems(customeDIY.fivePieceList);
            for (CustomeType.CustomTypeItem item:customeDIY.fivePieceList){
                item.setSupportMultSelect(false);
                item.setDiy_type(DIYType.DIY_TYPE_FIVE_PIECES.getType());
                item.setType_name(fivePiece.getName());
            }
            customeTypes.add(fivePiece);
        }

        if (customeDIY.aromaList != null && !customeDIY.aromaList.isEmpty()) {
            CustomeType aromatype = new CustomeType();
            aromatype.setName("香气");
            aromatype.setType(DIYType.DIY_TYPE_ARMOS.getType());
            aromatype.setCustomTypeItems(customeDIY.aromaList);
            for (CustomeType.CustomTypeItem item:customeDIY.aromaList){
                item.setSupportMultSelect(false);
                item.setDiy_type(DIYType.DIY_TYPE_ARMOS.getType());
                item.setType_name(aromatype.getName());
            }
            customeTypes.add(aromatype);
        }

        if (customeDIY.roomLayoutList != null && !customeDIY.roomLayoutList.isEmpty()) {
            CustomeType roomlayout = new CustomeType();
            roomlayout.setName("房间布局");
            roomlayout.setType(DIYType.DIY_TYPE_ROOM_LAYOUT.getType());
            roomlayout.setCustomTypeItems(customeDIY.roomLayoutList);
            for (CustomeType.CustomTypeItem item:customeDIY.roomLayoutList){
                item.setSupportMultSelect(false);
                item.setDiy_type(DIYType.DIY_TYPE_ROOM_LAYOUT.getType());
                item.setType_name(roomlayout.getName());
            }
            customeTypes.add(roomlayout);
        }

        if (customeDIY.wineList != null && !customeDIY.wineList.isEmpty()) {
            CustomeType windType = new CustomeType();
            windType.setName("酒水");
            windType.setType(DIYType.DIY_TYPE_WINE.getType());
            windType.setCustomTypeItems(customeDIY.wineList);
            for (CustomeType.CustomTypeItem item:customeDIY.wineList){
                item.setSupportMultSelect(true);
                item.setDiy_type(DIYType.DIY_TYPE_WINE.getType());
                item.setType_name(windType.getName());
            }
            customeTypes.add(windType);
        }

        if (!customeTypes.isEmpty()) {
            llPesonalCustome.setVisibility(View.VISIBLE);
            initCustomeNeedDialog();

        }
    }


    private void showUserSelectDialog() {
        final String[] stringItems = new String[peopleItemInfos.size()];
        for (int i = 0; i < peopleItemInfos.size(); i++) {
            stringItems[i] = peopleItemInfos.get(i).name;
        }
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, llPriceDetail);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvHotelUserName.setText(stringItems[position]);
                etUserTel.setText(peopleItemInfos.get(position).mobilePhone);
                dialog.dismiss();
            }
        });
    }


    private void showSelectTimeDialog() {
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, arriveTimes, llPriceDetail);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvArriveTime.setText(arriveTimes[position]);
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_add_user, R.id.ll_arrive_time, R.id.ll_pesonal_custome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_add_user:
                if (!peopleItemInfos.isEmpty()) {
                    showUserSelectDialog();
                } else {
                    openActivity(CommonInfoActivity.class);
                }
                break;
            case R.id.ll_arrive_time:
                if (arriveTimes!=null ) {
                    showSelectTimeDialog();
                }else{
                    getOrderTime();
                }
                break;
            case R.id.ll_pesonal_custome:
                if (customeDIY != null) {
                    custemDialg.show();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
