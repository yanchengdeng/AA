package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.HotelOrderItem;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 酒店订单适配器
*/
public class HotelOrderListAdapter extends BaseQuickAdapter<HotelOrderItem,BaseViewHolder> {
    public HotelOrderListAdapter(@LayoutRes int layoutResId, @Nullable List<HotelOrderItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelOrderItem item) {
        helper.addOnClickListener(R.id.bt_order_pre);
        helper.addOnClickListener(R.id.bt_order_delete);
        helper.addOnClickListener(R.id.bt_do_comment);

    }
}
