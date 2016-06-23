package com.example.samridhamla06.aptitude.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Group {
    @SerializedName("groupName")
    private String name;
    @SerializedName("_id")
    private String id;
    @SerializedName("location")
    private String location;
    @SerializedName("description")
    private String description;
    @SerializedName("sufferingName")
    private String sufferingName;
    @SerializedName("creatorId")
    private String creatorId;
    @SerializedName("loc")
    private Location loc;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @SerializedName("users")
    private List<User> users;

    public Group() {

    }


    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getSufferingName() {
        return sufferingName;
    }

    public void setSufferingName(String sufferingName) {
        this.sufferingName = sufferingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Group(String name, String id, String location, String description, String sufferingName, String creatorId, Location loc, List<User> users) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.description = description;
        this.sufferingName = sufferingName;
        this.creatorId = creatorId;
        this.loc = loc;
        this.users = users;
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
