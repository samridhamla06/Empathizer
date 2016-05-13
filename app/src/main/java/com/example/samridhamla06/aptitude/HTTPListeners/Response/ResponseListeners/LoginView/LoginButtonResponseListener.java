package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;
import com.example.samridhamla06.aptitude.Views.Activities.LoginPage;
import com.example.samridhamla06.aptitude.Views.Activities.MainPage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginButtonResponseListener implements Response.Listener<JSONObject> {

    private JSONObject responseReceived;
    private LoginPage loginPageReference;
    private String status;
    private String token;
    private Intent intentToMainPage;
    private SharedPreferences sharedPreferences;



    public LoginButtonResponseListener(LoginPage loginPageReference) {
        this.loginPageReference = loginPageReference;
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(this.loginPageReference);

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("ReceivedJSONwithToken", response.toString());
        responseReceived = response;
        try {
            actOnResponse();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("receivedJSON", "JSON NOT IN RIGHT FORMAT");
            dismissProgressBar();
            Toast.makeText(loginPageReference, "JSON received not in right format", Toast.LENGTH_LONG).show();//for user
        }
    }

    private void actOnResponse() throws JSONException {
        try {
            extractStatusAndToken();
            if (validateStatusAndToken()) {
                removeStatusAndTokenFromResponseReceived();
                addResponseReceivedToSharedPreferences();//could be done by another thread..if u want
                dismissProgressBar();
                logInToMainPage();
            } else
                dismissProgressBar();
                Toast.makeText(loginPageReference, "Invalid UserName or Password", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dismissProgressBar() {
        loginPageReference.dismissProgressBar();
    }


    private void removeStatusAndTokenFromResponseReceived() {
        responseReceived.remove(Constants.TOKEN);
        responseReceived.remove(Constants.STATUS);
    }


    private void addResponseReceivedToSharedPreferences() {
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(loginPageReference);
        Log.d("TokenInsertion", "Passed");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.putString(Constants.USER_INFO, stringifyResponseReceived());
        editor.commit();
    }
    private void logInToMainPage() {
        intentToMainPage = new Intent(loginPageReference, MainPage.class);
        intentToMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TO CALL startActivity from outside Context
        loginPageReference.startActivity(intentToMainPage);
        loginPageReference.finish();
    }

    private String stringifyResponseReceived() {
        return responseReceived.toString();
    }

    private void extractStatusAndToken() throws JSONException {
        status = responseReceived.getString(Constants.STATUS);
        token = responseReceived.getString(Constants.TOKEN);
    }

    private boolean validateStatusAndToken() {
        return status.equals(Constants.VALID) && token != null;
    }
}
