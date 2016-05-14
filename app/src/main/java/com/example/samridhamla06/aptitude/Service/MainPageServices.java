package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.Adapters.MainPageRecyclerViewAdapter;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView.MainPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView.MainPageResponseListener;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.Service.HTTPRequests.AuthJsonObjectRequestForGroups;
import com.example.samridhamla06.aptitude.Service.HTTPRequests.UnAuthJsonObjectRequestForGroups;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;
import com.example.samridhamla06.aptitude.Utility.UserRelated;

import java.util.List;


public class MainPageServices {


    private final Context mainPageContext;
    private MainPageRecyclerViewAdapter adapterForGroups;
    public final String AUTH_READ_GROUP_URL = Constants.URL + "auth/readGroups/";
    public final String UNAUTH_READ_GROUP_URL = Constants.URL + "readGroups/";
    private SharedPreferences sharedPreferences;
    private String token;
    private JsonObjectRequest jsonObjectRequest;
    private MainPageResponseListener mainPageResponseListener;
    private MainPageErrorListener mainPageErrorListener;
    private RequestQueue mainPageRequestQueue;
    private List<Group> groupList;
    private String sufferingName;


    public MainPageServices(Context mainPageContext, MainPageRecyclerViewAdapter arrayAdapterForGroups, List<Group> groupList) {
        this.mainPageContext = mainPageContext;
        this.adapterForGroups = arrayAdapterForGroups;
        this.groupList = groupList;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(mainPageContext);
        mainPageRequestQueue = Volley.newRequestQueue(mainPageContext);
        sufferingName = getSufferingNameFromSharedPreferences();
    }

    private String getSufferingNameFromSharedPreferences() {
        return UserRelated.getUserPOJOForCurrentUser(sharedPreferences).getSufferingName();
    }

    public void getGroupsFromTheServer() {

        try {
            initializeTokenVariable();
            initialiseListenersForGroups();
            String userId = UserRelated.getUserIdForCurrentUser(sharedPreferences);
            jsonObjectRequest = new AuthJsonObjectRequestForGroups(Request.Method.GET, AUTH_READ_GROUP_URL + sufferingName + "/" +userId,
                    mainPageResponseListener, mainPageErrorListener, token);
        } catch (NullPointerException e) {//------------WHEN TOKEN IS NULL In SHARED-PREFERENCES..SEND UNAUTHREQUEST
            Toast.makeText(mainPageContext, "Loggin In As GUEST", Toast.LENGTH_LONG).show();
            jsonObjectRequest = new UnAuthJsonObjectRequestForGroups(Request.Method.GET, UNAUTH_READ_GROUP_URL + sufferingName, mainPageResponseListener, mainPageErrorListener);
        }
        mainPageRequestQueue.add(jsonObjectRequest);
    }

    private void initializeTokenVariable() throws NullPointerException {
        token = sharedPreferences.getString(Constants.TOKEN, null);
        if (token == null) {
            throw new NullPointerException();//IMPORTANT
        }
    }

    private void initialiseListenersForGroups() {
        mainPageResponseListener = new MainPageResponseListener(mainPageContext, adapterForGroups, groupList);
        mainPageErrorListener = new MainPageErrorListener();
    }


}
