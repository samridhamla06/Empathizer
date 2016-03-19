package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Modals.Community;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPageResponseListener implements Response.Listener<JSONArray>{

    private final Context mainPageContext;
    private ArrayAdapter adapterForGroups;
    private JSONArray myJsonArray;
    private List<Community> communityList;

    public MainPageResponseListener(Context mainPageContext, ArrayAdapter arrayAdapterForGroups, List<Community> communityList) {
        this.mainPageContext = mainPageContext;
        this.adapterForGroups = arrayAdapterForGroups;
        this.communityList = communityList;
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
        Community community;
        System.out.println("entered addJSONArrayToList");
        try{
            for(int i = 0 ; i < myJsonArray.length();i++ ){
                communityJSONObject = myJsonArray.getJSONObject(i);
                System.out.println("*********** "+ i + " JSON object is " + communityJSONObject.toString());
                community = convertJSONToObject(communityJSONObject);
                System.out.println("*********** " + i + " Community is " + community.toString());
                communityList.add(community);
                System.out.println("************ LIST STATUS  " + communityList.toString());
            }
            adapterForGroups.notifyDataSetChanged();
            System.out.println("ended addJSONArrayToList");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private Community convertJSONToObject(JSONObject communityJSONObject) throws JSONException{
        return new Community(communityJSONObject.getString("name"), "G01");
    }


    }

