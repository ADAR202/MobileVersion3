package org.me.gcu.mobileversion3;

//
// Name - Abdul Qadir Dar
// Student ID - S1511786
// Programme of Study - Computing Bsc
//

import android.os.Parcel;
import android.os.Parcelable;

public class custObj   {

    String dateTime;
    String location;
    String latitude;
    String longitude;
    String depth;
    String magnitude;


    public custObj() {

    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

}
