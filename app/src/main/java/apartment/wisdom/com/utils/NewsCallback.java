package apartment.wisdom.com.utils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import apartment.wisdom.com.beans.AAResponse;
import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.events.LoginOutSuccessEvent;
import okhttp3.Response;

public abstract class NewsCallback<T> extends AbsCallback<T> {

    /**
     * 这里的数据解析是根据 http://gank.io/api/data/Android/10/1 返回的数据来写的
     * 实际使用中,自己服务器返回的数据格式和上面网站肯定不一样,所以以下是参考代码,根据实际情况自己改写
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
//        String xxx = AESUtil.decrypt(new String(response.body().bytes()), Constants.Net.ACCESS_KEY);
//        Log.w("==dyc==",xxx);
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");

        Type rawType = ((ParameterizedType) type).getRawType();
        if (rawType == AAResponse.class) {
            String decy = AESCipher.aesDecryptString(new String(response.body().bytes()), Constants.Net.ACCESS_KEY);
            LogUtils.w("dyc", decy);
            AAResponse aaResponse = new Gson().fromJson(decy, type);
            if (aaResponse.resultCode.equals("0000")) {
                response.close();
                return (T) aaResponse;
            } else {
                response.close();
                if (aaResponse.resultCode.equals("0005")){
                    EventBus.getDefault().post(new LoginOutSuccessEvent());
                    throw new IllegalStateException("登陆超时");
                }else {
                    throw new IllegalStateException(aaResponse.msg);
                }
            }
        } else {
            response.close();
            throw new IllegalStateException("数据解析失败!");
        }
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (!NetworkUtils.isConnected()){
            ToastUtils.showShort("请检查网络");
            request.adapt().cancel();
        }
    }


    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        LogUtils.w("dyc",response);
    }
}
