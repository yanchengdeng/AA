package apartment.wisdom.com.beans;

import java.io.Serializable;
import java.util.List;

//优惠劵信息
public class TicketInfo implements Serializable {

    public List<TicketInfoItem> couponList;

    public static class TicketInfoItem implements Serializable{
        public String couponName;//新用户,
        public String validDate;//2017-12-31,
        public String couponMoney;//1,
        public String couponId;//51,
        public String useStatus;//1
        public boolean isSelect;
    }


}
