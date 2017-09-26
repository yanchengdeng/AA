package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

//消费信息
public class CusumListInfo implements Serializable{

    public List<CusumItem> consumeList;

    public class CusumItem implements Serializable{
       public String consumeDate;//2017-09-04,
        public String   consumePrice;//0.04,
        public String   consumeTitle;//房间费
    }
}
