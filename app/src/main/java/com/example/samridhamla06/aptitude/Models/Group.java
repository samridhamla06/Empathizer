package com.example.samridhamla06.aptitude.Models;

import com.google.gson.annotations.SerializedName;


public class Group {
    @SerializedName("groupName")
    private String name;
    @SerializedName("_id")
    private String id;
    @SerializedName("location")
    private String location;
    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Group(String name, String id, String location, String description) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getLocation() {
        return location;
    }
}
