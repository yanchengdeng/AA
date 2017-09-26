package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

//自助选房信息
public class SelfRoomListInfo implements Serializable {

    public List<SelfRoomItem> roomList;
    public String keyWord;

    public class SelfRoomItem implements Serializable{
        public String roomDomain;//二楼,
        public String roomNo;//203
        public boolean isSelect;
        public String roomId;
    }
}
