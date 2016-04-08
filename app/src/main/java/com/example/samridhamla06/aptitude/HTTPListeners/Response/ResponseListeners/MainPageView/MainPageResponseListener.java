package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Modals.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class MainPageResponseListener implements Response.Listener<JSONArray>{

    private final Context mainPageContext;
    private ArrayAdapter adapterForGroups;
    private JSONArray myJsonArray;
    private List<Group> groupList;

    public MainPageResponseListener(Context mainPageContext, ArrayAdapter arrayAdapterForGroups, List<Group> groupList) {
        this.mainPageContext = mainPageContext;
        this.adapterForGroups = arrayAdapterForGroups;
        this.groupList = groupList;
    }

    @Override
    public void onResponse(JSONArray response) {
        myJsonArray = response;
        Log.d("RECEIVED_JSON_ARRAY", myJsonArray.toString());
        actOnResponse(myJsonArray);
    }

    private void actOnResponse(JSONArray myJsonArray) {
        addJSONArrayToList(myJsonArray);
    }


    private void addJSONArrayToList(JSONArray myJsonArray) {
        JSONObject communityJSONObject;
        Group group;
        System.out.println("entered addJSONArrayToList");
        try{
            for(int i = 0 ; i < myJsonArray.length();i++ ){
                communityJSONObject = myJsonArray.getJSONObject(i);
                group = convertJSONToObject(communityJSONObject);
                groupList.add(group);
            }
            adapterForGroups.notifyDataSetChanged();
            System.out.println("ended addJSONArrayToList");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private Group convertJSONToObject(JSONObject communityJSONObject) throws JSONException{
        return new Group(communityJSONObject.getString("name"), communityJSONObject.getInt("_id"));
    }
}

