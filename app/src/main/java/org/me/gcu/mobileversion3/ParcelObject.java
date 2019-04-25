package org.me.gcu.mobileversion3;

//
// Name - Abdul Qadir Dar
// Student ID - S1511786
// Programme of Study - Computing Bsc
//

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelObject implements Parcelable {


    String dateTime;
    String location;
    String latitude;
    String longitude;
    String depth;
    String magnitude;

    protected ParcelObject(Parcel in) {
        dateTime = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        depth = in.readString();
        magnitude = in.readString();
    }

    public static final Creator<ParcelObject> CREATOR = new Creator<ParcelObject>() {
        @Override
        public ParcelObject createFromParcel(Parcel in) {
            return new ParcelObject(in);
        }

        @Override
        public ParcelObject[] newArray(int size) {
            return new ParcelObject[size];
        }
    };

    public ParcelObject(String dat, String loc, String lat, String lngt, String dpth, String mgt) {
        dateTime = dat;
        location = loc;
        latitude = lat;
        longitude = lngt;
        depth = dpth;
        magnitude = mgt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateTime);
        dest.writeString(location);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(depth);
        dest.writeString(magnitude);
    }
}
