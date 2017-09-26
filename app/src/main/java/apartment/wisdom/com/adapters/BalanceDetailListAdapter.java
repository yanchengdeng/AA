package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.CusumListInfo;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 消费适配器
 */
public class BalanceDetailListAdapter extends BaseQuickAdapter<CusumListInfo.CusumItem, BaseViewHolder> {
    public BalanceDetailListAdapter(@LayoutRes int layoutResId, @Nullable List<CusumListInfo.CusumItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CusumListInfo.CusumItem item) {
        ((TextView) helper.getView(R.id.tv_hotel_name)).setText(item.consumeTitle);
        ((TextView) helper.getView(R.id.tv_integral_time)).setText(item.consumeDate);
        ((TextView) helper.getView(R.id.tv_integraltype)).setText("-");
        ((TextView) helper.getView(R.id.tv_integralcount)).setText(item.consumePrice);
    }
}
