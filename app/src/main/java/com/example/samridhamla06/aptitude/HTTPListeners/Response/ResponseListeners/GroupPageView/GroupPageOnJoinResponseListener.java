package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.GroupPageView;

import android.app.Activity;
import android.widget.Toast;
import com.android.volley.Response;
import com.example.samridhamla06.aptitude.Views.GroupPage;
import com.example.samridhamla06.aptitude.Views.LoginPage;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupPageOnJoinResponseListener implements Response.Listener<JSONObject> {

    private Activity groupPageReference;
    private JSONObject responseReceived;

    public GroupPageOnJoinResponseListener(Activity groupPageReference) {
        this.groupPageReference = groupPageReference;
    }

    @Override
    public void onResponse(JSONObject response) {
        responseReceived = response;
        try {
            actOnResponse();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateResponse() throws JSONException{
        return responseReceived.getString(LoginPage.STATUS).equals(LoginPage.VALID);
    }

    private void refreshGroupPage(Activity groupPageReference){
        groupPageReference.startActivity(groupPageReference.getIntent());
        groupPageReference.finish();
    }

    private void actOnResponse() throws JSONException {
        if (validateResponse()){
            Toast.makeText(groupPageReference, "You have joined the group", Toast.LENGTH_LONG).show();
            refreshGroupPage(groupPageReference);
        }else{
            Toast.makeText(groupPageReference, "Couldnt join the group..try again", Toast.LENGTH_LONG).show();
        }
    }
}
