package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Views.LoginPage;
import com.example.samridhamla06.aptitude.Views.MainPage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginButtonResponseListener implements Response.Listener<JSONObject> {

    private JSONObject responseServer;
    private String status;
    private String token;
    private LoginPage loginPageReference;
    private SharedPreferences sharedPreferences;
    private Intent intentToMainPage;
    private String suffering;
    private String userName;
    private String userId;
    private String email;
    private String location;


    public LoginButtonResponseListener(LoginPage loginPageReference) {
        this.loginPageReference = loginPageReference;
        sharedPreferences = loginPageReference.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
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
            Toast.makeText(loginPageReference, "Couldnt connect to server", Toast.LENGTH_LONG).show();//for user
        }
    }

    private void actOnResponse() throws JSONException {
        if (insertIntoSharedPreferencesIfTokenReceived())
            logInToMainPage();//HAVE TO ADD ELSE AS RESET CREDENTIALS ..LETS SEE
        else {
            Toast.makeText(loginPageReference, "Invalid USERNAME OR PASSWORD", Toast.LENGTH_LONG).show();
        }
    }

    private boolean insertIntoSharedPreferencesIfTokenReceived() throws JSONException {
        extractStatusAndToken();//--THESE TWO ARE MAPPED FIRST AS IF STATUS = 'INVALID',
        // OTHER WONT BE RECEIVED
        Log.d("STATUS AND TOKEN", status + "  ------   " + token);
        if (validateStatusAndToken()) {
            addTokenAndSufferingToSharedPreferences();
            validateOtherFieldsReceivedAndAddToSharedPreferences();
            return true;
        } else {
            return false;
        }
    }

    private boolean validateStatusAndToken() {
        return status.equals("Valid") && token != null;
    }

    private void extractStatusAndToken() throws JSONException {
        status = responseServer.getString(LoginPage.STATUS);//COULD BE SHIFTED TO ANOTHER FUNCTION
        token = responseServer.getString(LoginPage.TOKEN);
    }


    private void validateOtherFieldsReceivedAndAddToSharedPreferences() throws JSONException {

        try {
            userName = responseServer.getString(LoginPage.USER_NAME);
            userId = responseServer.getString(LoginPage.USER_ID);
            email = responseServer.getString(LoginPage.EMAIL);
            location = responseServer.getString(LoginPage.LOCATION);
            addOtherFieldsToSharedPreferences();
        } catch(NullPointerException e) {
            e.printStackTrace();
            Log.d("LOGIN_ERROR","NULL_FIELD_RECEIVED_VIA_LOGIN");
        }
    }

    private void addTokenAndSufferingToSharedPreferences() throws JSONException{
            Log.d("TokenInsertion", "Passed");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            extractSuffering();
            editor.putString(LoginPage.TOKEN, token);
            editor.commit();
    }

    private void extractSuffering() throws JSONException{
        suffering = responseServer.getString(LoginPage.SUFFERING_NAME);
    }

    private void addOtherFieldsToSharedPreferences() {
            Log.d("TokenInsertion", "Passed");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LoginPage.SUFFERING_NAME, suffering);
            editor.putString(LoginPage.USER_NAME, userName);
            editor.putString(LoginPage.USER_ID, userId);
            editor.putString(LoginPage.EMAIL, email);
            editor.putString(LoginPage.LOCATION, location);
            editor.commit();
        }

    private void logInToMainPage() {
        intentToMainPage = new Intent(loginPageReference, MainPage.class);
        intentToMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TO CALL startActivity from outside Context
        loginPageReference.startActivity(intentToMainPage);
        loginPageReference.finish();
    }
}
