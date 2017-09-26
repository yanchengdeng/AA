package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.fragments.HotelOrdersFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/15  20:14
 * Email: yanchengdeng@gmail.com
 * Describle: 酒店订单列表
 */
public class HotelOrderListActivity extends BaseActivity {

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
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_order_list);
        ButterKnife.bind(this);

        tvTittle.setText(getString(R.string.hotel_orders));
        Bundle all = new Bundle();
        all.putString(Constants.PASS_STRING,"0");

        Bundle waitAprise = new Bundle();
        waitAprise.putString(Constants.PASS_STRING,"1");
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(getString(R.string.all_orders), HotelOrdersFragment.class,all)
                .add(getString(R.string.wait_comments), HotelOrdersFragment.class,waitAprise)
                .create());

        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);
        viewpager.setOffscreenPageLimit(2);

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
