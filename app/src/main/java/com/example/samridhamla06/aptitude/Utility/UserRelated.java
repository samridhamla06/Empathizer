package com.example.samridhamla06.aptitude.Utility;

import android.content.SharedPreferences;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRelated {

    private static String getValue(SharedPreferences sharedPreferences, String key){
        return sharedPreferences.getString(key, "000");
    }


    public static JSONObject createUserJSONObjectFromSharedPreferences(SharedPreferences sharedPreferences) throws JSONException {
        JSONObject userJson = new JSONObject();
        String userName = getValue(sharedPreferences, Constants.USER_NAME);
        String email = getValue(sharedPreferences, Constants.EMAIL);
        String suffering = getValue(sharedPreferences, Constants.SUFFERING_NAME);
        String userId = getValue(sharedPreferences, Constants.USER_ID);
        String user_location = getValue(sharedPreferences, Constants.LOCATION);
        userJson.put(Constants.USER_NAME, userName);
        userJson.put(Constants.EMAIL, email);
        userJson.put(Constants.SUFFERING_NAME, suffering);
        userJson.put(Constants.USER_ID, userId);
        userJson.put(Constants.LOCATION, user_location);
        return userJson;
    }

    public static User getUserObjectFromUserInfo(SharedPreferences sharedPreferences){
        String userInfo = getValue(sharedPreferences, Constants.USER_INFO);
        return getUserObjectFromJson(userInfo);
    }

    public static User getUserObjectFromJson(String userInfo) {
        return new Gson().fromJson(userInfo, User.class);
    }


    public static String convertUserObjectToJsonString(User selectedUser) {
        return new Gson().toJson(selectedUser);
    }
}
