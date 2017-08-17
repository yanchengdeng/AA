package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.DepostInfo;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 充值返现适配器
*/
public class RechargeDepositAdapter extends BaseQuickAdapter<DepostInfo,BaseViewHolder> {
    private Context context;
    public RechargeDepositAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<DepostInfo> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DepostInfo item) {
        if (item.isSelect()) {
            helper.getView(R.id.ll_deposit_ui).setBackgroundColor(context.getResources().getColor(R.color.Yellow));
        }else{
            helper.getView(R.id.ll_deposit_ui).setBackgroundColor(context.getResources().getColor(R.color.white));

        }

    }
}
