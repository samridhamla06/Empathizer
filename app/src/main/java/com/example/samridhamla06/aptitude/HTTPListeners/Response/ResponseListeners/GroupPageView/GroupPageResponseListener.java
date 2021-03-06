package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Adapters.GroupPageRecyclerViewAdapter;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.Utility.UserRelated;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class GroupPageResponseListener implements Response.Listener<JSONArray> {
    private static final int POSITION_ON_TOP = 0;
    private final Activity groupPageReference;
    private GroupPageRecyclerViewAdapter groupPageAdapter;
    private List<User> userList;
    private JSONArray jsonArrayReceived;


    public GroupPageResponseListener(Activity groupPageReference, GroupPageRecyclerViewAdapter adapterForUsers, List<User> userList) {
        this.groupPageReference = groupPageReference;
        this.groupPageAdapter = adapterForUsers;
        this.userList = userList;
    }

    @Override
    public void onResponse(JSONArray response) {
        jsonArrayReceived = response;
        Log.d("RECEIVED_JSON_ARRAY", jsonArrayReceived.toString());
        actOnResponse();
    }

    private void actOnResponse() {
        sendUsersInfoToGroupPage();
    }

    private void sendUsersInfoToGroupPage() {
        User user;
        JSONObject myJson;
        JSONArray actualJSONArray;                  //*****without other fields*****
        try {
            myJson = jsonArrayReceived.getJSONObject(0);
            actualJSONArray = myJson.getJSONArray("users");
            Log.d("JSON_ARRAY_RECEIVED", actualJSONArray.toString());
            for (int i = 0; i < actualJSONArray.length(); i++) {
                myJson = actualJSONArray.getJSONObject(i);
                user = addJSONToUserObject(myJson);
                userList.add(user);
            }
            groupPageAdapter.notifyItemChanged(POSITION_ON_TOP);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error", "Wrong JSON received");
        }

    }

    private User addJSONToUserObject(JSONObject myJson) throws JSONException {
        String userInfo = myJson.toString();
        User user = UserRelated.getUserObjectFromJson(userInfo);
        return user;
    }
}
