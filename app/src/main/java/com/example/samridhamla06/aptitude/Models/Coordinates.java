package com.example.samridhamla06.aptitude.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samridhamla06 on 23/06/16.
 */
public class Coordinates {

    private double latitude;
    private double longitude;

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
}
