package com.example.samridhamla06.aptitude.Utility;

import android.content.SharedPreferences;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.User;
import com.google.gson.Gson;

import org.json.JSONObject;

public class UserRelated {

    private static String getValue(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.getString(key, "000");
    }


    public static User getUserPOJOForCurrentUser(SharedPreferences sharedPreferences) {
        String userInfo = getValue(sharedPreferences, Constants.USER_INFO);
        return getUserObjectFromJson(userInfo);
    }

    public static String getJsonStringForCurrentUser(SharedPreferences sharedPreferences) {
        String userInfo = getValue(sharedPreferences, Constants.USER_INFO);
        return userInfo;
    }

    public static User getUserObjectFromJson(String userInfo) {
        return new Gson().fromJson(userInfo, User.class);
    }


    public static String convertUserObjectToJsonString(User selectedUser) {
        return new Gson().toJson(selectedUser);
    }

    public static String getUserIdForCurrentUser(SharedPreferences sharedPreferences){
        return getUserPOJOForCurrentUser(sharedPreferences).getId();
    }

    public static double getLatitudeForCurrentUser(SharedPreferences sharedPreferences) {
        return getUserPOJOForCurrentUser(sharedPreferences).getLatitude();
    }

    public static double getLongitudeForCurrentUser(SharedPreferences sharedPreferences) {
        return getUserPOJOForCurrentUser(sharedPreferences).getLongitude();
    }
}
