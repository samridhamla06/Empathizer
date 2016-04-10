package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
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
    private String name;


    public LoginButtonResponseListener(LoginPage loginPageReference) {
        this.loginPageReference = loginPageReference;
        sharedPreferences = loginPageReference.getSharedPreferences("PREFERENCES",Context.MODE_PRIVATE);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("ReceivedJSONwithToken", response.toString());
        responseServer = response;
        try {
            actOnResponse(responseServer);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("receivedJSON", "JSON NOT IN RIGHT FORMAT");
            Toast.makeText(loginPageReference, "Couldnt connect to server", Toast.LENGTH_LONG).show();//for user
        }
    }

    private void actOnResponse(JSONObject responseServer) throws JSONException{
        if(insertIntoSharedPreferencesIfTokenReceived(responseServer))
            logInToMainPage();//HVE TO ADD ELSE AS RESET CREDENTIALS ..LETS SEE
    }
    
    private boolean insertIntoSharedPreferencesIfTokenReceived(JSONObject responseServer) throws JSONException{
        initialiseVariablesUsingJSONReceived(responseServer);
        Log.d("STATUS AND TOKEN", status+"  ------   " + token);
        if(status.equals("Valid") && token != null){
            addTokenToSharedPreferences(status, token, suffering);
            return true;
        }else{
            return false;
        }
    }

    private void initialiseVariablesUsingJSONReceived(JSONObject responseServer) throws JSONException{
        status = responseServer.getString("status");//COULD BE SHIFTED TO ANOTHER FUNCTION
        token = responseServer.getString("token");
        suffering =responseServer.getString("suffering");
        name =responseServer.getString("name");
    }

    private void addTokenToSharedPreferences(String status, String token,String suffering) {
        if(status.equals("Valid") && token != null) {//could move to another func...IMPORTANT NOT ==
            Log.d("TokenInsertion", "Passed");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token",token);
            editor.putString("suffering",suffering);
            editor.putString("name",name);
            editor.commit();
        }else{
            Log.d("TokenInsertion", "Failed");
            Toast.makeText(loginPageReference,"No Token Received...Wrong UserName or Password",Toast.LENGTH_LONG).show();
        }
    }

    private void logInToMainPage() {
        intentToMainPage = new Intent(loginPageReference, MainPage.class);
        intentToMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TO CALL startActivity from outside Context
        loginPageReference.startActivity(intentToMainPage);
        loginPageReference.finish();
    }
}
