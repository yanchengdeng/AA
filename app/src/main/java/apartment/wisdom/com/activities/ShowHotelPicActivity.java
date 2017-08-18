package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.PhotoGridViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowHotelPicActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private PhotoGridViewAdapter photoGridViewAdapter;
    private List<String> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hotel_pic);
        ButterKnife.bind(this);
        tvTittle.setText("酒店照片(6张)");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502997811859&di=61393767367f3ab4bfd80fad86a61b5c&imgtype=0&src=http%3A%2F%2Ffile2.sheencity.com%2Fdata%2Fbig%2F2014-04%2F16%2F60761ec86d85133d4629849a338d0c76.jpg");
        photoGridViewAdapter = new PhotoGridViewAdapter(mContext,R.layout.adapter_photo_layout,photos);
        recycle.setLayoutManager(new GridLayoutManager(mContext,2));
        recycle.setAdapter(photoGridViewAdapter);

        photoGridViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                ImagePagerActivity.startImagePagerActivity(mContext, photos, position, imageSize);

            }
        });

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
