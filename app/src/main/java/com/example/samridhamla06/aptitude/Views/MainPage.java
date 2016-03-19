package com.example.samridhamla06.aptitude.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.Modals.Community;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Modals.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainPage extends AppCompatActivity {

    public final String LOGIN_URL = "http://192.168.2.3:8000/authLogin";
    public static final String USER_NAME = "USER_NAME";
    private SharedPreferences sharedPreferences;
    private String username;
    private String token;
    private JSONObject myJson;
    private JSONArray myJsonArray;
    private JsonArrayRequest jsonArrayRequestForGroups;
    private JsonArrayRequest jsonArrayRequestForUsers;
    private Response.Listener<JSONArray> jsonArrayListenerForGroups;
    private Response.ErrorListener jsonErrorListenerForGroups;
    private Response.Listener<JSONArray> jsonArrayListenerForUsers;
    private Response.ErrorListener jsonErrorListenerForUsers;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private List<Community> communityList;
    private Community community;
    private Intent intentToGroupPage;
    private List<User> usersList;


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
            initialiseListenersForGroups();
            jsonArrayRequestForGroups = new JsonArrayRequest(Request.Method.POST, LOGIN_URL, myJson, jsonArrayListenerForGroups, jsonErrorListenerForGroups){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(jsonArrayRequestForGroups);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void initialiseListenersForGroups() {
        initialiseResponseListenersForGroups();
        initialiseErrorListenersForGroups();
    }

    private void initialiseErrorListenersForGroups() {
        jsonErrorListenerForGroups = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_VOLLEY", error.toString());
            }
        };
    }

    private void initialiseResponseListenersForGroups() {
        jsonArrayListenerForGroups = new Response.Listener<JSONArray>() {
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
        usersList = new ArrayList<>();
        //communityList.add(new Community("HIV","G02"));
        intentToGroupPage = new Intent(MainPage.this,GroupPage.class);
        arrayAdapter = new ArrayAdapter(MainPage.this, R.layout.support_simple_spinner_dropdown_item, communityList);
        //listView.setAdapter(arrayAdapter);
        addListViewParameters(listView);
        Log.d("token", token);
    }

    private void addListViewParameters(ListView listView) {
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("id selected:", Long.toString(id) + " position Selected: " + Integer.toString(position));
                //retrieveGroupsInfoFromTheServer(id);
                intentToGroupPage.putExtra(GroupPage.GROUP_ID,id);
                startActivity(intentToGroupPage);
            }
        });
    }



}
