package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.AssistantItemInfo;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 历史适配器
 */
public class HistoryListAdapter extends BaseQuickAdapter<AssistantItemInfo, BaseViewHolder> {
    public HistoryListAdapter(@LayoutRes int layoutResId, @Nullable List<AssistantItemInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssistantItemInfo item) {
        helper.addOnClickListener(R.id.btn_trip_history_comment);
        helper.addOnClickListener(R.id.btn_trip_history_delete);

    }
}
