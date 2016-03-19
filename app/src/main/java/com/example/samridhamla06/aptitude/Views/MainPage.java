package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
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
import com.example.samridhamla06.aptitude.Service.MainPageServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainPage extends AppCompatActivity {

    public static final String USER_NAME = "USER_NAME";
    private SharedPreferences sharedPreferences;
    private String token;
    private ListView listView;
    private ArrayAdapter arrayAdapterForGroups;
    private List<Community> communityList;
    private Intent intentToGroupPage;
    private Context mainPageContext;
    private MainPageServices mainPageServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initialiseLocalVariables();
        retrieveGroupsFromTheServer();
    }

    private void retrieveGroupsFromTheServer() {
        mainPageServices.getGroupsFromTheServer();
    }


    private void initialiseLocalVariables() {
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "000");
        listView = (ListView)findViewById(R.id.groups);
        communityList = new ArrayList<>();
        mainPageContext = MainPage.this;
        intentToGroupPage = new Intent(mainPageContext,GroupPage.class);
        arrayAdapterForGroups = new ArrayAdapter(mainPageContext, R.layout.support_simple_spinner_dropdown_item, communityList);
        mainPageServices = new MainPageServices(mainPageContext,arrayAdapterForGroups,communityList);
        addListViewParameters(listView);
        Log.d("token", token);
    }

    private void addListViewParameters(ListView listView) {
        listView.setAdapter(arrayAdapterForGroups);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("id selected:", Long.toString(id) + " position Selected: " + Integer.toString(position));
                intentToGroupPage.putExtra(GroupPage.GROUP_ID,id);
                startActivity(intentToGroupPage);
            }
        });
    }



}
