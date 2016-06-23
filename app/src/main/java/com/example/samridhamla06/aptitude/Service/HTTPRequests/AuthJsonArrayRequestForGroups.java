package com.example.samridhamla06.aptitude.Service.HTTPRequests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthJsonArrayRequestForGroups extends JsonArrayRequest {
    private String token;
    public AuthJsonArrayRequestForGroups(int method, String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener, String token) {
        super(method, url, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("User-agent", System.getProperty("http.agent"));
        headers.put("authorization", token);
        return headers;
    }
}
