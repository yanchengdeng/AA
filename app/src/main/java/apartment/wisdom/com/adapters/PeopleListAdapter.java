package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.CustomPeopleList;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 常用旅客适配器
 */
public class PeopleListAdapter extends BaseQuickAdapter<CustomPeopleList.CustomPeopleItem, BaseViewHolder> {
    public PeopleListAdapter(@LayoutRes int layoutResId, @Nullable List<CustomPeopleList.CustomPeopleItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomPeopleList.CustomPeopleItem item) {
        ((TextView)helper.getView(R.id.tv_name)).setText(item.name);
        ((TextView)helper.getView(R.id.tv_phone)).setText(item.mobilePhone);

    }
}
