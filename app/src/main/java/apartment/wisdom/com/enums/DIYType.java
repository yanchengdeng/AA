package apartment.wisdom.com.enums;


//个性化定制
public enum DIYType {
    DIY_TYPE_BRAKFAST("0"),DIY_TYPE_ROOM_LAYOUT("1"),DIY_TYPE_WINE("2"),DIY_TYPE_FIVE_PIECES("3"),DIY_TYPE_ARMOS("4");

    private String type;
    DIYType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
