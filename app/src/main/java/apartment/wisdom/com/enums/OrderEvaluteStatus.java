package apartment.wisdom.com.enums;


//订单评价 评价状态（0-待评论，1-已评论)
public enum  OrderEvaluteStatus {
    ORDER_EVALUTE_STATUS_NO("0","待评论"),ORDER_EVALUTE_STATUS_HAS_EVALUTED("1","已评论");

    private String type,name;

    OrderEvaluteStatus(String type,String name) {
        this.type = type;
        this.name = name;
    }

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
