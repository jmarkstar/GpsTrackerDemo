package com.jmarkstar.gpstracker.models;

import java.util.Date;

/** Model
 * Created by jmarkstar on 23/05/2017.
 */
public class LocationModel {

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
}
