package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.RechargeListInfo;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 充值适配器
 */
public class ChargeDetailListAdapter extends BaseQuickAdapter<RechargeListInfo.RechargeItem, BaseViewHolder> {
    public ChargeDetailListAdapter(@LayoutRes int layoutResId, @Nullable List<RechargeListInfo.RechargeItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeListInfo.RechargeItem item) {
        ((TextView) helper.getView(R.id.tv_hotel_name)).setText(item.rechargeTitle);
        ((TextView) helper.getView(R.id.tv_integral_time)).setText(item.rechargeDate);
        ((TextView) helper.getView(R.id.tv_integraltype)).setText("+");
        ((TextView) helper.getView(R.id.tv_integralcount)).setText(item.rechargePrice);
    }
}
