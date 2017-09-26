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
import apartment.wisdom.com.commons.Constants;
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
    public
    TextView tvScore;
    @BindView(R.id.tv_score_rate)
    public
    TextView tvScoreRate;
    @BindView(R.id.tv_score_ws)
    public
    ScoreView tvScoreWs;
    @BindView(R.id.tv_score_hj)
    public
    ScoreView tvScoreHj;
    @BindView(R.id.tv_score_fw)
    public
    ScoreView tvScoreFw;
    @BindView(R.id.tv_score_xjb)
    public
    ScoreView tvScoreXjb;
    @BindView(R.id.ll_score)
    LinearLayout llScore;

    private String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commend_list);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.hotel_comment));
        storeId = getIntent().getStringExtra(Constants.PASS_STRING);
        Bundle all = new Bundle();
        //0 全部  1  差评 2 中评 3 好评
        all.putString(Constants.PASS_STRING,storeId);
        all.putString(Constants.SELECT_CARD_TYPE,"0");


        Bundle  bad= new Bundle();
        //0 全部  1  差评 2 中评 3 好评
        bad.putString(Constants.PASS_STRING,storeId);
        bad.putString(Constants.SELECT_CARD_TYPE,"1");

        Bundle  middle= new Bundle();
        //0 全部  1  差评 2 中评 3 好评
        middle.putString(Constants.PASS_STRING,storeId);
        middle.putString(Constants.SELECT_CARD_TYPE,"2");

        Bundle  good= new Bundle();
        //0 全部  1  差评 2 中评 3 好评
        good.putString(Constants.PASS_STRING,storeId);
        good.putString(Constants.SELECT_CARD_TYPE,"3");


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(getString(R.string.all_commend), CommendFragment.class,all)
                .add(getString(R.string.good_commend), CommendFragment.class,good)
                .add(getString(R.string.middle_commend), CommendFragment.class,middle)
                .add(getString(R.string.bad_commend), CommendFragment.class,bad)
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
