package com.example.samridhamla06.aptitude.Models;

import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("userName")
    private String name;
    @SerializedName("age")
    private int age;
    @SerializedName("location")
    private String location;
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
