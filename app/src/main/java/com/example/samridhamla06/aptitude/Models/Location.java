package com.example.samridhamla06.aptitude.Models;

import com.example.samridhamla06.aptitude.Constants;

import java.util.List;

/**
 * Created by samridhamla06 on 23/06/16.
 */
public class Location {


    private String type = Constants.POINT;
    private List<Double> coordinates;

    public Location(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
