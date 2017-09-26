package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

public class MyCommendList implements Serializable {
    public List<CommendInfo> storeList;

    public class CommendInfo implements Serializable{
        public String storeName;//宝龙分店,
        public String storeScore;//5.00,
        public String address;//福建福州省委党校8栋304,
        public String storeImage;
        public String storeId;
        public String storePrice;
    }
}
