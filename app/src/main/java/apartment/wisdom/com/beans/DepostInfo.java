package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

//充值返现
public class DepostInfo implements Serializable {


    public List<DepostInfoItem> moneyList;


    public class DepostInfoItem implements Serializable {
        public String rechargeMoney;//600,
        public String giveMoney;//50,
        public String rechargeId;//10029
        public boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
