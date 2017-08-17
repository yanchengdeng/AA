package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.beans.TicketInfo;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 优惠劵适配器
*/
public class TicketListAdapter extends BaseQuickAdapter<TicketInfo,BaseViewHolder> {
    public TicketListAdapter(@LayoutRes int layoutResId, @Nullable List<TicketInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketInfo item) {

    }
}
