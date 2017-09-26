package apartment.wisdom.com.enums;

//优惠劵类型
public enum TicketType {
    //（0-已使用 ，1-未使用，2-已过期）
    TICKET_TYPE_USED("0"), TICKET_TYPE_NOT_USER("1"), TICKET_TYPE_OUTDATE("2");

    private String type;

    TicketType(String type) {
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
