package com.example.samridhamla06.aptitude.Modals;

import android.os.Parcel;
import android.os.Parcelable;

public class User  {
    private String username;
    private String name;
    private int age;
    private String location;
    private long id;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;

    }

    public String toString() {
        return username;
    }

    public User(String name, int age, String location,long id) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.id = id;
    }

    public static User createUser(String username, int age, String location,long id){
        return new User(username,age,location,id);
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }
}
