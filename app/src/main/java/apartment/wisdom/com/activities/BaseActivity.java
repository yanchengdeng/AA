package apartment.wisdom.com.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.lzy.okgo.OkGo;


/**
*
* Author:
* Email:
* Create at : 17/5/6
* Describle: 基类 ：返回功能 、跳转功能、进度条加载  、左手势返回功能
*/
public class BaseActivity extends AppCompatActivity {

    public SVProgressHUD mSVProgressHUD;
    public Context mContext;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mSVProgressHUD = new SVProgressHUD(this);
    }

    /***
     * 打开一个新界面
     *
     * @param pClass
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, 0);
    }


    /***
     * 打开一个新界面
     *alipay_ic.png
     * @param pClass
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, 0);
    }

    /***
     * 打开新界面
     *
     * @param pClass
     * @param requestCode 请求码
     */
    public void openActivity(Class<?> pClass, int requestCode) {
        openActivity(pClass, null, requestCode);
    }

    /***
     * 打开新界面
     *
     * @param pClass      界面类
     * @param pBundle     携带参数
     * @param requestCode 请求码
     */
    public void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(mContext, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(mContext, intent, requestCode);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mSVProgressHUD.isShowing()) {
                mSVProgressHUD.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mSVProgressHUD.isShowing()) {
            mSVProgressHUD.dismiss();
        }
    }

    /***
     * 开启新界面
     *
     * @param context     Context 对象
     * @param intent      意向
     * @param requestCode 请求码 0代表未有返回码
     */
    public static void startActivity(Context context, Intent intent, int requestCode) {
        if (requestCode == 0) {
            context.startActivity(intent);
        }else{
            ((BaseActivity) context).startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
