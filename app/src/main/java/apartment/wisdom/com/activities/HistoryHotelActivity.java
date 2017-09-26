package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HistoryListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.HotelOrderItem;
import apartment.wisdom.com.beans.HotelOrderList;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.DoCommendAlreadyEvnet;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/17  15:10
 * Email: yanchengdeng@gmail.com
 * Describle:  历史记录
 */
public class HistoryHotelActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{

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

    private View noDateView;
    private List<HotelOrderItem>  assistantItemInfos = new ArrayList<>();
    private HistoryListAdapter historyListAdapter;
    private  int pageNmu = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.trip_history));
        historyListAdapter = new HistoryListAdapter(R.layout.item_trip_history_card, assistantItemInfos);
        noDateView = getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView)noDateView.findViewById(R.id.tv_empty_tips)).setText("您没有历史行程哦~");
        recycle.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_transe));
        recycle.addItemDecoration(dividerItemDecoration);
        recycle.setAdapter(historyListAdapter);

        historyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PASS_STRING, historyListAdapter.getData().get(position).orderNo);
               openActivity(HotelOrderDetailActivity.class, bundle);
            }
        });
        historyListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.btn_trip_history_comment){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.PASS_OBJECT,historyListAdapter.getData().get(position));
                    openActivity(DoCommendActivity.class,bundle);

                }else if(view.getId()==R.id.btn_trip_history_delete){
                    doDelOrder(position);
                }
            }
        });
        EventBus.getDefault().register(this);

        getHistoryList();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DoCommendAlreadyEvnet event) {
        int positonCommend = 0;
        for (int i = 0;i<historyListAdapter.getData().size();i++){
            if (historyListAdapter.getData().get(i).orderNo.equals(event.getOrderNo())){
                positonCommend = i;
            }
        }
        historyListAdapter.getData().get(positonCommend).evaluateStatus="1";
        historyListAdapter.notifyItemChanged(positonCommend);

    }

    //删除订单
    private void doDelOrder(final int canclePostiont) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderNo", historyListAdapter.getData().get(canclePostiont).orderNo);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "delHistoryTrip"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        historyListAdapter.notifyItemRemoved(canclePostiont);
                        historyListAdapter.remove(canclePostiont);
                        if (historyListAdapter.getData().isEmpty()) {
                            historyListAdapter.setNewData(null);
                            historyListAdapter.setEmptyView(noDateView);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }


    private void getHistoryList() {
        if (pageNmu==1)
            mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        data.put("pageNum",pageNmu);
        data.put("pageSize",Constants.PAGE_SIZE);
        OkGo.<AAResponse<HotelOrderList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data,"historyTrip"))
                .execute(new NewsCallback<AAResponse<HotelOrderList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<HotelOrderList>> response) {
                        LogUtils.w("dyc",response.body());
                        mSVProgressHUD.dismiss();
                        HotelOrderList hotelOrderList = response.body().data;
                        if (hotelOrderList!=null){
                            if (hotelOrderList.orderList.isEmpty()){
                                if (pageNmu==1) {
                                    historyListAdapter.setNewData(null);
                                    historyListAdapter.setEmptyView(noDateView);
                                }else{
                                    historyListAdapter.loadMoreComplete();
                                    historyListAdapter.loadMoreEnd(true);
                                }
                            }else{
                                historyListAdapter.setNewData(hotelOrderList.orderList);
                                pageNmu++;
                            }
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<HotelOrderList>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        mSVProgressHUD.dismiss();
                    }
                });

}

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMoreRequested() {
        getHistoryList();
    }
}
