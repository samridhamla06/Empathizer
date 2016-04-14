package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageResponseListener;
import com.example.samridhamla06.aptitude.Modals.User;
import com.example.samridhamla06.aptitude.Views.LoginPage;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupPageServices {

    private final String groupId;
    private  ArrayAdapter adapterForUsers;
    private  List<User> userList;
    private JsonArrayRequest requestToGetGroupInfo;
    public final String URL_GROUP_DESC = LoginPage.URL + "groups/";
    private RequestQueue requestQueue;
    private final Context groupPageContext;
    private GroupPageResponseListener groupPageResponseListener;
    private GroupPageErrorListener groupPageErrorListener;
    private String token;
    private SharedPreferences sharedPreferences;

    public GroupPageServices(Context groupPageContext, ArrayAdapter adapterForUsers, List<User> userList, String id) {
        this.groupPageContext = groupPageContext;
        this.adapterForUsers = adapterForUsers;
        this.userList = userList;
        this.groupId = id;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        requestQueue = Volley.newRequestQueue(groupPageContext);
        sharedPreferences = groupPageContext.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "000");
    }


    public void getUsersForParticularGroup() {
        initialiseListenersForUsers();
        Log.d("GROUP_ID_SENT",groupId);
        requestToGetGroupInfo = new JsonArrayRequest(Request.Method.GET, URL_GROUP_DESC + groupId,groupPageResponseListener, groupPageErrorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("User-agent", System.getProperty("http.agent"));
                headers.put("authorization", token);
                return headers;
            }
        };
        requestQueue.add(requestToGetGroupInfo);
    }
    private void initialiseListenersForUsers() {
        groupPageResponseListener = new GroupPageResponseListener(groupPageContext,adapterForUsers,userList);
        groupPageErrorListener = new GroupPageErrorListener();
    }
}
