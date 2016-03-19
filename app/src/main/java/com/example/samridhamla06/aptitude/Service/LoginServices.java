package com.example.samridhamla06.aptitude.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView.LoginButtonErrorListener;
import com.example.samridhamla06.aptitude.HTTPListeners.Response.ResponseListeners.LoginView.LoginButtonResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samridhamla06 on 20/03/16.
 */
public class LoginServices {

    private  JSONObject myJson;
    private JsonObjectRequest myJsonRequest;
    public final String URL = "http://192.168.2.3:8000/login";
    private final Context loginContext; //IMPORTANT
    private RequestQueue loginRequestQueue;
    private LoginButtonResponseListener loginButtonResponseListener;
    private LoginButtonErrorListener loginButtonErrorListener;


    public LoginServices(Context context) {
        this.loginContext = context;
        initialiseLocalVariables();
    }


    public void hitLogInRequest(String username_android, String password_android) {
        try {
            prepareJSONObjectToSend(username_android, password_android);
            Log.d("JSON_SENT", myJson.toString());
            initialiseListeners();
            myJsonRequest = new JsonObjectRequest(Request.Method.POST, URL, myJson,loginButtonResponseListener, loginButtonErrorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }
            };
            loginRequestQueue.add(myJsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("status", "JSON exception");
            Toast.makeText(loginContext, "Couldnt connect to server", Toast.LENGTH_LONG).show();//for user
        }

    }

    private void prepareJSONObjectToSend(String username_android,String password_android) throws JSONException{
        myJson.put("username",username_android);
        myJson.put("password", password_android);
    }

    private void initialiseLocalVariables(){
        myJson = new JSONObject();
        loginRequestQueue = Volley.newRequestQueue(loginContext);
    }

    private void initialiseListeners() {
        loginButtonResponseListener = new LoginButtonResponseListener(loginContext);
        loginButtonErrorListener = new LoginButtonErrorListener(loginContext);
    }

}
