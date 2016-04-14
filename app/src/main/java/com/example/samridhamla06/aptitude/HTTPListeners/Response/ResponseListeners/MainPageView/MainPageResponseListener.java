package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Modals.Group;
import com.example.samridhamla06.aptitude.Views.LoginPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainPageResponseListener implements Response.Listener<JSONObject> {

    private final Context mainPageContext;
    private ArrayAdapter adapterForGroups;
    private JSONObject groupJSONReceived;
    private List<Group> groupList;
    private final String GROUPS = "groups";

    public MainPageResponseListener(Context mainPageContext, ArrayAdapter arrayAdapterForGroups, List<Group> groupList) {
        this.mainPageContext = mainPageContext;
        this.adapterForGroups = arrayAdapterForGroups;
        this.groupList = groupList;
    }

    @Override
    public void onResponse(JSONObject response) {
        groupJSONReceived = response;
        Log.d("RECEIVED_JSON_ARRAY", groupJSONReceived.toString());
        actOnResponse(groupJSONReceived);
    }

    private void actOnResponse(JSONObject groupJSONObjectReceived) {
        try {
            addJSONArrayToList(groupJSONObjectReceived.getJSONArray(GROUPS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void addJSONArrayToList(JSONArray myJsonArray) throws JSONException {
        JSONObject groupJSONObject;
        Group group;
        System.out.println("entered addJSONArrayToList");

        for (int i = 0; i < myJsonArray.length(); i++) {
            groupJSONObject = myJsonArray.getJSONObject(i);
            group = convertJSONToObject(groupJSONObject);
            groupList.add(group);
        }
        adapterForGroups.notifyDataSetChanged();
        System.out.println("ended addJSONArrayToList");

    }

    private Group convertJSONToObject(JSONObject communityJSONObject) throws JSONException {
        return new Group(communityJSONObject.getString(LoginPage.GROUP_NAME), communityJSONObject.getString("_id"));
    }
}

