package com.example.samridhamla06.aptitude.Models;

import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("userName")
    private String name;
    @SerializedName("age")
    private int age;
    @SerializedName("location")
    private String location;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("email")
    private String email;
    @SerializedName("aboutMe")
    private String aboutMe;
    @SerializedName("sufferingName")
    private String sufferingName;
    @SerializedName("currentStatus")
    private String currentStatus;
    @SerializedName("gender")
    private String gender;
    @SerializedName("_id")
    private String id;
    @SerializedName("password")
    private String password;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("loc")
    private Location loc;

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public User(String name, int age, String location, String email, String aboutMe, String sufferingName, String currentStatus, String gender, String id) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.email = email;
        this.aboutMe = aboutMe;
        this.sufferingName = sufferingName;
        this.currentStatus = currentStatus;
        this.gender = gender;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getSufferingName() {
        return sufferingName;
    }

    public void setSufferingName(String sufferingName) {
        this.sufferingName = sufferingName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;

    }

    public String toString() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }
}
