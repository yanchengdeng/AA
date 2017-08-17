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

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.TicketListAdapter;
import apartment.wisdom.com.beans.TicketInfo;
import apartment.wisdom.com.commons.Constants;
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

    private TicketListAdapter adapter;
    private List<TicketInfo> hotelCommendItems;
    public static final int SELECT_COUPON_SUCCESS_ERSULT_CODE = 0x112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.conpon));

        hotelCommendItems = new ArrayList<>();
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        hotelCommendItems.add(new TicketInfo());
        adapter = new TicketListAdapter(R.layout.adapter_ticket_item, hotelCommendItems);
        lvCoupon.setLayoutManager(new LinearLayoutManager(this));
        lvCoupon.setAdapter(adapter);

        initStatus();

        cbNeedCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    initStatus();
                }else{
                    cbNeedCoupon.setChecked(false);
                    lvCoupon.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.GONE);
                }
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,PayActivity.class);
                intent.putExtra(Constants.PASS_OBJECT,hotelCommendItems.get(position));
                setResult(SELECT_COUPON_SUCCESS_ERSULT_CODE,intent);
                finish();
            }
        });
    }


    private void initStatus(){
        if (hotelCommendItems.size()>0){
            cbNeedCoupon.setChecked(true);
            lvCoupon.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }else{
            cbNeedCoupon.setChecked(false);
            lvCoupon.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
