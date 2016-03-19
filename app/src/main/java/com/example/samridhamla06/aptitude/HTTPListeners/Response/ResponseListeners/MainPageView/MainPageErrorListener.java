package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.MainPageView;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by samridhamla06 on 20/03/16.
 */
public class MainPageErrorListener implements Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("ERROR_VOLLEY", error.toString());
    }
}
