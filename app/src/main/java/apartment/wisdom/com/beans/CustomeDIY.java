package apartment.wisdom.com.beans;

import java.io.Serializable;
import java.util.List;

//客户自选套餐
public class CustomeDIY implements Serializable{

    public List<CustomeType.CustomTypeItem> roomLayoutList;//Array[2],
    public List<CustomeType.CustomTypeItem> breakfastList;//Array[3],
    public List<CustomeType.CustomTypeItem> wineList;//Array[4],
    public List<CustomeType.CustomTypeItem> fivePieceList;//Array[4],
    public List<CustomeType.CustomTypeItem> aromaList;//Array[4]

}
