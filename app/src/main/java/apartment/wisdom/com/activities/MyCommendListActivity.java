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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.MyCommendListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.MyCommendList;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的点评
public class MyCommendListActivity extends BaseActivity {

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
    private MyCommendListAdapter myCommendListAdapter;
    private List<MyCommendList.CommendInfo> storeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commend_list);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.my_commend));
        recycle.setLayoutManager(new LinearLayoutManager(this));
        noDateView = getLayoutInflater().inflate(R.layout.layour_listview_empty, (ViewGroup) recycle.getParent(), false);
        ((TextView) noDateView.findViewById(R.id.tv_empty_tips)).setText("暂无点评");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
        recycle.addItemDecoration(dividerItemDecoration);
        myCommendListAdapter = new MyCommendListAdapter(mContext, R.layout.adapter_my_commend_layout, storeList);
        recycle.setAdapter(myCommendListAdapter);
        getMyCommend();

        myCommendListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle commend = new Bundle();
                commend.putString(Constants.PASS_STRING, myCommendListAdapter.getData().get(position).storeId);
                openActivity(CommendListActivity.class, commend);
            }
        });

    }

    private void getMyCommend() {
        mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<MyCommendList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "myReview"))
                .execute(new NewsCallback<AAResponse<MyCommendList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<MyCommendList>> response) {
                        LogUtils.w("dyc", response.body());
                        mSVProgressHUD.dismiss();
                        MyCommendList myCommendList = response.body().data;
                        if (myCommendList != null && !myCommendList.storeList.isEmpty()) {
                            myCommendListAdapter.setNewData(myCommendList.storeList);
                        } else {
                            myCommendListAdapter.setNewData(null);
                            myCommendListAdapter.setEmptyView(noDateView);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<MyCommendList>> response) {
                        ToastUtils.showShort(response.getException().getMessage());
                        mSVProgressHUD.dismiss();
                        myCommendListAdapter.setNewData(null);
                        myCommendListAdapter.setEmptyView(noDateView);
                    }
                });

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
