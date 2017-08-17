package apartment.wisdom.com.utils;

import com.blankj.utilcode.util.SPUtils;

import apartment.wisdom.com.commons.Constants;

/**
*
* Author: 邓言诚  Create at : 17/8/17  13:59
* Email: yanchengdeng@gmail.com
* Describle: 用户工具
*/
public class LoginUtils {

    //获取登陆状态
    public static boolean getLoginStatus(){
        return SPUtils.getInstance().getBoolean(Constants.IS_LOGIN_STATUS,false);
    }



    //记录登陆状态
    public static void setLoginStatus(boolean isLogin){
        SPUtils.getInstance().put(Constants.IS_LOGIN_STATUS,isLogin);
    }
}
