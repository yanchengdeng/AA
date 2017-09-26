package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.adapters.city.CustomeNeedListItemAdapter;
import apartment.wisdom.com.beans.CustomeType;
import apartment.wisdom.com.beans.DIYSaveInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DIYType;
import apartment.wisdom.com.utils.LoginUtils;
import apartment.wisdom.com.widgets.views.MyGridView;

/**
 * Author: 邓言诚  Create at : 17/8/13  17:02
 * Email: yanchengdeng@gmail.com
 * Describle:
 */
public class CustomeNeedListAdapter extends BaseQuickAdapter<CustomeType, BaseViewHolder> {
    private Context context;

    public CustomeNeedListAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<CustomeType> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CustomeType item) {
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getName());
        if (item.getType() == DIYType.DIY_TYPE_BRAKFAST.getType()) {
            helper.getView(R.id.ll_breakfast).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_breakfast).setVisibility(View.GONE);
        }

        final TextView tvnum = helper.getView(R.id.tv_num_breakfast);
        helper.getView(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvnum.setText(Integer.parseInt(tvnum.getText().toString()) + 1 + "");
                LoginUtils.updateBreakFast(Integer.parseInt(tvnum.getText().toString()));
            }
        });

        helper.getView(R.id.iv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvnum.getText().toString().equals("1")) {
                    tvnum.setText(Integer.parseInt(tvnum.getText().toString()) - 1 + "");
                    LoginUtils.updateBreakFast(Integer.parseInt(tvnum.getText().toString()));
                }
            }
        });
        ((MyGridView) helper.getView(R.id.lv_custome)).setAdapter(new CustomeNeedListItemAdapter(context, item));
        ((MyGridView) helper.getView(R.id.lv_custome)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<CustomeType.CustomTypeItem> customTypeItems = item.getCustomTypeItems();
                for (CustomeType.CustomTypeItem item : customTypeItems) {
                    item.setSelect(false);
                }

                customTypeItems.get(i).setSelect(true);
                saveDIY(item, customTypeItems.get(i), Integer.parseInt(tvnum.getText().toString()));
                ((CustomeNeedListItemAdapter) ((MyGridView) helper.getView(R.id.lv_custome)).getAdapter()).notifyDataSetChanged();
            }
        });
    }


    //保存客户diy
    private void saveDIY(CustomeType customeType, CustomeType.CustomTypeItem customTypeItem, int count) {
        List<DIYSaveInfo> diySaveInfos = LoginUtils.getDIY();
        if (diySaveInfos.isEmpty()) {
            DIYSaveInfo diySaveInfo = new DIYSaveInfo();
            diySaveInfo.setType(customeType.getType());
            diySaveInfo.setTypeName(customeType.getName());
            diySaveInfo.setSelectName(customTypeItem.getName());
            diySaveInfo.setSelectType(customTypeItem.getId());
            diySaveInfo.setMoney(customTypeItem.getPrice());
            if (customeType.getType().equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
                diySaveInfo.setNum(count);
            }
            diySaveInfos.add(diySaveInfo);
            SPUtils.getInstance().put(Constants.SAVE_DIY_SELECT,new Gson().toJson(diySaveInfos));
        } else {

            boolean isExist = false;
            DIYSaveInfo hasExist = null;
            for (DIYSaveInfo item : diySaveInfos) {
                if (item.getType().equals(customeType.getType())) {
                    isExist = true;
                    hasExist = item;
                }
            }

            if (isExist) {
                hasExist.setSelectType(customTypeItem.getId());
                hasExist.setSelectName(customTypeItem.getName());
                hasExist.setMoney(customTypeItem.getPrice());
                if (hasExist.getType().equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
                    hasExist.setNum(count);
                }
                LoginUtils.updateDIY(hasExist);
            } else {
                DIYSaveInfo diySaveInfo = new DIYSaveInfo();
                diySaveInfo.setType(customeType.getType());
                diySaveInfo.setTypeName(customeType.getName());
                diySaveInfo.setSelectName(customTypeItem.getName());
                diySaveInfo.setSelectType(customTypeItem.getId());
                diySaveInfo.setMoney(customTypeItem.getPrice());
                if (customeType.getType().equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
                    diySaveInfo.setNum(count);
                }
                diySaveInfos.add(diySaveInfo);
                SPUtils.getInstance().put(Constants.SAVE_DIY_SELECT,new Gson().toJson(diySaveInfos));
            }
        }

        List<DIYSaveInfo>  pp = LoginUtils.getDIY();
        LogUtils.w("dyc",pp);
    }
}
