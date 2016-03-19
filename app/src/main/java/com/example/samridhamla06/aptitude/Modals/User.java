package com.example.samridhamla06.aptitude.Modals;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String username;
    private int age;
    private String location;

    @Override
    public String toString() {
        return "Name = " + username;
    }

    public User(String username, int age, String location) {
        this.username = username;
        this.age = age;
        this.location = location;
    }

    public static User createUser(String username, int age, String location){
        return new User(username,age,location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
