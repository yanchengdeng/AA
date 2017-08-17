package apartment.wisdom.com.beans;

import android.os.Parcel;
import android.os.Parcelable;

//优惠劵信息
public class TicketInfo implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public TicketInfo() {
    }

    protected TicketInfo(Parcel in) {
    }

    public static final Parcelable.Creator<TicketInfo> CREATOR = new Parcelable.Creator<TicketInfo>() {
        @Override
        public TicketInfo createFromParcel(Parcel source) {
            return new TicketInfo(source);
        }

        @Override
        public TicketInfo[] newArray(int size) {
            return new TicketInfo[size];
        }
    };
}
