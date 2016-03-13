package com.example.samridhamla06.aptitude;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainPage extends AppCompatActivity {

    public final String URL = "http://192.168.2.3:8000/authLogin";
    public static final String USER_NAME = "USER_NAME";
    private SharedPreferences sharedPreferences;
    private String username;
    private String token;
    private JSONObject myJson;
    private JSONArray myJsonArray;
    private JsonArrayRequest jsonArrayRequest;
    private Response.Listener<JSONArray> jsonArrayListener;
    private Response.ErrorListener jsonErrorListener;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private List<Community> communityList;
    private Community community;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initialiseLocalVariables();
        retrieveGroupsFromTheServer();

    }



    private void retrieveGroupsFromTheServer() {
        try {
            myJson.put("username", username);
            myJson.put("token", token);
            Log.d("JSON_SENT", myJson.toString());
            initialiseListeners();
            jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, myJson,jsonArrayListener, jsonErrorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(jsonArrayRequest);

        }catch (JSONException e){
            e.printStackTrace();
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
                Log.d("ERROR_VOLLEY", error.toString());
            }
        };
    }

    private void initialiseResponseListeners() {
        jsonArrayListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                myJsonArray = response;
                Log.d("RECEIVED_JSON_ARRAY", myJsonArray.toString());
                MainPage.this.runOnUiThread(new Runnable() {//
                    @Override
                    public void run() {
                        addJSONArrayToList(myJsonArray);
                    }
                });
            }
        };
    }


    private void addJSONArrayToList(JSONArray myJsonArray) {
        JSONObject communityJSONObject;
        Community community;
        System.out.println("entered addJSONArrayToList");
        try{
            for(int i = 0 ; i < myJsonArray.length();i++ ){
                communityJSONObject = myJsonArray.getJSONObject(i);
                System.out.println("*********** "+ i + " JSON object is " + communityJSONObject.toString());
                community = convertJSONToObject(communityJSONObject);
                System.out.println("*********** " + i + " Community is " + community.toString());
                communityList.add(community);
                System.out.println("************ LIST STATUS  " + communityList.toString());
                //VERY IMPORTANT
                arrayAdapter.notifyDataSetChanged();
            }
            System.out.println("ended addJSONArrayToList");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private Community convertJSONToObject(JSONObject communityJSONObject) throws JSONException{
        return new Community(communityJSONObject.getString("name"), "G01");
    }

    private void initialiseLocalVariables() {
        username = getIntent().getStringExtra(USER_NAME);
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "000");
        myJson = new JSONObject();
        listView = (ListView)findViewById(R.id.groups);
        communityList = new ArrayList<>();
        communityList.add(new Community("HIV","G02"));
        arrayAdapter = new ArrayAdapter(MainPage.this, R.layout.support_simple_spinner_dropdown_item, communityList);
        listView.setAdapter(arrayAdapter);
        Log.d("token",token);
    }

}
