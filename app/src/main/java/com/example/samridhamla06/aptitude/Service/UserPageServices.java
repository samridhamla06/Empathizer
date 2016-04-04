package com.example.samridhamla06.aptitude.Service;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.UserPageView.UserPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.UserPageView.UserPageResponseListener;
import com.example.samridhamla06.aptitude.Modals.User;
import com.example.samridhamla06.aptitude.Views.UserPage;



public class UserPageServices {
    //private final Context userPageContext;
    private final UserPage userPageReference;
    private long userId;
    public final String URL = "http://192.168.2.2:8000/users/";
    private UserPageResponseListener userPageResponseListener;
    private UserPageErrorListener userPageErrorListener;
    private JsonArrayRequest requestToGetUserInfo;
    private RequestQueue requestQueue;

    public UserPageServices(UserPage userPageReference, long userId) {
        this.userPageReference = userPageReference;
        this.userId = userId;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        requestQueue = Volley.newRequestQueue(userPageReference);

    }

  /*  public UserPageServices(Context userPageContext, long userId) {
        this.userPageContext = userPageContext;
        this.userId = userId;
    }*/

    public void retrieveInfoFromServer(){
        initialiseListeners();
        requestToGetUserInfo = new JsonArrayRequest(Request.Method.GET,URL + Long.toString(userId),
                userPageResponseListener,userPageErrorListener);
        requestQueue.add(requestToGetUserInfo);
    }

    private void initialiseListeners() {
        userPageResponseListener = new UserPageResponseListener(userPageReference);
        userPageErrorListener = new UserPageErrorListener();
    }

}
