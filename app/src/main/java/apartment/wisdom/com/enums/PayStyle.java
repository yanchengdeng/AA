package apartment.wisdom.com.enums;

//支付/充值方式
//支付方式（0-微信支付，1-支付宝支付，2-银行卡支付，3-会员余额支付）
public enum PayStyle {
    PAY_STYLE_BALANCE(3),PAY_STYLE_ALIPAY(1),PAY_STYLE_WX(0);

    private int type;
    PayStyle(int i) {
        this.type = i;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
