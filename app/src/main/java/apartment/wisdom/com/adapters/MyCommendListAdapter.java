package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.MyCommendList;

public class MyCommendListAdapter extends BaseQuickAdapter<MyCommendList.CommendInfo,BaseViewHolder> {
    private Context context;
    public MyCommendListAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<MyCommendList.CommendInfo> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommendList.CommendInfo item) {
        Glide.with(context).load(item.storeImage).into((ImageView) helper.getView(R.id.img));
        ((TextView)helper.getView(R.id.tv_tittle)).setText(item.storeName+item.address);
        ((TextView)helper.getView(R.id.tv_price)).setText(""+item.storePrice);
        ((TextView)helper.getView(R.id.tv_score)).setText(item.storeScore);
        ((RatingBar)helper.getView(R.id.app_star_score)).setRating(Float.parseFloat(item.storeScore));


    }
}
