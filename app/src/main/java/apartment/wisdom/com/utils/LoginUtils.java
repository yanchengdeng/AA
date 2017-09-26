package apartment.wisdom.com.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.beans.DIYSaveInfo;
import apartment.wisdom.com.beans.UserInfo;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.enums.DIYType;

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

    //获取  diy 保存信息
    public static List<DIYSaveInfo> getDIY() {
        List<DIYSaveInfo> diySaveInfos = new ArrayList<>();
        String diyInfo = SPUtils.getInstance().getString(Constants.SAVE_DIY_SELECT);
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(diyInfo)) {
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            //将JSON的String 转成一个JsonArray对象
            JsonArray jsonArray = parser.parse(diyInfo).getAsJsonArray();
            for (JsonElement user : jsonArray) {
                diySaveInfos.add(gson.fromJson(user, DIYSaveInfo.class));
            }
        }
        return diySaveInfos;
    }

    //更新已存在diy
    public static void updateDIY(DIYSaveInfo hasExist) {
        List<DIYSaveInfo> diySaveInfos = getDIY();
        for (DIYSaveInfo item : diySaveInfos) {
            if (item.getType().equals(hasExist.getType())) {
                if (item.getType().equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
                    item.setNum(item.getNum());
                }
                item.setSelectType(hasExist.getSelectType());
                item.setSelectName(hasExist.getSelectName());
                item.setMoney(hasExist.getMoney());
            }
        }
        SPUtils.getInstance().put(Constants.SAVE_DIY_SELECT, new Gson().toJson(diySaveInfos));
    }

    //更新选择早餐数
    public static void updateBreakFast(int num) {
        List<DIYSaveInfo> diySaveInfos = getDIY();
        boolean isExist = false;
        for (DIYSaveInfo item : diySaveInfos) {
            if (item.getType().equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
                item.setNum(num);
                isExist = true;
            }
        }
        if (isExist) {
            SPUtils.getInstance().put(Constants.SAVE_DIY_SELECT, new Gson().toJson(diySaveInfos));
        }
    }

    public static CharSequence getSelectIdByType(String type) {
        List<DIYSaveInfo> diySaveInfos = getDIY();
        String  selectTyper ="";
        for (DIYSaveInfo item : diySaveInfos) {
            if (item.getType().equals(type)) {
                selectTyper = item.getSelectType();
            }
        }
        return selectTyper;
    }


    public static int getBreakfastNum() {
        List<DIYSaveInfo> diySaveInfos = getDIY();
        int  num =1;
        for (DIYSaveInfo item : diySaveInfos) {
            if (item.getType().equals(DIYType.DIY_TYPE_BRAKFAST.getType())) {
                num = item.getNum();
            }
        }
        return num;
    }
}
