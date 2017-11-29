package apartment.wisdom.com.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
        ((MyGridView) helper.getView(R.id.lv_custome)).setAdapter(new CustomeNeedListItemAdapter(context, item));
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
