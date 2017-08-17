package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import apartment.wisdom.com.R;
import apartment.wisdom.com.fragments.CommendFragment;
import apartment.wisdom.com.widgets.views.ScoreView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommendListActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_score_rate)
    TextView tvScoreRate;
    @BindView(R.id.tv_score_ws)
    ScoreView tvScoreWs;
    @BindView(R.id.tv_score_hj)
    ScoreView tvScoreHj;
    @BindView(R.id.tv_score_fw)
    ScoreView tvScoreFw;
    @BindView(R.id.tv_score_xjb)
    ScoreView tvScoreXjb;
    @BindView(R.id.ll_score)
    LinearLayout llScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commend_list);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.hotel_comment));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(getString(R.string.all_commend), CommendFragment.class)
                .add(getString(R.string.good_commend), CommendFragment.class)
                .add(getString(R.string.middle_commend), CommendFragment.class)
                .add(getString(R.string.bad_commend), CommendFragment.class)
                .create());

        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);
        viewpager.setOffscreenPageLimit(4);

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
