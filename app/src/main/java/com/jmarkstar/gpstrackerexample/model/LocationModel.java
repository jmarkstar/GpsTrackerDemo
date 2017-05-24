package com.jmarkstar.gpstrackerexample.model;

import java.util.Date;

/** Model
 * Created by jmarkstar on 23/05/2017.
 */
public class LocationModel {

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
