package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.UserPageView.UserPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.UserPageView.UserPageResponseListener;
import com.example.samridhamla06.aptitude.Views.LoginPage;
import com.example.samridhamla06.aptitude.Views.UserPage;

import java.util.HashMap;
import java.util.Map;


public class UserPageServices {
    //private final Context userPageContext;
    private final UserPage userPageReference;
    private String userId;
    public final String URL = LoginPage.URL + "auth/users/";
    private UserPageResponseListener userPageResponseListener;
    private UserPageErrorListener userPageErrorListener;
    private JsonArrayRequest requestToGetUserInfo;
    private RequestQueue requestQueue;
    private String token;
    private SharedPreferences sharedPreferences;

    public UserPageServices(UserPage userPageReference, String userId) {
        this.userPageReference = userPageReference;
        this.userId = userId;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        requestQueue = Volley.newRequestQueue(userPageReference);
        sharedPreferences = userPageReference.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(LoginPage.TOKEN, "000");

    }

  /*  public UserPageServices(Context userPageContext, long userId) {
        this.userPageContext = userPageContext;
        this.userId = userId;
    }*/

    public void retrieveInfoFromServer() {
        initialiseListeners();
        Log.d("USER_ID_SENT",userId);
        requestToGetUserInfo = new JsonArrayRequest(Request.Method.GET, URL + userId,
                userPageResponseListener, userPageErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("User-agent", System.getProperty("http.agent"));
                headers.put("authorization", token);
                return headers;
            }
        };
        requestQueue.add(requestToGetUserInfo);
    }

    private void initialiseListeners() {
        userPageResponseListener = new UserPageResponseListener(userPageReference);
        userPageErrorListener = new UserPageErrorListener();
    }

}
