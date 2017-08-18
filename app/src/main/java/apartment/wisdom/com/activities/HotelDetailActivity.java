package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import apartment.wisdom.com.R;
import apartment.wisdom.com.widgets.views.ColoredSnackbar;
import apartment.wisdom.com.widgets.views.MyListView;
import apartment.wisdom.com.widgets.views.PriceShowView;
import apartment.wisdom.com.widgets.views.ScoreView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

/**
 * Author: 邓言诚  Create at : 17/8/13  20:13
 * Email: yanchengdeng@gmail.com
 * Describle: 旅馆详情
 */
public class HotelDetailActivity extends BaseActivity {

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.tv_photos_count)
    TextView tvPhotosCount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tag_group)
    TagGroup tagGroup;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_hotel_detail)
    TextView tvHotelDetail;
    @BindView(R.id.tv_hotel_call)
    TextView tvHotelCall;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.card_map_view)
    CardView cardMapView;
    @BindView(R.id.tv_type_day)
    TextView tvTypeDay;
    @BindView(R.id.tv_type_hour)
    TextView tvTypeHour;
    @BindView(R.id.tv_stay_in_date)
    TextView tvStayInDate;
    @BindView(R.id.tv_stay_in_week)
    TextView tvStayInWeek;
    @BindView(R.id.rl_stay_in)
    RelativeLayout rlStayIn;
    @BindView(R.id.tv_stay_days)
    TextView tvStayDays;
    @BindView(R.id.tv_stay_out_date)
    TextView tvStayOutDate;
    @BindView(R.id.tv_stay_out_week)
    TextView tvStayOutWeek;
    @BindView(R.id.rl_stay_out)
    RelativeLayout rlStayOut;
    @BindView(R.id.tv_normal_price_tittle)
    PriceShowView tvNormalPriceTittle;
    @BindView(R.id.rl_normal_room_tittle)
    RelativeLayout rlNormalRoomTittle;
    @BindView(R.id.tv_normal_discount_price)
    TextView tvNormalDiscountPrice;
    @BindView(R.id.tv_normal_room_price)
    TextView tvNormalRoomPrice;
    @BindView(R.id.tv_normal_room_order)
    TextView tvNormalRoomOrder;
    @BindView(R.id.rl_normal_room_discount_price)
    RelativeLayout rlNormalRoomDiscountPrice;
    @BindView(R.id.tv_stand_price_tittle)
    PriceShowView tvStandPriceTittle;
    @BindView(R.id.rl_stand_room_tittle)
    RelativeLayout rlStandRoomTittle;
    @BindView(R.id.tv_stand_discount_price)
    TextView tvStandDiscountPrice;
    @BindView(R.id.tv_stand_room_price)
    TextView tvStandRoomPrice;
    @BindView(R.id.tv_stand_room_order)
    TextView tvStandRoomOrder;
    @BindView(R.id.rl_stand_room_discount_price)
    RelativeLayout rlStandRoomDiscountPrice;
    @BindView(R.id.tv_splendid_price_tittle)
    PriceShowView tvSplendidPriceTittle;
    @BindView(R.id.rl_splendid_room_tittle)
    RelativeLayout rlSplendidRoomTittle;
    @BindView(R.id.tv_splendid_discount_price)
    TextView tvSplendidDiscountPrice;
    @BindView(R.id.tv_splendid_room_price)
    TextView tvSplendidRoomPrice;
    @BindView(R.id.tv_splendid_room_order)
    TextView tvSplendidRoomOrder;
    @BindView(R.id.rl_splendid_room_discount_price)
    RelativeLayout rlSplendidRoomDiscountPrice;
    @BindView(R.id.ll_all_ui_list)
    LinearLayout llAllUiList;
    @BindView(R.id.list_time)
    MyListView listTime;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_score_rate)
    TextView tvScoreRate;
    @BindView(R.id.ll_score)
    LinearLayout llScore;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rb_score)
    RatingBar rbScore;
    @BindView(R.id.tv_comment_score)
    TextView tvCommentScore;
    @BindView(R.id.tv_comment_creat_time)
    TextView tvCommentCreatTime;
    @BindView(R.id.tv_hotel_type)
    TextView tvHotelType;
    @BindView(R.id.tv_comment_content)
    TextView tvCommentContent;
    @BindView(R.id.iv_change_lines)
    ImageView ivChangeLines;
    @BindView(R.id.tv_show_reply)
    TextView tvShowReply;
    @BindView(R.id.tv_reply_content)
    TextView tvReplyContent;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;
    @BindView(R.id.tv_look_for_all_comments)
    TextView tvLookForAllComments;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    boolean[] isShowChile = new boolean[]{false, false, false};
    @BindView(R.id.tv_score_ws)
    ScoreView tvScoreWs;
    @BindView(R.id.tv_score_hj)
    ScoreView tvScoreHj;
    @BindView(R.id.tv_score_fw)
    ScoreView tvScoreFw;
    @BindView(R.id.tv_score_xjb)
    ScoreView tvScoreXjb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        ButterKnife.bind(this);
        Snackbar snackbar = Snackbar.make(imageView, "已有12人浏览", Toast.LENGTH_SHORT);
        ColoredSnackbar.alert(snackbar).show();
        setCollapsingToolbarLayoutTitle(getString(R.string.hotel_detail));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // to change the title's font size of toolbar layout
    private void setCollapsingToolbarLayoutTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    boolean isLike;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_like) {
            if (isLike) {
                item.setIcon(R.mipmap.nav_detail_like_btn_1);
                isLike = false;
            } else {
                item.setIcon(R.mipmap.nav_detail_like_btn_2);
                isLike = true;
            }
        } else if (id == R.id.action_share) {
            new ShareAction(HotelDetailActivity.this)
                    .withText("hello")
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener)
                    .open();
        }
        return true;
    }

    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调，可以用来处理等待框，或相关的文字提示

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort(platform.toString());
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

            ToastUtils.showShort(platform.toString() + t.getLocalizedMessage() + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.image_view, R.id.tv_hotel_detail, R.id.tv_hotel_call, R.id.iv_map, R.id.tv_address, R.id.tv_type_day, R.id.tv_type_hour, R.id.rl_stay_in, R.id.rl_stay_out, R.id.rl_normal_room_tittle, R.id.tv_normal_room_order, R.id.rl_stand_room_tittle, R.id.tv_stand_room_order, R.id.rl_splendid_room_tittle, R.id.tv_splendid_room_order, R.id.tv_look_for_all_comments})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_view:
                openActivity(ShowHotelPicActivity.class);
                break;
            case R.id.tv_hotel_detail:
                openActivity(HotelIntroduceActivity.class);
                break;
            case R.id.tv_hotel_call:
                setTvHotelCall("021-020302002");
                break;
            case R.id.iv_map:
            case R.id.tv_address:
                openActivity(MapActivity.class);
                break;
            case R.id.tv_type_day:
                break;
            case R.id.tv_type_hour:
                break;
            case R.id.rl_stay_in:
                break;
            case R.id.rl_stay_out:
                break;
            case R.id.rl_normal_room_tittle:
                isShowChile[0] = !isShowChile[0];
                tvNormalPriceTittle.setShowStatus(isShowChile[0]);
                rlNormalRoomDiscountPrice.setVisibility(isShowChile[0]?View.VISIBLE: View.GONE);
                break;
            case R.id.rl_stand_room_tittle:
                isShowChile[1] = !isShowChile[1];
                tvStandPriceTittle.setShowStatus(isShowChile[1]);
                rlStandRoomDiscountPrice.setVisibility(isShowChile[1]?View.VISIBLE: View.GONE);
                break;
            case R.id.rl_splendid_room_tittle:
                isShowChile[2] = !isShowChile[2];
                tvSplendidPriceTittle.setShowStatus(isShowChile[2]);
                rlSplendidRoomDiscountPrice.setVisibility(isShowChile[2]?View.VISIBLE: View.GONE);
                break;
            case R.id.tv_normal_room_order:
                openActivity(PreOrderActivity.class);
                break;
            case R.id.tv_stand_room_order:
                openActivity(PreOrderActivity.class);
                break;
            case R.id.tv_splendid_room_order:
                openActivity(PreOrderActivity.class);
                break;
            case R.id.tv_look_for_all_comments:
                openActivity(CommendListActivity.class);
                break;
        }
    }


    private void setTvHotelCall(final String phone) {
        final String[] stringItems = {phone};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, ivMap);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(IntentUtils.getDialIntent(phone));
                dialog.dismiss();
            }
        });
    }


}
