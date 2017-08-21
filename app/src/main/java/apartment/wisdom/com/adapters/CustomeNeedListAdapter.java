package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.city.CustomeNeedListItemAdapter;
import apartment.wisdom.com.beans.CustomeType;
import apartment.wisdom.com.widgets.views.MyGridView;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 首页活动适配器
*/
public class CustomeNeedListAdapter extends BaseQuickAdapter<CustomeType,BaseViewHolder> {
    private Context context;
    public CustomeNeedListAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<CustomeType> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CustomeType item) {
        ( (TextView)helper.getView(R.id.tv_title)).setText(item.getName());
        ((MyGridView)helper.getView(R.id.lv_custome)).setAdapter(new CustomeNeedListItemAdapter(context,item.getCustomTypeItems()));
        ((MyGridView)helper.getView(R.id.lv_custome)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<CustomeType.CustomTypeItem> customTypeItems = item.getCustomTypeItems();
                for (CustomeType.CustomTypeItem item:customTypeItems){
                    item.setSelect(false);
                }

                customTypeItems.get(i).setSelect(true);
                ((CustomeNeedListItemAdapter) ((MyGridView) helper.getView(R.id.lv_custome)).getAdapter()).notifyDataSetChanged();
            }
        });
    }
}
