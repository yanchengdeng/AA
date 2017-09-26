package apartment.wisdom.com.beans;

/**
 * 邮箱：yanchengdeng@gmail.com
 * 作者： 邓言诚 创建于： 2016/11/23 16:41.
 */

public class PayInfo {

    private WXPayInfo wxpayinfo;

    private String orderStr;

    public WXPayInfo getWxpayinfo() {
        return wxpayinfo;
    }

    public void setWxpayinfo(WXPayInfo wxpayinfo) {
        this.wxpayinfo = wxpayinfo;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}