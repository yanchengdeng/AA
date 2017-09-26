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
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.RechargeDepositAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.DepostInfo;
import apartment.wisdom.com.beans.PayInfo;
import apartment.wisdom.com.beans.PayResult;
import apartment.wisdom.com.beans.WXPayInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.PayStyle;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
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

    //（0-微信充值，1-充值宝充值）
    private int paySyle = PayStyle.PAY_STYLE_ALIPAY.getType();

    private RechargeDepositAdapter rechargeDepositAdapter;
    private List<DepostInfo.DepostInfoItem> depostInfos = new ArrayList<>();
    private DepostInfo.DepostInfoItem selectDeposit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechager);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.recharge));
        getDeposit();
    }

    //获取充值 额度分类
    private void getDeposit() {

        Map<String, Object> data = new HashMap<String, Object>();
        OkGo.<AAResponse<DepostInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "rechargePrice"))
                .execute(new NewsCallback<AAResponse<DepostInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<DepostInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        DepostInfo depostInfo = response.body().data;
                        if (!depostInfo.moneyList.isEmpty()) {
                            setDepostInfos(depostInfo.moneyList);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<DepostInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }


    private void setDepostInfos(List<DepostInfo.DepostInfoItem> moneyList) {
        depostInfos = moneyList;
        depostInfos.get(0).setSelect(true);
        selectDeposit = depostInfos.get(0);
        rechargeDepositAdapter = new RechargeDepositAdapter(mContext, R.layout.adapter_deposit_item, depostInfos);
        recycle.setLayoutManager(new GridLayoutManager(mContext, 2));
        recycle.setAdapter(rechargeDepositAdapter);
        rechargeDepositAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (DepostInfo.DepostInfoItem item : depostInfos) {
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
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("payWay", PayStyle.PAY_STYLE_ALIPAY.getType());
        data.put("rechargeId", selectDeposit.rechargeId);
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<PayInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "walletRecharge"))
                .execute(new NewsCallback<AAResponse<PayInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<PayInfo>> response) {
                        payInfo = response.body().data;
                        WXPayInfo wxPayInfo = new WXPayInfo();
                        wxPayInfo.setAppid(Constants.WXPay.APP_ID);
                        wxPayInfo.setPartnerid(Constants.WXPay.MCH_ID);
                        wxPayInfo.setNoncestr("3LUm8dtC60rRJKfT");
                        wxPayInfo.setPrepayid("");
                        wxPayInfo.setSign("724AED95F41CE97855D99048D1EB336A");
                        wxPayInfo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
                        wxPayInfo.setWxpackage("Sign=WXPay");
                        payInfo.setWxpayinfo(wxPayInfo);
                        //1  根据提交的充值信息 及类型  生成相对应的 充值验证返回值
                        //2 检查是否安装充值客户端 进行充值
                        //3充值完成后 回调用户信息接口 进行数据更新

                        doPay();
                    }

                    @Override
                    public void onError(Response<AAResponse<PayInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
}

    private void doPay() {
        if (paySyle == PayStyle.PAY_STYLE_WX.getType()) {
            if (!checkWeiXin()) {
                mSVProgressHUD.showInfoWithStatus("您未安装微信客户端!", SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
                return;
            } else {
                //微信充值
                wxPrePay();
            }
        } else if (paySyle == PayStyle.PAY_STYLE_ALIPAY.getType()) {
            if (!checkALi()) {
                mSVProgressHUD.showInfoWithStatus("您未安装充值宝客户端!", SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
                return;
            } else {
                //充值宝充值
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
     * 调用微信充值
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
//            mSVProgressHUD.showWithStatus("正常调起充值...", SVProgressHUD.SVProgressHUDMaskType.Clear);
            // 在充值之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } else {
            mSVProgressHUD.showInfoWithStatus(getString(R.string.do_later), SVProgressHUD.SVProgressHUDMaskType.Clear);
            mSVProgressHUD.dismiss();
        }
    }


    /***
     * 充值费用
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

        // 完整的符合充值宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + AliPayUtiils.getSignType();*/

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RechagerActivity.this);
                // 调用充值接口，获取充值结果
                String result = alipay.pay(payInfo.getOrderStr(), true);

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

                    // 充值宝返回此次充值结果及加签，建议对充值宝签名信息拿签约时充值宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表充值成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        mSVProgressHUD.showSuccessWithStatus("充值成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new PayOrRechargeSuccess());
                                finish();
                            }
                        }, 1500);


                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        mSVProgressHUD.showInfoWithStatus("取消充值", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能充值失败
                        // “8000”代表充值结果因为充值渠道原因或者系统原因还在等待充值结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            mSVProgressHUD.showSuccessWithStatus("充值结果确认中", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        } else {
                            // 其他值就可以判断为充值失败，包括用户主动取消充值，或者系统返回的错误
                            mSVProgressHUD.showSuccessWithStatus("充值失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
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
