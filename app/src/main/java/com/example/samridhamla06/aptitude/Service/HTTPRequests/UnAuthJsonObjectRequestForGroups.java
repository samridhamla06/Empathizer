package com.example.samridhamla06.aptitude.Service.HTTPRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class UnAuthJsonObjectRequestForGroups extends JsonObjectRequest {
    public UnAuthJsonObjectRequestForGroups(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
