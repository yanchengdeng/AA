package apartment.wisdom.com.beans;


import java.io.Serializable;
import java.util.List;

//常用旅客
public class CustomPeopleList implements Serializable {
    public List<CustomPeopleItem> contacList;

    public class CustomPeopleItem implements Serializable

    {
        public String idType;//null,
        public String mobilePhone;//1586022279,
        public String checkInNo;//1,
        public String name;//张三
    }
}
