package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.UserPageView;

import android.util.Log;
import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.Views.LoginPage;
import com.example.samridhamla06.aptitude.Views.UserPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UserPageResponseListener implements Response.Listener<JSONArray>{
    private final UserPage userPageReference;
    private JSONArray jsonArrayReceived;

    public UserPageResponseListener(UserPage userPageReference) {
        this.userPageReference = userPageReference;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {

    }

    @Override
    public void onResponse(JSONArray response) {
        jsonArrayReceived = response;
        Log.d("USER_JSON_RECEIVED", jsonArrayReceived.toString());
        actOnResponse(jsonArrayReceived);
    }

    private void actOnResponse(JSONArray jsonArrayReceived) {
        try {
            sendFullUserInfoToUserPage(jsonArrayReceived);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void sendFullUserInfoToUserPage(JSONArray jsonArrayReceived) throws JSONException{
        User userReceived;
        JSONObject jsonReceived;
        jsonReceived = jsonArrayReceived.getJSONObject(0);//because we know it will return a single object :)
        userReceived = convertJSONToUser(jsonReceived);
        userPageReference.fillAllTextViews(userReceived);
    }

    private User convertJSONToUser(JSONObject jsonObjectReceived) throws JSONException {
        String name = jsonObjectReceived.getString(LoginPage.USER_NAME);
        String location = jsonObjectReceived.getString("location");
        String userId = jsonObjectReceived.getString("_id");
        return User.createUser(name,location,userId);
    }


}
