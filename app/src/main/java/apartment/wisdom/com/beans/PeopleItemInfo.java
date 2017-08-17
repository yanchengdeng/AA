package apartment.wisdom.com.beans;

import android.os.Parcel;
import android.os.Parcelable;

//旅客信息
public class PeopleItemInfo implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PeopleItemInfo() {
    }

    protected PeopleItemInfo(Parcel in) {
    }

    public static final Parcelable.Creator<PeopleItemInfo> CREATOR = new Parcelable.Creator<PeopleItemInfo>() {
        @Override
        public PeopleItemInfo createFromParcel(Parcel source) {
            return new PeopleItemInfo(source);
        }

        @Override
        public PeopleItemInfo[] newArray(int size) {
            return new PeopleItemInfo[size];
        }
    };
}
