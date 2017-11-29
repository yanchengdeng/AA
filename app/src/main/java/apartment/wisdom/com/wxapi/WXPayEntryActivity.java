package apartment.wisdom.com.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.AutoSelectRoomActivity;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.beans.PreOrderInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.events.WXpaySuccessEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

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
    @BindView(R.id.tv_room_num)
    TextView tvRoomNum;
    @BindView(R.id.tv_choose_room_selves)
    TextView tvChooseRoomSelves;
    @BindView(R.id.tv_order_room_tip)
    TextView tvOrderRoomTip;
    @BindView(R.id.detail_delay_tv)
    TextView detailDelayTv;
    @BindView(R.id.fl_hotel_keep_time)
    FrameLayout flHotelKeepTime;
    @BindView(R.id.detail_exit_tv)
    TextView detailExitTv;
    @BindView(R.id.ll_show_only_all_day)
    LinearLayout llShowOnlyAllDay;
    @BindView(R.id.tv_room_code)
    TextView tvRoomCode;
    private IWXAPI api;

    private PreOrderInfo preOrderInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
        preOrderInfo = (PreOrderInfo) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
        api = WXAPIFactory.createWXAPI(this, Constants.WXPay.APP_ID);
        api.handleIntent(getIntent(), this);
        tvTittle.setText(getString(R.string.pay_success));
        if (preOrderInfo!=null &&  !TextUtils.isEmpty(preOrderInfo.checkInNo)) {
            tvRoomCode.setText(preOrderInfo.checkInNo);
        }else{
            tvRoomCode.setText(""+Constants.CHECK_ROOM_CODE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    private boolean isSuccess;

    @Override
    public void onResp(BaseResp req) {

        if (req.errCode == BaseResp.ErrCode.ERR_OK) {
            LogUtils.w("dyc","----");
            EventBus.getDefault().post(new WXpaySuccessEvent());
            EventBus.getDefault().post(new PayOrRechargeSuccess());
        } else if (req.errCode== BaseResp.ErrCode.ERR_USER_CANCEL){
            LogUtils.w("dyc","----     "+req.errCode);
            Toast.makeText(mContext,"取消支付",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(mContext,"支付失败",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick({R.id.back, R.id.tv_choose_room_selves})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_choose_room_selves:
                openActivity(AutoSelectRoomActivity.class, getIntent().getExtras());
                finish();
                break;
        }
    }
}