package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView.LoginButtonErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView.LoginButtonResponseListener;
import com.example.samridhamla06.aptitude.Modals.Community;
import com.example.samridhamla06.aptitude.Views.LoginPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginServices {

    private  JSONObject myJson;
    private JsonObjectRequest myJsonRequest;
    private final String URL = LoginPage.URL+"login";
    private RequestQueue loginRequestQueue;
    private LoginButtonResponseListener loginButtonResponseListener;
    private LoginButtonErrorListener loginButtonErrorListener;
    public static List<Community> communityList;
    private final LoginPage loginPageReference;

    public LoginServices(LoginPage loginPageReference) {
        this.loginPageReference = loginPageReference;
        initialiseLocalVariables();
    }

    private void instantiateCommunityList() {
        communityList = new ArrayList<>();
        communityList.add(new Community("Stammerers"));
        communityList.add(new Community("OCD"));
        communityList.add(new Community("Insomnia"));
    }


    public void hitLogInRequest(String username_android, String password_android) {
        try {
            prepareJSONObjectToSend(username_android, password_android);
            Log.d("JSON_SENT", myJson.toString());
            initialiseListeners();
            myJsonRequest = new JsonObjectRequest(Request.Method.POST, URL, myJson,loginButtonResponseListener, loginButtonErrorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }
            };
            loginRequestQueue.add(myJsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("status", "JSON exception");
            Toast.makeText(loginPageReference, "Couldnt connect to server", Toast.LENGTH_LONG).show();//for user
        }

    }

    private void prepareJSONObjectToSend(String username_android,String password_android) throws JSONException{
        myJson.put("username",username_android);
        myJson.put("password", password_android);
    }

    private void initialiseLocalVariables(){
        myJson = new JSONObject();
        loginRequestQueue = Volley.newRequestQueue(loginPageReference);
    }

    private void initialiseListeners() {
        loginButtonResponseListener = new LoginButtonResponseListener(loginPageReference);
        loginButtonErrorListener = new LoginButtonErrorListener(loginPageReference);
    }

}
