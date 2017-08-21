package apartment.wisdom.com.beans;


import java.util.List;

public class CustomeType {
    private String name;
    private int type;
    private List<CustomeType.CustomTypeItem> customTypeItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CustomTypeItem> getCustomTypeItems() {
        return customTypeItems;
    }

    public void setCustomTypeItems(List<CustomTypeItem> customTypeItems) {
        this.customTypeItems = customTypeItems;
    }

    public static class CustomTypeItem {
        private String name;
        private String pic;
        private boolean isSelect;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
