package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

public class CommendListInfo implements Serializable {
    public String storeHotelScore;//5.00,
    public String storeScore;//5.00,
    public String storeDeviceScore;//5.00,
    public String storePercent;//100%,
    public String storeRoomHealthScore;//5.00,
    public List<HotelCommendItem> customerList;
    public String storeEnvironmentScore;//5.00
}
