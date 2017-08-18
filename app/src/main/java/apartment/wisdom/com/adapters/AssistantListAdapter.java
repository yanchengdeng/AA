package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AssistantItemInfo;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 旅行助手适配器
 */
public class AssistantListAdapter extends BaseQuickAdapter<AssistantItemInfo, BaseViewHolder> {
    public AssistantListAdapter(@LayoutRes int layoutResId, @Nullable List<AssistantItemInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssistantItemInfo item) {
        helper.addOnClickListener(R.id.btn_cancle_order);
        helper.addOnClickListener(R.id.btn_app_open_door);
        helper.addOnClickListener(R.id.btn_auto_out);
        if (item.isOnlineCard()){
            helper.getView(R.id.btn_auto_out).setVisibility(View.VISIBLE);
            helper.getView(R.id.btn_app_open_door).setVisibility(View.VISIBLE);
            helper.getView(R.id.btn_cancle_order).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.btn_auto_out).setVisibility(View.GONE);
            helper.getView(R.id.btn_app_open_door).setVisibility(View.GONE);
            helper.getView(R.id.btn_cancle_order).setVisibility(View.VISIBLE);
        }

    }
}
