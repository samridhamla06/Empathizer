package com.example.samridhamla06.aptitude.Utility;

import android.content.SharedPreferences;
import com.example.samridhamla06.aptitude.Views.LoginPage;
import org.json.JSONException;
import org.json.JSONObject;

public class UserRelated {

    private static String getValue(SharedPreferences sharedPreferences, String key){
        return sharedPreferences.getString(key, "000");
    }


    public static JSONObject createUserJSONObjectFromSharedPreferences(SharedPreferences sharedPreferences) throws JSONException {
        JSONObject userJson = new JSONObject();
        String userName = getValue(sharedPreferences,LoginPage.USER_NAME);
        String email = getValue(sharedPreferences,LoginPage.EMAIL);
        String suffering = getValue(sharedPreferences,LoginPage.SUFFERING_NAME);
        String userId = getValue(sharedPreferences,LoginPage.USER_ID);
        String user_location = getValue(sharedPreferences,LoginPage.LOCATION);
        userJson.put(LoginPage.USER_NAME, userName);
        userJson.put(LoginPage.EMAIL, email);
        userJson.put(LoginPage.SUFFERING_NAME, suffering);
        userJson.put(LoginPage.USER_ID, userId);
        userJson.put(LoginPage.LOCATION, user_location);
        return userJson;
    }



}
