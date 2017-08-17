package apartment.wisdom.com.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import apartment.wisdom.com.R;
import apartment.wisdom.com.activities.AboutActivity;
import apartment.wisdom.com.activities.BalanceActivity;
import apartment.wisdom.com.activities.CommendListActivity;
import apartment.wisdom.com.activities.CommonInfoActivity;
import apartment.wisdom.com.activities.HotelOrderListActivity;
import apartment.wisdom.com.activities.MeInfoActivity;
import apartment.wisdom.com.activities.MyIntegralActivity;
import apartment.wisdom.com.activities.TicketListActivity;
import apartment.wisdom.com.commons.Constants;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


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
        return view;
    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(context).inflate(R.layout.layout_fragment_me_header, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.layout_fragment_me_zoom, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_fragment_me_content, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        ivHeader = (ImageView) scrollView.getPullRootView().findViewById(R.id.image_person);
        tvPhone = (TextView) scrollView.getPullRootView().findViewById(R.id.mine_phone);
        Glide.with(this).load(Constants.JZ_PIC).bitmapTransform(new CropCircleTransformation(context)).crossFade(1000).into(ivHeader);
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
                startActivity(new Intent(context, MeInfoActivity.class));
            }
        });


        //余额
        scrollView.getPullRootView().findViewById(R.id.lay_my_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, BalanceActivity.class));
            }
        });

        //优惠劵
        scrollView.getPullRootView().findViewById(R.id.lay_my_privilege).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TicketListActivity.class));
            }
        });

        //积分
        scrollView.getPullRootView().findViewById(R.id.lay_my_integral).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MyIntegralActivity.class));
            }
        });

        //酒店订单
        scrollView.getPullRootView().findViewById(R.id.mine_hotel_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,HotelOrderListActivity.class));
            }
        });

        //常用信息
        scrollView.getPullRootView().findViewById(R.id.common_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,CommonInfoActivity.class));
            }
        });
        //我的点评
        scrollView.getPullRootView().findViewById(R.id.mine_evaluate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, CommendListActivity.class));
            }
        });

        //客服电话
        scrollView.getPullRootView().findViewById(R.id.mine_tel_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTvHotelCall("010-234342323");
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

}