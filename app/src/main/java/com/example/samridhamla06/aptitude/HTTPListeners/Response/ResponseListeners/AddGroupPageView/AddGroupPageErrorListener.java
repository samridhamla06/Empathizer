package com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.AddGroupPageView;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by samridhamla06 on 12/04/16.
 */
public class AddGroupPageErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Log.d("VOLLEY_ERROR","Taking lods of time TO ADD GROUP");
    }
}
