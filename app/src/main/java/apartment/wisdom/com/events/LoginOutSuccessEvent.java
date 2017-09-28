package apartment.wisdom.com.events;

//退出登录成功通知
public class LoginOutSuccessEvent {

    private String msg;
    public LoginOutSuccessEvent(String msg) {
        this.msg = msg;
    }

    public LoginOutSuccessEvent(){

    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
