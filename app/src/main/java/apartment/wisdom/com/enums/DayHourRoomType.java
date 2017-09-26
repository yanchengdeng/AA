package apartment.wisdom.com.enums;

//0  天房  1钟房  类型
public enum DayHourRoomType {

    DAY_HOUR_ROOM_TYPE_DAY("0"), DAY_HOUR_ROOM_TYPE_HOUR("1");

    private String type;

    DayHourRoomType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

