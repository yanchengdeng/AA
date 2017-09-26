package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.HotelCommendItem;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 评论适配器
 */
public class CommendListAdapter extends BaseQuickAdapter<HotelCommendItem, BaseViewHolder> {
    public CommendListAdapter(@LayoutRes int layoutResId, @Nullable List<HotelCommendItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelCommendItem item) {
        ((TextView) helper.getView(R.id.tv_user_name)).setText(item.username);
        ((RatingBar) helper.getView(R.id.rb_score)).setRating(Float.parseFloat(item.customerScore));
        ((TextView) helper.getView(R.id.tv_comment_score)).setText(item.customerScore);
        ((TextView) helper.getView(R.id.tv_comment_creat_time)).setText(item.evaluateDate);
        if (!TextUtils.isEmpty(item.storeEvaluate)) {
            ((TextView) helper.getView(R.id.tv_reply_content)).setText(item.storeEvaluate);
        }
        if (!TextUtils.isEmpty(item.customerEvaluate)) {
            ((TextView) helper.getView(R.id.tv_comment_content)).setText(item.customerEvaluate);
        }
    }
}
