package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.RegisterPageView;

import android.util.Log;
import android.widget.Toast;
import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Views.Activities.LoginPage;
import com.example.samridhamla06.aptitude.Views.RegisterPage;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPageResponseListener implements Response.Listener<JSONObject>{

    private JSONObject responseReceived;
    private RegisterPage registerPageReference;


    public RegisterPageResponseListener(RegisterPage registerPageReference) {
        this.registerPageReference = registerPageReference;
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.d("REGISTER_JSON_RECEIVED",response.toString());
        responseReceived = response;
        try {
            actOnResponse(responseReceived);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void actOnResponse(JSONObject responseReceived) throws JSONException {
        if(responseReceived.getString("status").equals(LoginPage.VALID)){
            Toast.makeText(registerPageReference, "You are Registered", Toast.LENGTH_LONG).show();
            goToLogInPage();
            registerPageReference.finish();
        }else{          //have to add for existing username
            Toast.makeText(registerPageReference, "Please re-enter your Details", Toast.LENGTH_LONG).show();
            registerPageReference.resetAllViews();
        }
    }

    private void goToLogInPage() {
        registerPageReference.goToLogInPage();
    }
}
