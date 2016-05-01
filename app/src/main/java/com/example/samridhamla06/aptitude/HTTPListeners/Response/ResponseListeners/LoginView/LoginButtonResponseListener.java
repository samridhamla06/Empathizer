package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Views.Activities.LoginPage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginButtonResponseListener implements Response.Listener<JSONObject> {

    private JSONObject responseServer;
    private LoginPage loginPageReference;



    public LoginButtonResponseListener(LoginPage loginPageReference) {
        this.loginPageReference = loginPageReference;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("ReceivedJSONwithToken", response.toString());
        responseServer = response;
        try {
            actOnResponse();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("receivedJSON", "JSON NOT IN RIGHT FORMAT");
            Toast.makeText(loginPageReference, "JSON received not in right format", Toast.LENGTH_LONG).show();//for user
        }
    }

    private void actOnResponse() throws JSONException {
        loginPageReference.sendResponseReceivedToLoginPage(responseServer);
    }

}
