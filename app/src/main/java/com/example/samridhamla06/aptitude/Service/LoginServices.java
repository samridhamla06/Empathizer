package com.example.samridhamla06.aptitude.Service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView.LoginButtonErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView.LoginButtonResponseListener;
import com.example.samridhamla06.aptitude.Views.LoginPage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginServices {

    private JsonObjectRequest myJsonRequest;
    private final String URL = LoginPage.URL + "login";
    private RequestQueue loginRequestQueue;
    private LoginButtonResponseListener loginButtonResponseListener;
    private LoginButtonErrorListener loginButtonErrorListener;
    private LoginPage loginPageReference;

    public LoginServices(LoginPage loginPageReference) {
        this.loginPageReference = loginPageReference;
        initialiseLocalVariables();
    }

    public void hitLogInRequest(JSONObject userLoginDetails) {

        Log.d("JSON_SENT", userLoginDetails.toString());
        initialiseListeners();
        myJsonRequest = new JsonObjectRequest(Request.Method.POST, URL, userLoginDetails, loginButtonResponseListener, loginButtonErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        loginRequestQueue.add(myJsonRequest);


    }

    private void initialiseLocalVariables() {
        loginRequestQueue = Volley.newRequestQueue(loginPageReference);
    }

    private void initialiseListeners() {
        loginButtonResponseListener = new LoginButtonResponseListener(loginPageReference);
        loginButtonErrorListener = new LoginButtonErrorListener(loginPageReference);
    }

}
