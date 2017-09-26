package apartment.wisdom.com.beans;

import java.io.Serializable;

/**
 * Author;// 邓言诚  Create at ;// 17/8/16  00;//57
 * Email;// yanchengdeng@gmail.com
 * Describle;//  酒店订单 列表信息
 */

public class HotelOrderItem implements Serializable{
    public String orderNo;//YD20170904095600001,
    public String storeName;//宝龙分店,
    public String evaluateStatus;//0,
    public String checkInNo;//476138,
    public String checkOutTime;//2017-09-05,
    public String orderStatus;//1,
    public String checkInTime;//2017-09-04
    public String address;
    public String lastCheckInTime;
    public String roomType;
    public String storeImage;
    public String consumeSumPrice;
}
