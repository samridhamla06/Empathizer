package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.AddGroupPageView;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Views.Activities.AddGroupPage;
import com.example.samridhamla06.aptitude.Views.Activities.MainPage;

import org.json.JSONException;
import org.json.JSONObject;

public class AddGroupPageResponseListener implements Response.Listener<JSONObject> {
    private final AddGroupPage addGroupPageReference;
    private JSONObject responseReceived;
    private Intent intentToMainPage;


    public AddGroupPageResponseListener(AddGroupPage addGroupPageReference) {
        this.addGroupPageReference = addGroupPageReference;
    }

    @Override
    public void onResponse(JSONObject response) {
        responseReceived = response;
        Log.d("RESPONSE_ADD_GROUP",responseReceived.toString());
        try {
            actOnResponse();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateResponse() throws JSONException{
        return responseReceived.getString(Constants.STATUS).equals(Constants.VALID);
    }
    private void actOnResponse() throws JSONException {
        if(validateResponse()){
            Toast.makeText(addGroupPageReference,"Group Successfully Created",Toast.LENGTH_LONG).show();
            intentToMainPage = new Intent(addGroupPageReference, MainPage.class);
            addGroupPageReference.onBackPressed();//TO GET BACK TO GroupPage
        }else{
            Toast.makeText(addGroupPageReference,"Couldnt add the group..Try again",Toast.LENGTH_LONG).show();
            //CAN RESET VALUES for editViews
        }
    }
}
