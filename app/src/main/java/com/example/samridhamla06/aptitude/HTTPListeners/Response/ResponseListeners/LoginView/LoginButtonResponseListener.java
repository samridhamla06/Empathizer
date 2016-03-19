package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Views.MainPage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samridhamla06 on 20/03/16.
 */
public class LoginButtonResponseListener implements Response.Listener<JSONObject> {

    private JSONObject responseServer;
    private String status;
    private String token;
    private Context loginContext;
    private SharedPreferences sharedPreferences;
    private Intent intentToMainPage;


    public LoginButtonResponseListener(Context loginContext) {
        this.loginContext = loginContext;
        sharedPreferences = loginContext.getSharedPreferences("PREFERENCES",Context.MODE_PRIVATE);
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
            Toast.makeText(loginContext, "Couldnt connect to server", Toast.LENGTH_LONG).show();//for user
        }
    }

    private void actOnResponse(JSONObject responseServer) throws JSONException{
        if(refreshSharedPreferences(responseServer))
            logInToMainPage();
    }
    
    private boolean refreshSharedPreferences(JSONObject responseServer) throws JSONException{

        status = responseServer.getString("status");//COULD BE SHIFTED TO ANOTHER FUNCTION
        token = responseServer.getString("token");
        Log.d("STATUS AND TOKEN", status+"  *****   " + token);
        return addTokenToSharedPreferences(status, token);

    }

    private boolean addTokenToSharedPreferences(String status, String token) {
        if(status.equals("Valid") && token != null) {//could move to another func...IMPORTANT NOT ==
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token",token);
            editor.commit();
            return true;
        }else{
            Log.d("tokenInsertion", "failed");
            Toast.makeText(loginContext,"Wrong UserName or Password",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void logInToMainPage() {
        intentToMainPage = new Intent(loginContext, MainPage.class);
        intentToMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TO CALL startActivity from outside Context
        loginContext.startActivity(intentToMainPage);
    }
}
