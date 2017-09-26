package apartment.wisdom.com.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.AboutActivity;
import apartment.wisdom.com.activities.BaseActivity;
import apartment.wisdom.com.activities.CommonInfoActivity;
import apartment.wisdom.com.activities.HotelOrderListActivity;
import apartment.wisdom.com.activities.LoginActivity;
import apartment.wisdom.com.activities.MeInfoActivity;
import apartment.wisdom.com.activities.MyCommendListActivity;
import apartment.wisdom.com.activities.TicketListActivity;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginOutSuccessEvent;
import apartment.wisdom.com.events.LoginSuccessEvent;
import apartment.wisdom.com.events.PayOrRechargeSuccess;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static apartment.wisdom.com.R.id.mine_phone;


/**
 * Author: 邓言诚  Create at : 17/8/7  16:53
 * Email: yanchengdeng@gmail.com
 * Describle: 我的
 */
public class MeFragment extends Fragment {
    private Context context;

    private PullToZoomScrollViewEx scrollView;

    private ImageView ivHeader, ivLevel;
    private TextView tvPhone, tvMeber;
    private TextView tvBalence, tvTicket, tvIntegral;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        scrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.scrollView);
        loadViewForCode();
        addLisener();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
        EventBus.getDefault().register(this);
        updateUserInfo();
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof LoginSuccessEvent) {
            updateUserInfo();
        } else if (event instanceof LoginOutSuccessEvent) {
            ivHeader.setImageResource(R.mipmap.mine_head);
            tvPhone.setText(getString(R.string.no_login));
            ivLevel.setVisibility(View.INVISIBLE);
            tvMeber.setVisibility(View.INVISIBLE);
        }else if (event instanceof PayOrRechargeSuccess){
            getLoginInfo();
        }
    }

    //获取登陆信息
    private void getLoginInfo() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"getLoginInfo"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        LogUtils.w("dyc",response.body());
                        UserInfo userInfo = response.body().data;
                        if (userInfo==null){
                            return;
                        }
                        SPUtils.getInstance().put(Constants.USER_INFO,new Gson().toJson(userInfo));
                        LoginUtils.setLoginStatus(true);
                        updateUserInfo();
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });


    }

    //更新用户信息
    private void updateUserInfo() {
        UserInfo userInfo = LoginUtils.getUserInfo();
        if (!TextUtils.isEmpty(userInfo.mobilePhone)) {
            tvPhone.setText(userInfo.mobilePhone);
        } else {
            if (!TextUtils.isEmpty(userInfo.cardNo)) {
                tvPhone.setText(userInfo.cardNo);
            }
        }

        if (!TextUtils.isEmpty(userInfo.cardLevel)) {
            tvMeber.setText(LoginUtils.getCardLeveName(userInfo.cardLevel));
        }

        if (!TextUtils.isEmpty(userInfo.cardMoney)) {
            tvBalence.setText(userInfo.cardMoney);
        }

        if (!TextUtils.isEmpty(userInfo.couponNum)) {
            tvTicket.setText(userInfo.couponNum);
        }
        if (!TextUtils.isEmpty(userInfo.cardIntegral)) {
            tvIntegral.setText(userInfo.cardIntegral);
        }
        ivLevel.setVisibility(View.VISIBLE);
        tvMeber.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(userInfo.headImage)) {
            Glide.with(this).load(userInfo.headImage).bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(ivHeader);
        }
    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(context).inflate(R.layout.layout_fragment_me_header, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.layout_fragment_me_zoom, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_fragment_me_content, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        ivHeader = (ImageView) scrollView.getPullRootView().findViewById(R.id.image_person);
        tvPhone = (TextView) scrollView.getPullRootView().findViewById(mine_phone);
        ivLevel = (ImageView) scrollView.getPullRootView().findViewById(R.id.image_vip_level);
        tvMeber = (TextView) scrollView.getPullRootView().findViewById(R.id.mine_number);
        tvBalence = (TextView) scrollView.getPullRootView().findViewById(R.id.tv_person_balance);
        tvTicket = (TextView) scrollView.getPullRootView().findViewById(R.id.tv_person_privilege);
        tvIntegral = (TextView) scrollView.getPullRootView().findViewById(R.id.tv_person_integral);
    }

    private void addLisener() {

        //个人信息
        scrollView.getPullRootView().findViewById(R.id.people_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
                    startActivity(new Intent(context, MeInfoActivity.class));
                } else {
                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }
            }
        });


        //余额
        scrollView.getPullRootView().findViewById(R.id.lay_my_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (LoginUtils.getLoginStatus()) {
//                    startActivity(new Intent(context, BalanceActivity.class));
                } else {
//                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }
            }
        });

        //优惠劵
        scrollView.getPullRootView().findViewById(R.id.lay_my_privilege).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
                    startActivity(new Intent(context, TicketListActivity.class));
                } else {
                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }
            }
        });

        //积分
        scrollView.getPullRootView().findViewById(R.id.lay_my_integral).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
//                    startActivity(new Intent(context, MyIntegralActivity.class));
                } else {
//                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }
            }
        });

        //酒店订单
        scrollView.getPullRootView().findViewById(R.id.mine_hotel_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
                    startActivity(new Intent(context, HotelOrderListActivity.class));
                } else {
                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }
            }
        });

        //常用信息
        scrollView.getPullRootView().findViewById(R.id.common_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
                    startActivity(new Intent(context, CommonInfoActivity.class));
                } else {
                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }

            }
        });
        //我的点评
        scrollView.getPullRootView().findViewById(R.id.mine_evaluate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.getLoginStatus()) {
                    startActivity(new Intent(context, MyCommendListActivity.class));
                } else {
                    ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
                }
            }
        });

        //客服电话
        scrollView.getPullRootView().findViewById(R.id.mine_tel_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTvHotelCall(getString(R.string.custome_phone));
            }
        });


        //关于
        scrollView.getPullRootView().findViewById(R.id.tv_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AboutActivity.class));
            }
        });
    }

    private void setTvHotelCall(final String phone) {
        final String[] stringItems = {phone};
        final ActionSheetDialog dialog = new ActionSheetDialog(context, stringItems, ivHeader);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(IntentUtils.getDialIntent(phone));
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
