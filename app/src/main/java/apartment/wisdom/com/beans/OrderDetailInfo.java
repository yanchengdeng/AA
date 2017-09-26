package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

public class OrderDetailInfo implements Serializable {

    public String liveName;//张三,
    public String storeName;//宝龙分店,
    public String remark;//,
    public String checkOutTime;//2017-09-05,
    public String storeAddress;//福建福州省委党校8栋304,
    public String storePhone;//15860292279,
    public String orderStatus;//1,
    public String checkInTime;//2017-09-04,
    public String cancelPromptInfo;//本订单最晚取消时间为09月04日 18;//00；当超过最晚取消时间后，蔓心公寓将不接受您的取消请求，同时已支付的预款项不予退还，优惠券订单一经取消后无法恢复。退款相关问题详询<font color=red>4006-456-999</font>。,
    public String roomDepositPrice;//0.01,
    public String orderNo;//YD20170904173000001,
    public String mobilePhone;//15860292278,
    public String evaluateStatus;//0,
    public String consumeSumPrice;//0.02,
    public String roomType;//标准房,
    public String roomNum;//1
    public List<PreOrderInfo.CustomeItem> consumeList;
}
