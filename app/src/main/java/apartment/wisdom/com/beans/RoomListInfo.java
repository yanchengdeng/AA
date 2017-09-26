package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

public class RoomListInfo implements Serializable {

    public List<DayRoom> dayRoomList;//
    public String storeHotelScore;//0.00,
    public String storeScore;//0.00,
    public List<HourRoom> hourRoomList;//Array[2],
    public String storeDeviceScore;//0.00,
    public String storePercent;//0,
    public String storeRoomHealthScore;//0.00,
    public String evaluateTotal;//0,
    public List<CustomePriceInfo> customerList;//Array[0],
    public String storeEnvironmentScore;//0.00,
    public String bespeakTime;//08;//00-10;//00

    public class DayRoom implements Serializable {
        public int bespeakDays;//0,
        public String roomNum;//3,
        public String roomPrice;//0.01,/打折价
        public String bedNum;//1,
        public String roomTypeName;//普通房,
        public String liveNum;//2,
        public String roomTypeId;//3
        public String shopPrice;//门市价
        public String storeId;
        public String roomDeposit;//押金
        public String roomRisePrice;//涨价 周五、六
    }

    public static class HourRoom implements Serializable {
        public String roomNum;//0,
        public String roomPrice;//0.01,
        public String bedNum;//1,
        public String roomTypeName;//普通房,
        public String liveNum;//2,
        public String hourNum;//4,
        public String roomTypeId;//3
        public String storeId;
        public String hotelName;
        public String standIn;
        public String standInSimple;
        public String standOut;
        public String standOutSimple;
        public int differDays;
        public String selectType;
        public String bespeakTime;
        public String roomDeposit;//押金
        public String roomRisePrice;//涨价 周五、六
    }

    public class CustomePriceInfo implements Serializable {
        public String username;
        public String customerEvaluate;
        public String storeEvaluate;
        public String customerScore;
        public String evaluateDate;

    }
}
