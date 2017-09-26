package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

//充值信息
public class RechargeListInfo implements Serializable {

    public List<RechargeItem> rechargeList;

    public class RechargeItem implements Serializable {
        public String rechargeDate;//2017-09-04,
        public String rechargePrice;//0.04,
        public String rechargeTitle;//房间费
    }
}
