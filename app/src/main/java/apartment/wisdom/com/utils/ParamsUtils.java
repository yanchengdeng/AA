package apartment.wisdom.com.utils;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import apartment.wisdom.com.commons.Constants;

public class ParamsUtils {

    /**
     * 获取请求加密参数
     *
     * @param paramsMaps
     * @param method
     * @return
     */
    public static String getParams(Map<String, Object> paramsMaps, String method) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("function", method);
        if (!TextUtils.isEmpty(LoginUtils.getUserInfo().token)) {
            paramsMaps.put("token", LoginUtils.getUserInfo().token);
        }
        params.put("data", JSONObject.toJSON(paramsMaps));
        LogUtils.w("dyc接口参数", JSONObject.toJSON(paramsMaps).toString());
        LogUtils.w("dyc接口方法", method);
        String sign = Signature.getSign(params, Constants.Net.ACCESS_KEY);
        params.put("sign", sign);
        String jsonStr = JSON.toJSONString(params);
        System.out.print(jsonStr);
        String encryptData = null;
        try {
            encryptData = AESCipher.aesEncryptString(jsonStr, Constants.Net.ACCESS_KEY);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptData;
    }
}
