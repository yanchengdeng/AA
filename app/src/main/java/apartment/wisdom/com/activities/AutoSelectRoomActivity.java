package apartment.wisdom.com.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.HotelEquitmentAdapter;
import apartment.wisdom.com.adapters.SelectRoomAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.PreOrderInfo;
import apartment.wisdom.com.beans.SelfRoomListInfo;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//自助选房
public class AutoSelectRoomActivity extends BaseActivity {

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
    @BindView(R.id.recycle_condition)
    GridView gridView;
    @BindView(R.id.recycle_rooms)
    RecyclerView recycleRooms;
    @BindView(R.id.tv_cancle_select)
    TextView tvCancleSelect;
    @BindView(R.id.tv_submit_btn)
    TextView tvSubmitBtn;
    @BindView(R.id.ll_bottom_btn)
    LinearLayout llBottomBtn;
    private String storeId, selectType;
    private PreOrderInfo preOrderInfo;
    private SelectRoomAdapter selectRoomAdapter;

    private SelfRoomListInfo.SelfRoomItem selfRoomItem;
    private View noDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_select_room);
        ButterKnife.bind(this);
        storeId = getIntent().getStringExtra(Constants.PASS_STRING);
        selectType = getIntent().getStringExtra(Constants.SELECT_CARD_TYPE);
        preOrderInfo = (PreOrderInfo) getIntent().getSerializableExtra(Constants.PASS_OBJECT);
        noDateView = getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycleRooms.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("未找到匹配房间");
        tvTittle.setText("自助选房");
        getAllRoomList();
    }

    private void saveSelectRoom(SelfRoomListInfo.SelfRoomItem selfRoomItem) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("roomId", selfRoomItem.roomId);
        data.put("orderNo", preOrderInfo.orderNo);
        OkGo.<AAResponse<UserInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "saveSelectRoom"))
                .execute(new NewsCallback<AAResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<UserInfo>> response) {
                        doFinishAllOrderUI();

                    }

                    @Override
                    public void onError(Response<AAResponse<UserInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    //关闭订单相关页面 进入 到我的旅行助手
    private void doFinishAllOrderUI() {
        ToastUtils.showShort(R.string.do_over);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1500);
    }

    private void getAllRoomList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("storeId", storeId);
        data.put("orderNo", preOrderInfo.orderNo);
        data.put("checkInRoomType", selectType);
        OkGo.<AAResponse<SelfRoomListInfo>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "selfHelpSelectRoom"))
                .execute(new NewsCallback<AAResponse<SelfRoomListInfo>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<SelfRoomListInfo>> response) {
                        LogUtils.w("dyc", response.body());
                        SelfRoomListInfo selfRoomListInfo = response.body().data;
                        if (selfRoomListInfo != null ) {
                            initData(selfRoomListInfo);
                        }

                    }

                    @Override
                    public void onError(Response<AAResponse<SelfRoomListInfo>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
    }

    private void initData(final SelfRoomListInfo selfRoomListInfo) {
        String[] devicesStore;
        if (!TextUtils.isEmpty(selfRoomListInfo.keyWord)) {
            if (selfRoomListInfo.keyWord.contains(";")) {
                devicesStore = selfRoomListInfo.keyWord.split(";");
            } else {
                devicesStore = new String[]{selfRoomListInfo.keyWord};
            }
        } else {
            devicesStore = new String[]{"暂无"};
        }
        gridView.setAdapter(new HotelEquitmentAdapter(mContext, devicesStore));


        recycleRooms.setLayoutManager(new LinearLayoutManager(this));
        selectRoomAdapter = new SelectRoomAdapter(R.layout.adapter_select_room_item, selfRoomListInfo.roomList);
        recycleRooms.setAdapter(selectRoomAdapter);
        if (selfRoomListInfo.roomList!=null && selfRoomListInfo.roomList.size()>0){
            selectRoomAdapter.setNewData(selfRoomListInfo.roomList);
        }else{
            selectRoomAdapter.setNewData(null);
            selectRoomAdapter.setEmptyView(noDateView);
            llBottomBtn.setVisibility(View.GONE);
        }

        selectRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (SelfRoomListInfo.SelfRoomItem item : selfRoomListInfo.roomList) {
                    item.isSelect = false;
                }
                selfRoomListInfo.roomList.get(position).isSelect = true;
                selectRoomAdapter.notifyDataSetChanged();

                selfRoomItem = selectRoomAdapter.getItem(position);
            }
        });
    }


    @OnClick({R.id.back, R.id.tv_cancle_select, R.id.tv_submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                doFinishAllOrderUI();
                break;
            case R.id.tv_cancle_select:
                doFinishAllOrderUI();
                break;
            case R.id.tv_submit_btn:
                if (selfRoomItem != null) {
                    saveSelectRoom(selfRoomItem);
                } else {
                    ToastUtils.showShort("请选择房间号");
                }
                break;
        }
    }
}
