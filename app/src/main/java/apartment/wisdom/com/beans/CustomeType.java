package apartment.wisdom.com.beans;


import java.util.List;

public class CustomeType {
    private String name;
    private String type;

    private List<CustomeType.CustomTypeItem> customTypeItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CustomTypeItem> getCustomTypeItems() {
        return customTypeItems;
    }

    public void setCustomTypeItems(List<CustomTypeItem> customTypeItems) {
        this.customTypeItems = customTypeItems;
    }

    public static class CustomTypeItem {
        private String id;//18,
        private String price;//0.01,
        private String name;//气球,
        private String img;
        private boolean isSelect;
        private boolean isSupportMultSelect;//是否支持多选
        private int num = 1;
        private String  diy_type;
        private String type_name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isSupportMultSelect() {
            return isSupportMultSelect;
        }

        public void setSupportMultSelect(boolean supportMultSelect) {
            isSupportMultSelect = supportMultSelect;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String  getDiy_type() {
            return diy_type;
        }

        public void setDiy_type(String diy_type) {
            this.diy_type = diy_type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
