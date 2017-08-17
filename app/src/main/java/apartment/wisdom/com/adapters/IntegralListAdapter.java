package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.beans.IntegralItem;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 积分适配器
*/
public class IntegralListAdapter extends BaseQuickAdapter<IntegralItem,BaseViewHolder> {
    public IntegralListAdapter(@LayoutRes int layoutResId, @Nullable List<IntegralItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralItem item) {

    }
}
