package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.TicketListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.TicketInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.TicketType;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/17  11:21
 * Email: yanchengdeng@gmail.com
 * Describle:  选择优惠劵
 */
public class SelectCouponActivity extends BaseActivity {

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
    @BindView(R.id.cb_need_coupon)
    CheckBox cbNeedCoupon;
    @BindView(R.id.lv_coupon)
    RecyclerView lvCoupon;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.fl_cpupon)
    FrameLayout flCpupon;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_show_coupon)
    LinearLayout llShowCoupon;
    private String ticketType = TicketType.TICKET_TYPE_NOT_USER.getType();

    private TicketListAdapter adapter;
    private List<TicketInfo.TicketInfoItem> hotelCommendItems = new ArrayList<>();
    private TicketInfo.TicketInfoItem selectTicket;
    public static final int SELECT_COUPON_SUCCESS_ERSULT_CODE = 0x112;
    private int preSelect;
    private String roomTypeId, storeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.conpon));
        adapter = new TicketListAdapter(true,R.layout.adapter_ticket_item, hotelCommendItems);
        lvCoupon.setLayoutManager(new LinearLayoutManager(this));
        lvCoupon.setAdapter(adapter);
        storeId = getIntent().getStringExtra(Constants.PASS_STRING);
        roomTypeId = getIntent().getStringExtra(Constants.PASS_SELECT_HOTLE_TYPE);

        cbNeedCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    initStatus();
                } else {
                    cbNeedCoupon.setChecked(false);
                    lvCoupon.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.GONE);
                }
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (TicketInfo.TicketInfoItem itme : hotelCommendItems) {
                    itme.isSelect = false;
                }
                hotelCommendItems.get(position).isSelect = true;
                adapter.notifyItemChanged(position);
                adapter.notifyItemChanged(preSelect);
                selectTicket = hotelCommendItems.get(position);
                preSelect = position;
            }
        });
        getTicketList();
    }


    //获取优惠劵
    private void getTicketList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        data.put("storeId",storeId);
        data.put("roomTypeId",roomTypeId);
        OkGo.<AAResponse<TicketInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "getCoupon"))
                .execute(new NewsCallback<AAResponse<TicketInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<TicketInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        TicketInfo ticketInfo = response.body().data;
                        if (ticketInfo != null && !ticketInfo.couponList.isEmpty()) {
                            if (filterTicketsByType(ticketInfo.couponList).isEmpty()) {
                                adapter.setNewData(null);
                            } else {
                                adapter.setNewData(filterTicketsByType(ticketInfo.couponList));
                                initStatus();
                            }
                        } else {
                            adapter.setNewData(null);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<TicketInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    private List<TicketInfo.TicketInfoItem> filterTicketsByType(List<TicketInfo.TicketInfoItem> couponList) {
        List<TicketInfo.TicketInfoItem> infoItems = new ArrayList<>();
        for (TicketInfo.TicketInfoItem item : couponList) {
            if (item.useStatus.equals(ticketType)) {
                infoItems.add(item);
            }
        }
        hotelCommendItems = infoItems;
        return infoItems;
    }


    private void initStatus() {
        if (hotelCommendItems.size() > 0) {
            cbNeedCoupon.setChecked(true);
            lvCoupon.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            cbNeedCoupon.setChecked(false);
            lvCoupon.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            selectTicket = new TicketInfo.TicketInfoItem();
        }
    }

    @OnClick({R.id.back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_submit:
                SelectResult();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SelectResult();
    }

    private void SelectResult() {
        Intent intent = new Intent(mContext, PayActivity.class);
        intent.putExtra(Constants.PASS_OBJECT, selectTicket);
        setResult(SELECT_COUPON_SUCCESS_ERSULT_CODE, intent);
        finish();
    }
}
