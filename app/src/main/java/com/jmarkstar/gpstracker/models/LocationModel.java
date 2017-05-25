package com.jmarkstar.gpstracker.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

/** Model
 * Created by jmarkstar on 23/05/2017.
 */
public class LocationModel implements Parcelable {

    public static final String TABLE_NAME = "location";
    public static final String ID_FIELD = "_id";
    public static final String LATITUDE_FIELD = "longitude";
    public static final String LONGITUDE_FIELD = "latitude";
    public static final String DATE_FIELD = "date";

    private long id;
    private double latitude;
    private double longitude;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    public LocationModel() {
    }

    protected LocationModel(Parcel in) {
        this.id = in.readLong();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Parcelable.Creator<LocationModel> CREATOR = new Parcelable.Creator<LocationModel>() {
        @Override public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override public String toString() {
        return "LocationModel{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", date=" + date +
                '}';
    }
}
