package apartment.wisdom.com.beans;

//旅行 助手  item 信息
public class AssistantItemInfo {
    private boolean isOnlineCard = false;//是否已经网络取卡   已付款未入住 可退款   ，以网络取卡的可以app开门  自助退房

    public boolean isOnlineCard() {
        return isOnlineCard;
    }

    public void setOnlineCard(boolean onlineCard) {
        isOnlineCard = onlineCard;
    }

    public AssistantItemInfo(boolean isOnlineCard) {
        this.isOnlineCard = isOnlineCard;
    }
}
