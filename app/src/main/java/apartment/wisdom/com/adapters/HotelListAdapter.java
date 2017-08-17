package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.beans.HotelListInfo;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 首页活动适配器
*/
public class HotelListAdapter extends BaseQuickAdapter<HotelListInfo,BaseViewHolder> {
    public HotelListAdapter(@LayoutRes int layoutResId, @Nullable List<HotelListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelListInfo item) {

    }
}
