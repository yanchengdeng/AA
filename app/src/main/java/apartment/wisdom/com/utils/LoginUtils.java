package apartment.wisdom.com.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apartment.wisdom.com.beans.CustomeType;
import apartment.wisdom.com.beans.DIYSaveInfo;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;

/**
 * Author: 邓言诚  Create at : 17/8/17  13:59
 * Email: yanchengdeng@gmail.com
 * Describle: 用户工具
 */
public class LoginUtils {

    //获取登陆状态
    public static boolean getLoginStatus() {
        return SPUtils.getInstance().getBoolean(Constants.IS_LOGIN_STATUS, false);
    }


    //记录登陆状态
    public static void setLoginStatus(boolean isLogin) {
        SPUtils.getInstance().put(Constants.IS_LOGIN_STATUS, isLogin);
    }

    //用户信息
    public static UserInfo getUserInfo() {

        String userInfo = SPUtils.getInstance().getString(Constants.USER_INFO);
        if (TextUtils.isEmpty(userInfo)) {
            return new UserInfo();
        } else {
            return new Gson().fromJson(userInfo, UserInfo.class);
        }
    }

    //会员卡信息
    public static String getCardLeveName(String cardLevel) {
        //0001-金卡，0002-银卡，0003-普卡
        if (cardLevel.equals("0001")) {
            return "金卡";
        } else if (cardLevel.equals("0002")) {
            return "银卡";
        } else {
            return "普卡";
        }
    }


    //是否存在该类型的选项
    public static boolean hasSelectedByType(List<CustomeType> customeTypes, String type) {
        boolean isSelected = false;
        if (customeTypes!=null && customeTypes.size()>0) {
            for (CustomeType customeType : customeTypes) {
                for (CustomeType.CustomTypeItem item : customeType.getCustomTypeItems()) {
                    if (item.getDiy_type().equals(type)) {
                        isSelected = true;
                    }
                }
            }
        }
        return isSelected;
    }

    //根据类型 获取 选取的id  2,2,2
    public static String getSelectIdsByType(List<CustomeType> customeTypes, String type) {
        List<CustomeType.CustomTypeItem> customTypeItems = new ArrayList<>();
        for (CustomeType customeType : customeTypes) {
            for (CustomeType.CustomTypeItem item : customeType.getCustomTypeItems()) {
                if (item.getDiy_type().equals(type) && item.isSelect()) {
                    customTypeItems.add(item);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customTypeItems.size(); i++) {
            if (i == customTypeItems.size() - 1) {
                sb.append(customTypeItems.get(i).getId());
            } else {
                sb.append(customTypeItems.get(i).getId()).append(",");
            }
        }
        return sb.toString();
    }

    //根据类型 获取 选取的数量  2,2,2
    public static String getSelectCountsByType(List<CustomeType> customeTypes, String type) {
        List<CustomeType.CustomTypeItem> customTypeItems = new ArrayList<>();
        for (CustomeType customeType : customeTypes) {
            for (CustomeType.CustomTypeItem item : customeType.getCustomTypeItems()) {
                if (item.getDiy_type().equals(type) && item.isSelect()) {
                    customTypeItems.add(item);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customTypeItems.size(); i++) {
            if (i == customTypeItems.size() - 1) {
                sb.append(customTypeItems.get(i).getNum());
            } else {
                sb.append(customTypeItems.get(i).getNum()).append(",");
            }
        }
        return sb.toString();
    }

    //已选择的套餐
    public static List<DIYSaveInfo> getDIY(List<CustomeType> customeTypes) {
        if (customeTypes!=null && customeTypes.size()>0) {
            List<CustomeType.CustomTypeItem> customTypeItems = new ArrayList<>();
            for (CustomeType customeType : customeTypes) {
                for (CustomeType.CustomTypeItem item : customeType.getCustomTypeItems()) {
                    if (item.isSelect()) {
                        customTypeItems.add(item);
                    }
                }
            }
            List<DIYSaveInfo> diySaveInfos = new ArrayList<>();
            for (CustomeType.CustomTypeItem item : customTypeItems) {
                DIYSaveInfo diySaveInfo = new DIYSaveInfo();
                diySaveInfo.setSelectName(item.getName());
                diySaveInfo.setMoney(item.getPrice());
//             diySaveInfo.setType();
                diySaveInfo.setNum(item.getNum());
                diySaveInfo.setTypeName(item.getType_name());
                diySaveInfos.add(diySaveInfo);
            }
            return diySaveInfos;
        }else{
            return new ArrayList<>();
        }
    }

    //重置已选择的套餐
    public static void setDIYByTypeResetSelected(List<CustomeType.CustomTypeItem> customTypeItems, String type) {
        for (CustomeType.CustomTypeItem item : customTypeItems) {
            if (item.getDiy_type().equals(type)) {
                item.setSelect(false);
            }
        }
    }

    public static boolean isZeroTime() {

        Calendar galendar = Calendar.getInstance();

        int hours = galendar.get(Calendar.HOUR_OF_DAY);
        if (hours == 0 || hours == 1 || hours == 2 || hours == 3) {
            return true;
        } else {
            return false;
        }
    }
}
