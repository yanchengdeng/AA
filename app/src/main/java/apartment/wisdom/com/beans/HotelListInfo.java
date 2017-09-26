package apartment.wisdom.com.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Author;// 邓言诚  Create at ;// 17/8/13  18;//13
 * Email;// yanchengdeng@gmail.com
 * Describle;// 旅馆列表信息
 */
public class HotelListInfo implements Serializable {
    public List<HotelListItem> storeList;
    public String storeNum;
    public String area;


    public class HotelListItem implements Serializable {
        public String storeRemark;//全自动化,
        public String storeRoomMinPrice;//0.00,
        public String phone;//15860292278,
        public String storeScore;//,
        public String storeName;//仓山分店,
        public String storeDevice;//wif,
        public String address;//福建福州福建省福州市仓山分店仓山小区,
        public String coordinate;//119.279984,26.050778,
        public String storePayment;//微信支付;支付宝支付,
        public List<StoreImageInfo> storeImageList;//http;////mxs-samp.oss-cn-hangzhou.aliyuncs.com/store/1503838761408.jpg?Expires=4659512381&OSSAccessKeyId=LTAIq4qbVMkvIwP3&Signature=4%2BKtj%2Bizt176ekUmiFXqOoSu%2BLY%3D,
        public String storeId;

        public class StoreImageInfo implements Serializable{
            public String storeImage;
        }
    }

}
