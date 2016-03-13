package com.example.samridhamla06.aptitude;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    public final String URL = "http://192.168.2.3:8000/login";
    private  EditText userName;
    private  EditText password;
    private Context context;
    private Intent intentToMainPage;
    private Button button;
    private JSONObject myJson;
    private JsonObjectRequest myJsonRequest;
    private Response.Listener<JSONObject> jsonObjectListener;
    private Response.ErrorListener jsonErrorListener;
    private String token;
    private String status;
    private SharedPreferences sharedPreferences;//SHOULD ONLY BE CALLED BY AN ACTIVITY WHOSE ONCREATE HAS BEEN CALLED
    private String username_android;
    private String password_android;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        instantiateLocalVariables();
    }

    private void instantiateLocalVariables() {
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        context = getApplicationContext();
        intentToMainPage = new Intent(context,MainPage.class);
        button = (Button)findViewById(R.id.signIn);
        sharedPreferences = context.getSharedPreferences("PREFERENCES",MODE_PRIVATE);//IMP STEP
    }

    public void onLogIn(View view){

         username_android = userName.getText().toString();//CREATE A FUNCTION FOR THIS
         password_android = password.getText().toString();
         validCredentialsAndLogIn(username_android, password_android);
    }



    private void validCredentialsAndLogIn(String username_android, String password_android) {
        //http call
        myJson = new JSONObject();
        try {
            myJson.put("username",username_android);
            myJson.put("password", password_android);
            Log.d("JSON_SENT", myJson.toString());
            initialiseListeners();
            myJsonRequest = new JsonObjectRequest(Request.Method.POST, URL, myJson,jsonObjectListener, jsonErrorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(myJsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("status", "JSON exception");
            Toast.makeText(context,"Couldnt connect to server",Toast.LENGTH_LONG).show();//for user
        }

    }

    private void initialiseListeners() {
        initialiseResponseListeners();
        initialiseErrorListeners();
    }

    private void initialiseErrorListeners() {
        jsonErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        };
    }

    private void initialiseResponseListeners()  {
           jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ReceivedJSONwithToken",response.toString());
                JSONObject responseServer = (JSONObject)response;
                try {
                    status = responseServer.getString("status");//COULD BE SHIFTED TO ANOTHER FUNCTION
                    token = responseServer.getString("token");
                    Log.d("STATUS AND TOKEN", status+"  *****   " + token);
                    addToSharedPreferences(status,token);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("receivedJSON", "JSON NOT IN RIGHT FORMAT");
                    Toast.makeText(context,"Couldnt connect to server",Toast.LENGTH_LONG).show();//for user
                }
            }
        };
    }

    private void addToSharedPreferences(String status, String token) {
        if(status.equals("Valid") && token != null) {//could move to another func...IMPORTANT NOT ==
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token",token);
            editor.commit();
            logInToMainPage();
        }else{
            Log.d("tokenInsertion", "failed");
            Toast.makeText(context,"Wrong UserName or Password",Toast.LENGTH_LONG).show();
        }
    }

    private void logInToMainPage() {
        intentToMainPage.putExtra(MainPage.USER_NAME, username_android);
        //intentToMainPage.putExtra(MainPage.LOG_IN_PAGE_CONTEXT,context);
        startActivity(intentToMainPage);
    }
}
