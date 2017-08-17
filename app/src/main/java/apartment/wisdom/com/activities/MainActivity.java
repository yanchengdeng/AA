package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import apartment.wisdom.com.R;
import apartment.wisdom.com.fragments.ActivityFragment;
import apartment.wisdom.com.fragments.AssistantFragment;
import apartment.wisdom.com.fragments.HomeFragment;
import apartment.wisdom.com.fragments.MeFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
*
* Author: 邓言诚  Create at : 17/8/8  11:22
* Email: yanchengdeng@gmail.com
* Describle: 首页
*/
public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
   public ViewPager viewpager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initFragments();
    }



    private void initFragments() {

        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of(getString(R.string.tab_home), HomeFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.tab_activity), ActivityFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.tab_assistant), AssistantFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.tab_me), MeFragment.class));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);


        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        viewpagertab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View tabView = inflater.inflate(R.layout.custom_tab_icon, container,
                        false);
                ImageView imageView = (ImageView) tabView.findViewById(R.id.custom_tab_icon);
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.tab_home);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.tab_activity);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.tab_assistant);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.tab_me);
                        break;
                }
                return tabView;
            }
        });
        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return doubleClickExist();
        }
        return super.onKeyDown(keyCode, event);
    }

    private long mExitTime;

    /****
     * 连续两次点击退出
     */
    private boolean doubleClickExist() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort(R.string.double_click_exit);
            mExitTime = System.currentTimeMillis();
            return true;
        } else {
            finish();
        }
        return false;
    }
}
