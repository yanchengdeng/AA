package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 旅行助手适配器
 */
public class PhotoGridViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    public PhotoGridViewAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(context).load(item).placeholder(R.mipmap.blank_default_nomal_bg).into((ImageView) helper.getView(R.id.iv_photo));

    }
}
