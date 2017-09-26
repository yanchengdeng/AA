package apartment.wisdom.com.enums;


//订单状态

/**
 * 订单状态（0-待支付，1-待入住，2-已入住，3-退房申请(退房申请已提交，请等待查房)，
 * 4-已查房(物件有损坏请联系前台人员)，5-确认退房(请自助机办理退房)，
 * 6-退款成功，7-取消订单,9-已离店)
 */
public enum OrderStatus {

    ORDER_STATUS_NO_PAY("0","待支付"), ORDER_STATUS_WAITING_LIVE("1","待入住"), ORDER_STATUS_HAVE_LIVED("2","已入住"),
    ORDER_STATUS_CHECK_OUT("3","退房中"), ORDER_STATUS_ALREADY_CHECK_ROOM("4","已查房"), ORDER_STATUS_CONFIRM_OUT("5","确认退房"),
    ORDER_STATUS_DRAWBACK_SUCCESS("6","退款成功"),
    ORDER_STATUS_CANCLE_ORDER("7","取消订单"), ORDER_STATUS_TIMEOUT("8","订单超时"),ORDER_STATUS_LEFT_HOTEL("9","已离店");

    private String type,name;

    OrderStatus(String type,String name) {
        this.type = type;
        this.name = name;    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
