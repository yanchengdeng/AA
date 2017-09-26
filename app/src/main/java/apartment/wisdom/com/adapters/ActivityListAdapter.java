package apartment.wisdom.com.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.ActivityDetailActivity;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.beans.HomeAdInfoList;
import apartment.wisdom.com.commons.Constants;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle: 首页活动适配器
 */
public class ActivityListAdapter extends BaseQuickAdapter<HomeAdInfoList, BaseViewHolder> {
    private Context context;

    public ActivityListAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<HomeAdInfoList> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeAdInfoList item) {
        Glide.with(context).load(item.activityImage).into((ImageView) helper.getView(R.id.img));
        if (!TextUtils.isEmpty(item.activityTitle)) {
            ((TextView) helper.getView(R.id.tv_tittle)).setText(item.activityTitle);
        }


        ((TextView) helper.getView(R.id.tv_time)).setText(item.beginDate+" ~ "+item.endDate);

        ((TextView) helper.getView(R.id.tv_status)).setText(""+item.activityButton);
        ( (TextView)helper.getView(R.id.tv_content)).setText(item.activitySubTitle);
        helper.getView(R.id.ll_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(item.activityUrl)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PASS_OBJECT, item);
                ((BaseActivity) context).openActivity(ActivityDetailActivity.class, bundle);
            }
        });
    }
}
