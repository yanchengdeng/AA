package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.beans.ActivityInfo;
/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 首页活动适配器
*/
public class ActivityListAdapter extends BaseQuickAdapter<ActivityInfo,BaseViewHolder> {
    public ActivityListAdapter(@LayoutRes int layoutResId, @Nullable List<ActivityInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityInfo item) {

    }
}
