package apartment.wisdom.com.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import apartment.wisdom.com.activities.ActivityDetailActivity;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.beans.HomeAdInfoList;
import apartment.wisdom.com.commons.Constants;

/**
 * Author: 邓言诚  Create at : 17/8/8  11:11
 * Email: yanchengdeng@gmail.com
 * Describle: 首页广告适配器
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private List<HomeAdInfoList> adInfos;
    private Context context;


    public GalleryPagerAdapter(List<HomeAdInfoList> homeAdInfos, FragmentActivity context) {
        this.adInfos = homeAdInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return adInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView item = new ImageView(context);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(context).load(adInfos.get(position)).bitmapTransform(new BlurTransformation(context, 25)).into(item);
        Glide.with(context).load(adInfos.get(position).activityImage).into(item);

        container.addView(item);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(adInfos.get(position).activityUrl)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PASS_OBJECT, adInfos.get(position));
                ((BaseActivity) context).openActivity(ActivityDetailActivity.class, bundle);
            }
        });
        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}