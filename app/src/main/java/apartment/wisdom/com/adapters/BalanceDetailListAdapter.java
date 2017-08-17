package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.beans.BalanceDetailInfo;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 消费适配器
*/
public class BalanceDetailListAdapter extends BaseQuickAdapter<BalanceDetailInfo,BaseViewHolder> {
    public BalanceDetailListAdapter(@LayoutRes int layoutResId, @Nullable List<BalanceDetailInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalanceDetailInfo item) {

    }
}
