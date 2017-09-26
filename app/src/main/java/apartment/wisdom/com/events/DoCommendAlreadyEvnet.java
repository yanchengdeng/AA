package apartment.wisdom.com.events;


public class DoCommendAlreadyEvnet {
    private String orderNo;
    public DoCommendAlreadyEvnet(String orderNo) {
        this.orderNo = orderNo;

    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
