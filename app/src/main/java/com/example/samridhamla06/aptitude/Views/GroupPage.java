package com.example.samridhamla06.aptitude.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Modals.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupPage extends AppCompatActivity {

    private long id;
    private String token;
    private ListView listView;
    private List<User> userList;
    private ArrayAdapter arrayAdapter;
    private JSONObject jsonWithGroupID;
    private JsonArrayRequest userJSONRequest;
    public final String URL_GROUP_DESC = "http://192.168.2.3:8000/groups/";
    private Response.Listener<JSONArray> jsonListenerForUsers;
    private Response.ErrorListener jsonErrorListenerForUsers;
    private JSONArray myJsonArray;
    private RequestQueue requestQueue;

    public static final String GROUP_ID = "GROUP_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        initialiseLocalVariables();
        retrieveGroupsFromTheServer();

    }

    private void retrieveGroupsFromTheServer() {
        initialiseListenersForUsers();
        userJSONRequest = new JsonArrayRequest(Request.Method.GET, URL_GROUP_DESC + Long.toString(id),
                jsonListenerForUsers, jsonErrorListenerForUsers);
        requestQueue.add(userJSONRequest);
  }


    private void initialiseListenersForUsers() {
        initialiseResponseListenersForUsers();
        initialiseErrorListenersForUsers();

    }

    private void initialiseResponseListenersForUsers() {
        jsonListenerForUsers = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                myJsonArray = response;
                Log.d("RECEIVED_JSON_ARRAY", myJsonArray.toString());
                GroupPage.this.runOnUiThread(new Runnable() {//
                    @Override
                    public void run() {
                        sendUsersInfoToGroupPage(myJsonArray);
                    }
                });
            }
        };

    }


    private void sendUsersInfoToGroupPage(JSONArray jsonArrayReceived) {
        User user;
        JSONArray tempJsonArray;
        JSONObject myJson;

        // intentToGroupPage.putExtra("USERS_LIST_JSON",JS)
        try {
            myJson = jsonArrayReceived.getJSONObject(0);
            myJsonArray = myJson.getJSONArray("users");
            Log.d("JSON_ARRAY_RECEIVED",myJsonArray.toString());

            for (int i = 0; i < myJsonArray.length(); i++) {
                myJson = myJsonArray.getJSONObject(i);
                user =  addJSONToUserObject(myJson);
                userList.add(user);
            }
           // System.out.println("*******  the USER LIST is " +userList.toString());
            arrayAdapter.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
            Log.d("Error","Wrong JSON received");
        }

    }

    private void initialiseErrorListenersForUsers() {
        jsonErrorListenerForUsers = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_VOLLEY", error.toString());
            }
        };
    }

    private void initialiseLocalVariables() {
        id = getIntent().getLongExtra(GROUP_ID,1);
        listView = (ListView)findViewById(R.id.users);
        requestQueue = Volley.newRequestQueue(this);
        userList = new ArrayList<>();
        userList.add(User.createUser("samla",12,"Ludhiana"));
        arrayAdapter = new ArrayAdapter(GroupPage.this,R.layout.support_simple_spinner_dropdown_item,userList);
        listView.setAdapter(arrayAdapter);
        jsonWithGroupID = new JSONObject();


        //token initialization
    }

    private User addJSONToUserObject(JSONObject myJson) throws JSONException{
       return User.createUser(myJson.getString("username"), 20, "Pune");
    }

}
