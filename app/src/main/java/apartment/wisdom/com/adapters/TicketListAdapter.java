package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.TicketInfo;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 优惠劵适配器
 */
public class TicketListAdapter extends BaseQuickAdapter<TicketInfo.TicketInfoItem, BaseViewHolder> {
    public TicketListAdapter(@LayoutRes int layoutResId, @Nullable List<TicketInfo.TicketInfoItem> data) {
        super(layoutResId, data);
    }

    private boolean isShowSelect = false;

    public TicketListAdapter(boolean isShow,@LayoutRes int layoutResId, @Nullable List<TicketInfo.TicketInfoItem> data) {
        super(layoutResId, data);
        this.isShowSelect = isShow;
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketInfo.TicketInfoItem item) {
        ((TextView) helper.getView(R.id.tv_prititle)).setText(item.couponName);
        ((TextView) helper.getView(R.id.tv_pridata)).setText(item.validDate);
        ((TextView) helper.getView(R.id.tv_priprice)).setText(item.couponMoney);
        ImageView ivSelect =helper.getView(R.id.iv_select);
        if (isShowSelect){
            helper.getView(R.id.iv_select).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.iv_select).setVisibility(View.GONE);
        }
        if (item.isSelect){
            ivSelect.setImageResource(R.mipmap.room_sele_s_ic);
        }else{
            ivSelect.setImageResource(R.mipmap.room_sele_ic);
        }

    }
}
