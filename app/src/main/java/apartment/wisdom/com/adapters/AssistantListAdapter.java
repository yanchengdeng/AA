package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

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

    }
}
