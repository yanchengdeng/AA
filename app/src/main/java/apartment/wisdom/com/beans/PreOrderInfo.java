package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

public class PreOrderInfo implements Serializable {

    public String orderNo;//YD20170903002800001,
    public String consumeTotalPrice;//0.02
    public String checkInNo;//邀请码
    public List<CustomeItem> consumeList;


    public static class CustomeItem implements Serializable{
        public String consumePrice;//0.01,
        public String consumeName;//房间费,
        public int consumeNum;//1
        public String type;
        public String typeName;
    }
}
