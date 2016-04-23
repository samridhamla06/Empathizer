package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView.MainPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView.MainPageResponseListener;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.Service.HTTPRequests.AuthJsonObjectRequestForGroups;
import com.example.samridhamla06.aptitude.Service.HTTPRequests.UnAuthJsonObjectRequestForGroups;
import com.example.samridhamla06.aptitude.Views.LoginPage;
import java.util.List;


public class MainPageServices {


    private final Context mainPageContext;
    private ArrayAdapter arrayAdapterForGroups;
    public final String AUTH_READ_GROUP_URL = LoginPage.URL + "auth/readGroups/";
    public final String UNAUTH_READ_GROUP_URL = LoginPage.URL + "readGroups/";
    private SharedPreferences sharedPreferences;
    private String token;
    private JsonObjectRequest jsonObjectRequest;
    private MainPageResponseListener mainPageResponseListener;
    private MainPageErrorListener mainPageErrorListener;
    private RequestQueue mainPageRequestQueue;
    private List<Group> groupList;
    private String sufferingName;

    public MainPageServices(Context mainPageContext, ArrayAdapter arrayAdapterForGroups, List<Group> groupList) {
        this.mainPageContext = mainPageContext;
        this.arrayAdapterForGroups = arrayAdapterForGroups;
        this.groupList = groupList;
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        sharedPreferences = mainPageContext.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        mainPageRequestQueue = Volley.newRequestQueue(mainPageContext);
        sufferingName = sharedPreferences.getString(LoginPage.SUFFERING_NAME, "Stammer");
    }

    public void getGroupsFromTheServer() {

        try {
            initializeTokenVariable();
            initialiseListenersForGroups();
            jsonObjectRequest = new AuthJsonObjectRequestForGroups(Request.Method.GET, AUTH_READ_GROUP_URL + sufferingName, mainPageResponseListener, mainPageErrorListener, token);
        } catch (NullPointerException e) {//------------WHEN TOKEN IS NULL IS SHARED-PREFERENCES..SEND UNAUTHREQUEST
            Toast.makeText(mainPageContext, "Loggin In As GUEST", Toast.LENGTH_LONG).show();
            //e.printStackTrace();
            jsonObjectRequest = new UnAuthJsonObjectRequestForGroups(Request.Method.GET, UNAUTH_READ_GROUP_URL + sufferingName, mainPageResponseListener, mainPageErrorListener);
        }
        mainPageRequestQueue.add(jsonObjectRequest);
    }

    private void initializeTokenVariable() throws NullPointerException {
        token = sharedPreferences.getString(LoginPage.TOKEN, null);
        if (token == null) {
            throw new NullPointerException();//IMPORTANT
        }
    }

    private void initialiseListenersForGroups() {
        mainPageResponseListener = new MainPageResponseListener(mainPageContext, arrayAdapterForGroups, groupList);
        mainPageErrorListener = new MainPageErrorListener();
    }


}
