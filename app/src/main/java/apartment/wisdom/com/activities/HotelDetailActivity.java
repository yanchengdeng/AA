package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tubb.calendarselector.library.FullDay;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.DayTypeRoomAdapterNew;
import apartment.wisdom.com.adapters.HourTypeRoomAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.DayGroupHotel;
import apartment.wisdom.com.beans.HotelListInfo;
import apartment.wisdom.com.beans.RoomListInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DayHourRoomType;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.utils.CalendarUtils;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import apartment.wisdom.com.widgets.views.MyListView;
import apartment.wisdom.com.widgets.views.ScoreView;
import apartment.wisdom.com.widgets.views.SelectRoomTypeView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

import static apartment.wisdom.com.activities.CalendarChooseActivity.SUCCESS_SELECT_TIME;

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
    @BindView(R.id.select_room_type)
    SelectRoomTypeView selectRoomType;
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
    @BindView(R.id.tv_break_time)
    TextView tvBreakTime;
    @BindView(R.id.ll_hour_stand_tips)
    LinearLayout llHourStandTips;
    @BindView(R.id.list_day)
    MyListView listDay;
    @BindView(R.id.list_time)
    MyListView listTime;
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
    @BindView(R.id.ll_commend_item)
    LinearLayout llCommendItem;
    @BindView(R.id.tv_empty_tips)
    TextView tvEmptyTips;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    private FullDay stant_in, stant_out;
    private int diffdays = 1;
    private static final int REQUEST_SELECT_DATE = 0x1112;
    private String area;
    private String selectType;
    private HotelListInfo.HotelListItem hotelListItem;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        selectType = getIntent().getStringExtra(Constants.PASS_SELECT_HOTLE_TYPE);
        stant_in = getIntent().getParcelableExtra(Constants.PASS_STAND_IN);
        stant_out = getIntent().getParcelableExtra(Constants.PASS_STAND_OUT);
        diffdays = getIntent().getIntExtra(Constants.PASS_DISTANCE_DAYS, 1);
        area = getIntent().getStringExtra(Constants.PASS_STRING);
        hotelListItem = (HotelListInfo.HotelListItem) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
//        Snackbar snackbar = Snackbar.make(imageView, "已有1人浏览", Toast.LENGTH_SHORT);
//        ColoredSnackbar.alert(snackbar).show();
        setCollapsingToolbarLayoutTitle(getString(R.string.hotel_detail));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectRoomType.rlDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectType = DayHourRoomType.DAY_HOUR_ROOM_TYPE_DAY.getType();
                initDayTypeUI();
            }
        });

        selectRoomType.rlHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectType = DayHourRoomType.DAY_HOUR_ROOM_TYPE_HOUR.getType();
                initHourTypeUI();
            }
        });

        if (selectType.equals(DayHourRoomType.DAY_HOUR_ROOM_TYPE_DAY.getType())) {
            initDayTypeUI();
        } else {
            initHourTypeUI();
        }


        initData();
        getHotelInfo();
        EventBus.getDefault().register(this);
    }

    //初始化  天房间 UI
    private void initDayTypeUI() {
        selectRoomType.setSelectDay();
        listDay.setVisibility(View.VISIBLE);
        listTime.setVisibility(View.GONE);
        rlStayOut.setVisibility(View.VISIBLE);
        tvStayDays.setVisibility(View.VISIBLE);
        llHourStandTips.setVisibility(View.GONE);
    }

    private void initHourTypeUI() {
        selectRoomType.setSelectHour();
        listDay.setVisibility(View.GONE);
        listTime.setVisibility(View.VISIBLE);
        rlStayOut.setVisibility(View.GONE);
        tvStayDays.setVisibility(View.GONE);
        llHourStandTips.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof PayOrRechargeSuccess) {
            finish();
        }
    }

    RoomListInfo roomListInfo;

    private void getHotelInfo() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("storeId", hotelListItem.storeId);
        data.put("checkInRoomType", selectType);
        data.put("checkInTime", CalendarUtils.getInstant().getDateForamte(stant_in));
        data.put("checkOutTime", CalendarUtils.getInstant().getDateForamte(stant_out));
        OkGo.<AAResponse<RoomListInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "selecStore"))
                .execute(new NewsCallback<AAResponse<RoomListInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<RoomListInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        roomListInfo = response.body().data;
                        if (roomListInfo != null) {
                            initRoomListInfo(roomListInfo);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<RoomListInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }

    private void initRoomListInfo(RoomListInfo roomListInfo) {
        if (!TextUtils.isEmpty(roomListInfo.bespeakTime)) {
            tvBreakTime.setText(roomListInfo.bespeakTime);
        }

        if (!TextUtils.isEmpty(roomListInfo.storeScore)) {
            tvScore.setText(roomListInfo.storeScore);
        }

        if (!TextUtils.isEmpty(roomListInfo.storeRoomHealthScore)) {
            tvScoreWs.setData("卫生", roomListInfo.storeRoomHealthScore);
        }
        if (!TextUtils.isEmpty(roomListInfo.storeEnvironmentScore)) {
            tvScoreHj.setData("环境", roomListInfo.storeEnvironmentScore);
        }

        if (!TextUtils.isEmpty(roomListInfo.storeHotelScore)) {
            tvScoreFw.setData("服务", roomListInfo.storeHotelScore);
        }
        if (!TextUtils.isEmpty(roomListInfo.storeDeviceScore)) {
            tvScoreXjb.setData("性价比", roomListInfo.storeDeviceScore);
        }

        if (roomListInfo.customerList != null && !roomListInfo.customerList.isEmpty()) {
            tvUserName.setText(roomListInfo.customerList.get(0).username);
            rbScore.setRating(Float.parseFloat(roomListInfo.customerList.get(0).customerScore));
            tvCommentScore.setText(roomListInfo.customerList.get(0).customerScore);
            tvCommentCreatTime.setText(roomListInfo.customerList.get(0).evaluateDate);
            if (!TextUtils.isEmpty(roomListInfo.customerList.get(0).storeEvaluate)) {
                tvReplyContent.setText(roomListInfo.customerList.get(0).storeEvaluate);
            }
            if (!TextUtils.isEmpty(roomListInfo.customerList.get(0).customerEvaluate)) {
                tvCommentContent.setText(roomListInfo.customerList.get(0).customerEvaluate);
            }
        } else {
            llCommendItem.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);
            tvEmptyTips.setText("暂无评论");
            tvLookForAllComments.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(roomListInfo.storePercent)) {
            tvScoreRate.setText(String.format(getString(R.string.score_rate), new Object[]{roomListInfo.storePercent}));
        }

        if (!roomListInfo.hourRoomList.isEmpty()) {

            listTime.setAdapter(new HourTypeRoomAdapter(mContext, roomListInfo.hourRoomList, hotelListItem.storeId, stant_in, stant_out, diffdays, roomListInfo.bespeakTime));
        }
        if (!TextUtils.isEmpty(roomListInfo.bespeakTime)) {
            tvBreakTime.setText(roomListInfo.bespeakTime);
        }

        if (!roomListInfo.dayRoomList.isEmpty()) {

            List<DayGroupHotel> dayGroupHotels = new ArrayList<>();
            for (RoomListInfo.DayRoom item : roomListInfo.dayRoomList) {
                DayGroupHotel groupHotel = new DayGroupHotel();
                groupHotel.name = item.roomTypeName;
                groupHotel.price = item.shopPrice;
                groupHotel.discountPrice = item.roomPrice;
                groupHotel.roomNum = item.roomNum;
                groupHotel.roomTypeId = item.roomTypeId;
                groupHotel.storeId = hotelListItem.storeId;
                groupHotel.hotelName = item.roomTypeName;
                groupHotel.standIn = stant_in;
                groupHotel.standOut = stant_out;
                groupHotel.differDays = diffdays;
                groupHotel.selectType = DayHourRoomType.DAY_HOUR_ROOM_TYPE_DAY.getType();
                groupHotel.roomRisePrice = item.roomRisePrice;
                groupHotel.roomDeposit = item.roomDeposit;
                groupHotel.bespeakDays = item.bespeakDays;
                List<DayGroupHotel.DayGroupHotelItem> childHotels = new ArrayList<>();
                DayGroupHotel.DayGroupHotelItem dayGroupHotelItem = new DayGroupHotel.DayGroupHotelItem();
                dayGroupHotelItem.discountPrice = item.roomPrice;
                dayGroupHotelItem.roomPirce = item.shopPrice;
                dayGroupHotelItem.roomNum = item.roomNum;
                dayGroupHotelItem.liveNum = item.liveNum;
                dayGroupHotelItem.bedNum = item.bedNum;
                dayGroupHotelItem.roomTypeId = item.roomTypeId;
                dayGroupHotelItem.storeId = hotelListItem.storeId;
                dayGroupHotelItem.hotelName = item.roomTypeName;
                dayGroupHotelItem.standIn = stant_in;
                dayGroupHotelItem.standOut = stant_out;
                dayGroupHotelItem.differDays = diffdays;
                dayGroupHotelItem.selectType = DayHourRoomType.DAY_HOUR_ROOM_TYPE_DAY.getType();
                dayGroupHotelItem.roomRisePrice = item.roomRisePrice;
                dayGroupHotelItem.roomDeposit = item.roomDeposit;
                childHotels.add(dayGroupHotelItem);
                groupHotel.items = childHotels;
                dayGroupHotels.add(groupHotel);

            }

//            listDay.setGroupIndicator(null);
            listDay.setAdapter(new DayTypeRoomAdapterNew(mContext, dayGroupHotels));
        }
    }


    private void initData() {
        tvStayDays.setText(String.format(getString(R.string.total_night), diffdays));
        tvStayInDate.setText(String.format(getString(R.string.month_day), new Object[]{stant_in.getMonth(), stant_in.getDay()}));
        tvStayOutDate.setText(String.format(getString(R.string.month_day), new Object[]{stant_out.getMonth(), stant_out.getDay()}));
        tvStayInWeek.setText(String.valueOf(stant_in.getDay()));
        tvStayOutWeek.setText(String.valueOf(stant_out.getDay()));
        tvStayInWeek.setText(CalendarUtils.getInstant().getWeekNameByDate(stant_in));
        tvStayOutWeek.setText(CalendarUtils.getInstant().getWeekNameByDate(stant_out));


        if (!TextUtils.isEmpty(hotelListItem.coordinate) && hotelListItem.coordinate.contains(",")){
            String[] mapCoordinate = hotelListItem.coordinate.split(",");
            String mapurl = "http://api.map.baidu.com/staticimage/v2?ak="+"PvyN3Dn6UR8QIGrjPLlZBtHAYPiR5YFS"+"&mcode=70:1A:8B:0B:81:E3:BE:66:EC:12:A1:1E:00:1D:4E:90:C0:0A:84:7D;apartment.wisdom.com&center="+mapCoordinate[1]+","+mapCoordinate[0]+"&width=300&height=200&zoom=18";
            Glide.with(mContext).load(mapurl).into(ivMap);
        }


        if (!TextUtils.isEmpty(hotelListItem.address)) {
            tvAddress.setText(hotelListItem.address);
        }

        if (!TextUtils.isEmpty(hotelListItem.storeName)) {
            tvTittle.setText(hotelListItem.storeName);
        }
        if (!TextUtils.isEmpty(hotelListItem.storeRoomMinPrice)) {
            tvPrice.setText(hotelListItem.storeRoomMinPrice);
        }
        if (!hotelListItem.storeImageList.isEmpty()) {
            tvPhotosCount.setText(hotelListItem.storeImageList.size() + "张");
        }

        if (!hotelListItem.storeImageList.isEmpty()) {
            Glide.with(mContext).load(hotelListItem.storeImageList.get(0).storeImage).into(imageView);
        }


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
        } /*else if (id == R.id.action_share) {
            new ShareAction(HotelDetailActivity.this)
                    .withText("hello")
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener)
                    .open();
        }*/
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
        if (requestCode == REQUEST_SELECT_DATE && resultCode == SUCCESS_SELECT_TIME) {
            if (data != null) {
                stant_in = data.getParcelableExtra(Constants.PASS_STAND_IN);
                stant_out = data.getParcelableExtra(Constants.PASS_STAND_OUT);
                diffdays = data.getExtras().getInt(Constants.STAND_IN_OUT_DISTANCE, 1);
                initData();
                getHotelInfo();
            }
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R.id.image_view, R.id.tv_hotel_detail, R.id.tv_hotel_call, R.id.iv_map, R.id.tv_address, R.id.rl_stay_in, R.id.rl_stay_out, R.id.tv_look_for_all_comments})
    public void onViewClicked(View view) {
        if (hotelListItem == null) {
            ToastUtils.showShort(R.string.no_net_data);
            return;
        }
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.image_view:
                bundle.putSerializable(Constants.PASS_OBJECT, (Serializable) hotelListItem.storeImageList);
                openActivity(ShowHotelPicActivity.class, bundle);
                break;
            case R.id.tv_hotel_detail:
                bundle.putSerializable(Constants.PASS_OBJECT, hotelListItem);
                openActivity(HotelIntroduceActivity.class, bundle);
                break;
            case R.id.tv_hotel_call:
                setTvHotelCall(hotelListItem.phone);
                break;
            case R.id.iv_map:
            case R.id.tv_address:
                bundle.putString(Constants.PASS_STRING, hotelListItem.coordinate);
                bundle.putString(Constants.PASS_ADDRESS, hotelListItem.address);
                openActivity(MapActivity.class, bundle);
                break;
            case R.id.rl_stay_in:
            case R.id.rl_stay_out:
                Intent intent = new Intent(mContext, CalendarChooseActivity.class);
                Bundle bundledate = new Bundle();
                bundledate.putParcelable(Constants.PASS_STAND_IN, stant_in);
                bundledate.putParcelable(Constants.PASS_STAND_OUT, stant_out);
                bundledate.putBoolean(Constants.PASS_SELECT_HOTLE_TYPE, selectRoomType.getSelectType().equals("0") ? false : true);
                intent.putExtras(bundledate);
                startActivityForResult(intent, REQUEST_SELECT_DATE);
                break;
            case R.id.tv_look_for_all_comments:
                if (roomListInfo == null) {
                    return;
                }
                if (LoginUtils.getLoginStatus()) {
                    Bundle commend = new Bundle();
                    commend.putString(Constants.PASS_STRING, hotelListItem.storeId);
                    openActivity(CommendListActivity.class, commend);
                } else {
                    openActivity(LoginActivity.class);
                }
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
