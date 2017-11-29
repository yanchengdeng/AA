package apartment.wisdom.com.beans;


import com.tubb.calendarselector.library.FullDay;

import java.io.Serializable;
import java.util.List;

//天房酒店
public class DayGroupHotel {
    public String name;
    public String price;
    public String discountPrice;
    public String roomNum;
    public List<DayGroupHotelItem> items;
    public String roomDeposit;//押金
    public String roomRisePrice;//涨价 周五、六
    public String roomTypeId;
    public String storeId;
    public String hotelName;
    public FullDay standIn;
    public FullDay standOut;
    public int differDays;
    public String selectType;
    public int bespeakDays;
    public List<RoomListInfo.RoomTypeImage> roomTypeImageList;
    public String roomTypeRemark;

    public static class DayGroupHotelItem implements Serializable{
        public String roomPirce;
        public String discountPrice;
        public String roomNum;
        public String liveNum;
        public String bedNum;
        public String roomTypeId;
        public String storeId;
        public String hotelName;
        public FullDay standIn;
        public FullDay standOut;
        public int differDays;
        public String selectType;
        public String roomDeposit;//押金
        public String roomRisePrice;//涨价 周五、六
        public List<RoomListInfo.RoomTypeImage> roomTypeImageList;
        public String roomTypeRemark;
    }
}
