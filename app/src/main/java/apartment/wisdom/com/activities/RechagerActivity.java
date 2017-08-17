package apartment.wisdom.com.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.RechargeDepositAdapter;
import apartment.wisdom.com.beans.AliPayInfo;
import apartment.wisdom.com.beans.DepostInfo;
import apartment.wisdom.com.beans.PayInfo;
import apartment.wisdom.com.beans.PayResult;
import apartment.wisdom.com.beans.WXPayInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.PayStyle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/16  21:54
 * Email: yanchengdeng@gmail.com
 * Describle: c充值
 */
public class RechagerActivity extends BaseActivity {

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
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.tv_alipay_tittle)
    TextView tvAlipayTittle;
    @BindView(R.id.iv_select_alipay)
    ImageView ivSelectAlipay;
    @BindView(R.id.rl_pay_alipay)
    RelativeLayout rlPayAlipay;
    @BindView(R.id.iv_wx)
    ImageView ivWx;
    @BindView(R.id.tv_wx_tittle)
    TextView tvWxTittle;
    @BindView(R.id.iv_select_wx)
    ImageView ivSelectWx;
    @BindView(R.id.rl_pay_wx)
    RelativeLayout rlPayWx;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;

    private int paySyle = PayStyle.PAY_STYLE_ALIPAY.getType();

    private RechargeDepositAdapter rechargeDepositAdapter;
    private List<DepostInfo> depostInfos = new ArrayList<>();
    private DepostInfo selectDeposit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechager);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.recharge));
        getDeposit();
    }

    private void getDeposit() {
        depostInfos.add(new DepostInfo());
        depostInfos.add(new DepostInfo());
        depostInfos.add(new DepostInfo());
        depostInfos.add(new DepostInfo());
        rechargeDepositAdapter = new RechargeDepositAdapter(mContext, R.layout.adapter_deposit_item, depostInfos);
        recycle.setLayoutManager(new GridLayoutManager(mContext, 2));
        recycle.setAdapter(rechargeDepositAdapter);

        rechargeDepositAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (DepostInfo item : depostInfos) {
                    item.setSelect(false);
                }
                depostInfos.get(position).setSelect(true);
                selectDeposit = depostInfos.get(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.back, R.id.rl_pay_alipay, R.id.rl_pay_wx, R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_pay_alipay:
                paySyle = PayStyle.PAY_STYLE_ALIPAY.getType();
                ivSelectAlipay.setImageResource(R.mipmap.continue_rb_check_theme);
                ivSelectWx.setImageResource(R.mipmap.continue_rb_uncheck_theme);
                break;
            case R.id.rl_pay_wx:
                paySyle = PayStyle.PAY_STYLE_WX.getType();
                ivSelectAlipay.setImageResource(R.mipmap.continue_rb_uncheck_theme);
                ivSelectWx.setImageResource(R.mipmap.continue_rb_check_theme);
                break;
            case R.id.tv_recharge:
                if (depostInfos != null) {
                    doCharge();
                } else {
                    mSVProgressHUD.showInfoWithStatus(getString(R.string.no_select_deposit), SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
                break;
        }
    }

    private PayInfo payInfo;

    private void doCharge() {
        //// TODO: 17/8/16  此接口可获得payInfo信息
        payInfo = new PayInfo();
        AliPayInfo aliPayInfo = new AliPayInfo();
        aliPayInfo.setOrderStr("app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D");

        payInfo.setPay(aliPayInfo);


        WXPayInfo wxPayInfo = new WXPayInfo();
        wxPayInfo.setAppid(Constants.WXPay.APP_ID);
        wxPayInfo.setPartnerid(Constants.WXPay.MCH_ID);
        wxPayInfo.setNoncestr("3LUm8dtC60rRJKfT");
        wxPayInfo.setPrepayid("");
        wxPayInfo.setSign("724AED95F41CE97855D99048D1EB336A");
        wxPayInfo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        wxPayInfo.setWxpackage("Sign=WXPay");
        payInfo.setWxpayinfo(wxPayInfo);
        //1  根据提交的充值信息 及类型  生成相对应的 支付验证返回值
        //2 检查是否安装支付客户端 进行支付
        //3支付完成后 回调用户信息接口 进行数据更新

        doPay();


    }

    private void doPay() {
        if (paySyle == PayStyle.PAY_STYLE_WX.getType()) {
            if (!checkWeiXin()) {
                mSVProgressHUD.showInfoWithStatus("您未安装微信客户端!", SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
                return;
            } else {
                //微信支付
                wxPrePay();
            }
        } else if (paySyle == PayStyle.PAY_STYLE_ALIPAY.getType()) {
            if (!checkALi()) {
                mSVProgressHUD.showInfoWithStatus("您未安装支付宝客户端!", SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
                return;
            } else {
                //支付宝支付
                pay();
            }
        }
    }

    private void wxPrePay() {
        mSVProgressHUD.showWithStatus("获取订单信息", SVProgressHUD.SVProgressHUDMaskType.Clear);
        weixinPay(payInfo.getWxpayinfo());
    }

    private IWXAPI api;

    /**
     * 调用微信支付
     *
     * @param wxPayAppId
     */
    private void weixinPay(WXPayInfo wxPayAppId) {
        api = WXAPIFactory.createWXAPI(this, wxPayAppId.getAppid());
        api.registerApp(wxPayAppId.getAppid());
        if (wxPayAppId != null) {
            PayReq req = new PayReq();
            req.appId = wxPayAppId.getAppid();
            req.partnerId = wxPayAppId.getPartnerid();
            req.prepayId = wxPayAppId.getPrepayid();
            req.nonceStr = wxPayAppId.getNoncestr();
            req.timeStamp = wxPayAppId.getTimestamp();
            req.packageValue = "Sign=WXPay";
            req.sign = wxPayAppId.getSign();
            LogUtils.w("dyc", req.checkArgs() + "---------");
//            mSVProgressHUD.showWithStatus("正常调起支付...", SVProgressHUD.SVProgressHUDMaskType.Clear);
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } else {
            mSVProgressHUD.showInfoWithStatus(getString(R.string.do_later), SVProgressHUD.SVProgressHUDMaskType.Clear);
            mSVProgressHUD.dismiss();
        }
    }


    /***
     * 支付费用
     */
    private void pay() {
        mSVProgressHUD.showWithStatus("加载中...");
      /*  // 订单
        String orderInfo = AliPayUtiils.getOrderInfo(payInfo.getAlipayinfo());//String.valueOf(payMoney)

        // 对订单做RSA 签名
        String sign = AliPayUtiils.sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + AliPayUtiils.getSignType();*/

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RechagerActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo.getPay().getOrderStr(), true);

                Message msg = new Message();
                msg.what = Constants.AliPay.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mSVProgressHUD.dismiss();
            switch (msg.what) {
                case Constants.AliPay.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        mSVProgressHUD.showSuccessWithStatus("支付成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1500);


                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        mSVProgressHUD.showInfoWithStatus("取消支付", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            mSVProgressHUD.showSuccessWithStatus("支付结果确认中", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            mSVProgressHUD.showSuccessWithStatus("支付失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
            return false;
        }
    });


    private boolean checkWeiXin() {
        try {
            getPackageManager().getApplicationInfo("com.tencent.mm", PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private boolean checkALi() {
        try {
            getPackageManager().getApplicationInfo("com.eg.android.AlipayGphone", PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
