package com.example.samridhamla06.aptitude.Service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.RegisterPageView.RegisterPageErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.RegisterPageView.RegisterPageResponseListener;
import com.example.samridhamla06.aptitude.Views.Activities.RegisterPage;

import org.json.JSONObject;

public class RegisterPageServices {

    //CONSTANTS----------
    private final RegisterPage registerPageReference;
    private final String REGISTER_URL = Constants.URL + "register";

    //OTHER OBJECTS--------
    private JsonObjectRequest requestToregister;
    private RegisterPageResponseListener registerPageResponseListener;
    private RegisterPageErrorListener registerPageErrorListener;
    private RequestQueue requestQueue;

    public RegisterPageServices(RegisterPage registerPageReference) {
        this.registerPageReference = registerPageReference;
        initializeLocalVariables();
    }

    private void initializeLocalVariables() {
        requestQueue = Volley.newRequestQueue(registerPageReference);
    }

    public void sendUserDataToServer(String userInfo) {
        initialiseListeners();
        requestToregister = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, userInfo,registerPageResponseListener, registerPageErrorListener);
        requestQueue.add(requestToregister);
    }

    private void initialiseListeners() {
        registerPageResponseListener = new RegisterPageResponseListener(registerPageReference);
        registerPageErrorListener = new RegisterPageErrorListener();
    }
}
