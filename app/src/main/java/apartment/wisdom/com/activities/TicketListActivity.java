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
import apartment.wisdom.com.enums.TicketType;
import apartment.wisdom.com.fragments.TicketListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/15  20:09
 * Email: yanchengdeng@gmail.com
 * Describle: 优惠劵
 */
public class TicketListActivity extends BaseActivity {

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
        setContentView(R.layout.activity_ticket_list);
        ButterKnife.bind(this);

        tvTittle.setText(getString(R.string.ticket));

        Bundle bundle = new Bundle();
        bundle.putString(Constants.PASS_STRING, TicketType.TICKET_TYPE_NOT_USER.getType());


        Bundle bundleUsed = new Bundle();
        bundleUsed.putString(Constants.PASS_STRING, TicketType.TICKET_TYPE_USED.getType());


        Bundle bundleOutDate = new Bundle();
        bundleOutDate.putString(Constants.PASS_STRING, TicketType.TICKET_TYPE_OUTDATE.getType());


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(getString(R.string.ticket_not_user), TicketListFragment.class,bundle)
                .add(getString(R.string.ticket_used), TicketListFragment.class,bundleUsed)
                .add(getString(R.string.ticket_outdate), TicketListFragment.class,bundleOutDate)
                .create());

        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
