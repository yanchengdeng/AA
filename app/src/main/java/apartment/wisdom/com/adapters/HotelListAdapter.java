package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.HotelListInfo;

/**
*
* Author: 邓言诚  Create at : 17/8/13  17:02
* Email: yanchengdeng@gmail.com
* Describle: 首页活动适配器
*/
public class HotelListAdapter extends BaseQuickAdapter<HotelListInfo.HotelListItem,BaseViewHolder> {
    private Context context;
    public HotelListAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<HotelListInfo.HotelListItem> data) {
        super(layoutResId, data);
        this.context   = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelListInfo.HotelListItem item) {
        if (!item.storeImageList.isEmpty()){
            Glide.with(context).load(item.storeImageList.get(0).storeImage).into((ImageView)helper.getView(R.id.img));
        }
        ((TextView)helper.getView(R.id.tv_tittle)).setText(item.storeName);
        ((TextView)helper.getView(R.id.tv_score)).setText(item.storeScore);
        ((TextView)helper.getView(R.id.tv_apprise_content)).setText(item.storeRemark);
        helper.getView(R.id.tv_apprise_content).setVisibility(View.GONE);
        ((TextView)helper.getView(R.id.tv_apprise_all)).setText(item.storePayment);
        ((TextView)helper.getView(R.id.tv_price)).setText(item.storeRoomMinPrice);


    }
}
