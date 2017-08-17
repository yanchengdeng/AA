package apartment.wisdom.com.enums;

//支付/充值方式
public enum PayStyle {
    PAY_STYLE_BALANCE(0),PAY_STYLE_ALIPAY(1),PAY_STYLE_WX(2);

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
