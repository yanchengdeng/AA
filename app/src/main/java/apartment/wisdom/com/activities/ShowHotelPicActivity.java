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
import apartment.wisdom.com.beans.HotelListInfo;
import apartment.wisdom.com.commons.Constants;
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
    private  ArrayList<HotelListInfo.HotelListItem.StoreImageInfo> storeImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hotel_pic);
        ButterKnife.bind(this);
        storeImageList = (ArrayList<HotelListInfo.HotelListItem.StoreImageInfo>) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
        tvTittle.setText("公寓照片(1张)");
        if (storeImageList!=null && storeImageList.size()>0){
            tvTittle.setText(String.format("公寓照片(%d张)",new Object[]{storeImageList.size()}));
            for (HotelListInfo.HotelListItem.StoreImageInfo item:storeImageList){
                photos.add(item.storeImage);
            }
            photoGridViewAdapter = new PhotoGridViewAdapter(mContext,R.layout.adapter_photo_layout,photos);
            recycle.setLayoutManager(new GridLayoutManager(mContext,2));
            recycle.setAdapter(photoGridViewAdapter);
        }else{

        }


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
