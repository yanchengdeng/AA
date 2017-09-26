package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import apartment.wisdom.com.adapters.PeopleListAdapter;
import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.beans.CustomPeopleList;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.utils.NewsCallback;
import apartment.wisdom.com.utils.ParamsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/15  20:16
 * Email: yanchengdeng@gmail.com
 * Describle: 常用信息
 */
public class CommonInfoActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.tv_add_people)
    TextView tvAddPeople;

    private static final int REQUEST_ADD_PEAPLE = 0x011;
    private static final int REQUEST_UPDATE_PEOPLE = 0x012;

    private PeopleListAdapter peopleListAdapter;
    private List<CustomPeopleList.CustomPeopleItem> peopleItemInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_info);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.common_info));
        peopleListAdapter = new PeopleListAdapter(R.layout.adapter_people_layout, peopleItemInfos);

        recycle.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_line_diver));
        recycle.addItemDecoration(itemDecoration);
        recycle.setAdapter(peopleListAdapter);


        peopleListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PASS_OBJECT, peopleItemInfos.get(position));
                openActivity(AdPeopleActivity.class, bundle, REQUEST_UPDATE_PEOPLE);
            }
        });

        getAllCustomeInfo();
    }

    //常用信息
    private void getAllCustomeInfo() {

        mSVProgressHUD.showWithStatus(getString(R.string.loading));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", LoginUtils.getUserInfo().cardNo);
        OkGo.<AAResponse<CustomPeopleList>>post(Constants.Net.URL)//
                .cacheMode(CacheMode.NO_CACHE)
                .params("data", ParamsUtils.getParams(data, "queryCommonInfo"))
                .execute(new NewsCallback<AAResponse<CustomPeopleList>>() {
                    @Override
                    public void onSuccess(Response<AAResponse<CustomPeopleList>> response) {
                        LogUtils.w("dyc", response.body());
                        mSVProgressHUD.dismiss();
                        CustomPeopleList customPeopleList = response.body().data;
                        if (customPeopleList != null && !customPeopleList.contacList.isEmpty()) {
                            peopleItemInfos = customPeopleList.contacList;
                            peopleListAdapter.setNewData(peopleItemInfos);
                        }else{
                            peopleListAdapter.setNewData(null);
                        }
                    }

                    @Override
                    public void onError(Response<AAResponse<CustomPeopleList>> response) {
                        //Failed to connect to /116.62.124.66:80
                        ToastUtils.showShort(response.getException().getMessage());
                        mSVProgressHUD.dismiss();
                    }
                });
    }

    //com.lzy.okgo.model.Response

    @OnClick({R.id.back, R.id.tv_add_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_add_people:
                openActivity(AdPeopleActivity.class, REQUEST_ADD_PEAPLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_PEAPLE) {
            if (resultCode == AdPeopleActivity.RESULT_ADD_SUCCESS) {
                getAllCustomeInfo();
            }
        } else if (requestCode == REQUEST_UPDATE_PEOPLE) {
            if (resultCode == AdPeopleActivity.RESULT_UPDATE_SUCCESS) {
                getAllCustomeInfo();
            }
        }
    }
}
