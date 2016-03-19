package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView.GroupPageResponseListener;
import com.example.samridhamla06.aptitude.Modals.User;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by samridhamla06 on 20/03/16.
 */
public class GroupPageServices {

    private final long id;
    private  ArrayAdapter adapterForUsers;
    private  List<User> userList;
    private JsonArrayRequest userJSONRequest;
    public final String URL_GROUP_DESC = "http://192.168.2.3:8000/groups/";
    private RequestQueue requestQueue;
    private final Context groupPageContext;
    private GroupPageResponseListener groupPageResponseListener;
    private GroupPageErrorListener groupPageErrorListener;

    public GroupPageServices(Context groupPageContext, ArrayAdapter adapterForUsers, List<User> userList, long id) {
        this.groupPageContext = groupPageContext;
        this.adapterForUsers = adapterForUsers;
        this.userList = userList;
        this.id = id;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        requestQueue = Volley.newRequestQueue(groupPageContext);
    }


    public void getUsersForParticularGroup() {
        initialiseListenersForUsers();
        userJSONRequest = new JsonArrayRequest(Request.Method.GET, URL_GROUP_DESC + Long.toString(id),groupPageResponseListener, groupPageErrorListener);
        requestQueue.add(userJSONRequest);
    }
    private void initialiseListenersForUsers() {
        groupPageResponseListener = new GroupPageResponseListener(groupPageContext,adapterForUsers,userList);
        groupPageErrorListener = new GroupPageErrorListener();

    }
}
