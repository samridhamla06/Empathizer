package com.example.samridhamla06.aptitude.Service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.Adapters.GroupPageRecyclerViewAdapter;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageOnJoinResponseListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageResponseListener;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupPageServices {

    private final String groupId;
    private GroupPageRecyclerViewAdapter adapterForUsers;
    private List<User> userList;
    private JsonArrayRequest requestToGetGroupInfo;
    private final String URL_GROUP_DESC = Constants.URL + "auth/groups/";
    private final String URL_JOIN_GROUP = Constants.URL + "joinGroup/";
    private RequestQueue requestQueue;
    private final Activity groupPageReference;
    private GroupPageResponseListener groupPageResponseListener;
    private GroupPageErrorListener groupPageErrorListener;
    private String token;
    private SharedPreferences sharedPreferences;
    private JsonObjectRequest requestToJoinGroup;
    private GroupPageOnJoinResponseListener groupPageOnJoinResponseListener;


    public GroupPageServices(Activity groupPageReference, GroupPageRecyclerViewAdapter adapterForUsers, List<User> userList, String id) {
        this.groupPageReference = groupPageReference;
        this.adapterForUsers = adapterForUsers;
        this.userList = userList;
        this.groupId = id;
        initialiseLocalVariables();
    }  //---------------------------------------------------------------FOR ALL_MEMBERS_REQUEST

    public GroupPageServices(Activity groupPageReference, String id) { //FOR JOIN GROUP,INVITE GROUP STUFF
        this.groupPageReference = groupPageReference;
        this.groupId = id;
        initialiseLocalVariables();
    }


    private void initialiseLocalVariables() {
        requestQueue = Volley.newRequestQueue(groupPageReference);
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(groupPageReference);
        token = sharedPreferences.getString("token", "000");
    }


    public void getUsersForParticularGroup() {
        initialiseListenersForUsers();
        Log.d("GROUP_ID_SENT", groupId);
        requestToGetGroupInfo = new JsonArrayRequest(Request.Method.GET, URL_GROUP_DESC + groupId, groupPageResponseListener, groupPageErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("User-agent", System.getProperty("http.agent"));
                headers.put("authorization", token);
                return headers;
            }
        };
        requestQueue.add(requestToGetGroupInfo);
    }


    private void initialiseListenersForUsers() {
        groupPageResponseListener = new GroupPageResponseListener(groupPageReference, adapterForUsers, userList);
        groupPageErrorListener = new GroupPageErrorListener();
    }

    public void sendRequestToJoinGroup(String userJsonObject) {
        initialiseListenersToJoinGroup();
        requestToJoinGroup = new JsonObjectRequest(Request.Method.POST, URL_JOIN_GROUP + groupId, userJsonObject, groupPageOnJoinResponseListener, groupPageErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("User-agent", System.getProperty("http.agent"));
                headers.put("authorization", token);
                return headers;
            }
        };
        requestQueue.add(requestToJoinGroup);
    }

    private void initialiseListenersToJoinGroup() {
        groupPageOnJoinResponseListener = new GroupPageOnJoinResponseListener(groupPageReference);
    }
}
