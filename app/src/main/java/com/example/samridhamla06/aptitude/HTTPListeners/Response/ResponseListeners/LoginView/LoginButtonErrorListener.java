package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public class LoginButtonErrorListener implements ErrorListener {
    private final Context loginContext;

    public LoginButtonErrorListener(Context loginContext) {
        this.loginContext = loginContext;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error.Response", error.toString());
    }
}
