package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView.MainPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView.MainPageResponseListener;
import com.example.samridhamla06.aptitude.Modals.Group;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainPageServices {


    private final Context mainPageContext;
    private ArrayAdapter arrayAdapterForGroups;
    public final String LOGIN_URL = "http://192.168.2.2:8000/readGroups/";
    private SharedPreferences sharedPreferences;
    private String token;
    private JSONObject myJson;
    private JsonObjectRequest jsonObjectRequestForGroups;
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
        myJson = new JSONObject();
        mainPageRequestQueue = Volley.newRequestQueue(mainPageContext);
        token = sharedPreferences.getString("token", "000");
        sufferingName = sharedPreferences.getString("suffering", "Stammer");
    }

    public void getGroupsFromTheServer() {

        try {
            prepareJSONObjectToSend(token);
            Log.d("JSON_SENT", myJson.toString());
            initialiseListenersForGroups();
            jsonObjectRequestForGroups = new JsonObjectRequest(Request.Method.GET, LOGIN_URL + sufferingName, mainPageResponseListener, mainPageErrorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    headers.put("authorization", token);
                    return headers;
                }
            };
            mainPageRequestQueue.add(jsonObjectRequestForGroups);
        } catch (JSONException e) {
            Toast.makeText(mainPageContext, "Connection gone suddenly", Toast.LENGTH_LONG).show();//when after login connection goes away
            e.printStackTrace();
        }

    }

    private void prepareJSONObjectToSend(String token) throws JSONException {
        myJson.put("token", token);
    }

    private void initialiseListenersForGroups() {
        mainPageResponseListener = new MainPageResponseListener(mainPageContext, arrayAdapterForGroups, groupList);
        mainPageErrorListener = new MainPageErrorListener();
    }


}
