package com.example.samridhamla06.aptitude.Service;

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.AddGroupPageView.AddGroupPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.AddGroupPageView.AddGroupPageResponseListener;
import com.example.samridhamla06.aptitude.Views.Activities.AddGroupPage;

public class AddGroupPageServices {

    //CONSTANTS
    private final String ADD_GROUP_URL = Constants.URL + "addGroup";

    //OBJECTS
    private JsonObjectRequest requestToCreateGroupOnServer;
    private RequestQueue requestQueue;
    private AddGroupPage addGroupPageReference;
    private AddGroupPageResponseListener addGroupPageResponseListener;
    private AddGroupPageErrorListener addGroupPageErrorListener;

    public AddGroupPageServices(AddGroupPage addGroupPageReference) {
        this.addGroupPageReference = addGroupPageReference;
        requestQueue = Volley.newRequestQueue(addGroupPageReference);
    }

    public void addGroupToServer(JSONObject groupJSONObject){
        initialiseListeners();
        requestToCreateGroupOnServer = new JsonObjectRequest(Request.Method.POST, ADD_GROUP_URL,
                groupJSONObject,addGroupPageResponseListener,addGroupPageErrorListener);
        requestQueue.add(requestToCreateGroupOnServer);
    }

    private void initialiseListeners() {
        addGroupPageResponseListener = new AddGroupPageResponseListener(addGroupPageReference);
        addGroupPageErrorListener = new AddGroupPageErrorListener();
    }
}
