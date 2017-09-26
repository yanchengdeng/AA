package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.DepostInfo;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 充值返现适配器
 */
public class RechargeDepositAdapter extends BaseQuickAdapter<DepostInfo.DepostInfoItem, BaseViewHolder> {
    private Context context;

    public RechargeDepositAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<DepostInfo.DepostInfoItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DepostInfo.DepostInfoItem item) {
        if (item.isSelect()) {
            helper.getView(R.id.ll_deposit_ui).setBackgroundColor(context.getResources().getColor(R.color.Yellow));
        } else {
            helper.getView(R.id.ll_deposit_ui).setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        if (!TextUtils.isEmpty(item.rechargeMoney)) {
            ((TextView) helper.getView(R.id.tv_recharge_money)).setText(String.format(context.getString(R.string.charge_money_tips), new Object[]{item.rechargeMoney}));
        }

        if (!TextUtils.isEmpty(item.giveMoney)) {
            ((TextView) helper.getView(R.id.tv_reback_money)).setText(String.format(context.getString(R.string.gift_money_tips), new Object[]{item.giveMoney}));
        }
    }
}
