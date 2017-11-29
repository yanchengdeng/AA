package apartment.wisdom.com.beans;

/**
 * 邮箱：yanchengdeng@gmail.com
 * 作者： 邓言诚 创建于： 2016/11/23 16;//41.
 */

public class WXPayInfo {
    private   String   noncestr;//3LUm8dtC60rRJKfT,
    private   String       timestamp;//1479890184,
    private   String      sign;//724AED95F41CE97855D99048D1EB336A,
    private   String      wxpackage;//Sign=WXPay,
    private   String      partnerid;//,
    private   String      appid;//wxb317c47e9d1ceb31
    private String prepayid;//商戶號

    public String getNoncestr() {
        return noncestr;
    }


    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getWxpackage() {
        return wxpackage;
    }

    public void setWxpackage(String wxpackage) {
        this.wxpackage = wxpackage;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}