package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.PreOrderInfo;

public class PayOrderListDetailAdapter extends BaseQuickAdapter<PreOrderInfo.CustomeItem, BaseViewHolder> {


    private Context context;
    public PayOrderListDetailAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<PreOrderInfo.CustomeItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PreOrderInfo.CustomeItem item) {
        ((TextView)helper.getView(R.id.tv_name)).setText(item.consumeName);
        ((TextView)helper.getView(R.id.tv_num)).setText("X "+item.consumeNum);
        ((TextView)helper.getView(R.id.tv_price)).setText(context.getString(R.string.unit_rmb)+item.consumePrice);


    }
}
