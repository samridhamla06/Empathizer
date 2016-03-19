package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Modals.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by samridhamla06 on 20/03/16.
 */


public class GroupPageResponseListener implements Response.Listener<JSONArray> {
    private final Context groupPageContext;
    private  ArrayAdapter adapterForUsers;
    private  List<User> userList;
    private JSONArray jsonArrayReceived;


    public GroupPageResponseListener(Context groupPageContext, ArrayAdapter adapterForUsers, List<User> userList) {
        this.groupPageContext = groupPageContext;
        this.adapterForUsers = adapterForUsers;
        this.userList = userList;
    }

    @Override
    public void onResponse(JSONArray response) {
        jsonArrayReceived = response;
        Log.d("RECEIVED_JSON_ARRAY", jsonArrayReceived.toString());
        actOnResponse(jsonArrayReceived);
    }

    private void actOnResponse(JSONArray jsonArrayReceived){
        sendUsersInfoToGroupPage(jsonArrayReceived);
    }

    private void sendUsersInfoToGroupPage(JSONArray jsonArrayReceived) {
        User user;
        JSONObject myJson;
        JSONArray actualJSONArray;                  //*****without other fields*****

        try {
            myJson = jsonArrayReceived.getJSONObject(0);
            actualJSONArray = myJson.getJSONArray("users");
            Log.d("JSON_ARRAY_RECEIVED",actualJSONArray.toString());
            for (int i = 0; i < actualJSONArray.length(); i++) {
                myJson = actualJSONArray.getJSONObject(i);
                user =  addJSONToUserObject(myJson);
                userList.add(user);
            }

            adapterForUsers.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
            Log.d("Error","Wrong JSON received");
        }

    }

    private User addJSONToUserObject(JSONObject myJson) throws JSONException{
        return User.createUser(myJson.getString("username"), 20, "Pune");
    }
}
