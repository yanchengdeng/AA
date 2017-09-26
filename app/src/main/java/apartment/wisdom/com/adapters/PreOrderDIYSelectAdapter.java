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
import apartment.wisdom.com.enums.DIYType;

public class PreOrderDIYSelectAdapter extends BaseQuickAdapter<PreOrderInfo.CustomeItem, BaseViewHolder> {


    private Context context;

    public PreOrderDIYSelectAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<PreOrderInfo.CustomeItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PreOrderInfo.CustomeItem item) {
        ((TextView)helper.getView(R.id.tv_name)).setText("个性消费 - "+item.typeName+" - "+item.consumeName);

        if (item.type.equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
            ((TextView) helper.getView(R.id.tv_price)).setText(item.consumePrice+" x "+item.consumeNum + "份");
        } else {
            ((TextView) helper.getView(R.id.tv_price)).setText(item.consumePrice);

        }


    }
}
