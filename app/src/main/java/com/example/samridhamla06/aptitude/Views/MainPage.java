package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.samridhamla06.aptitude.MappersOrAdapters.MainPageAdapter;
import com.example.samridhamla06.aptitude.AndroidListeners.MainPageView.MainPageListViewListener;
import com.example.samridhamla06.aptitude.Modals.Community;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.MainPageServices;
import java.util.ArrayList;
import java.util.List;


public class MainPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String token;
    private ListView listView;
    private List<Community> communityList;
    private Intent intentToGroupPage;
    private Context mainPageContext;
    private MainPageServices mainPageServices;
    private MainPageListViewListener mainPageListViewListener;
    private MainPageAdapter mainPageAdapter;


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
        mainPageAdapter = new MainPageAdapter(mainPageContext,communityList);
        mainPageServices = new MainPageServices(mainPageContext,mainPageAdapter,communityList);
        mainPageListViewListener = new MainPageListViewListener(mainPageContext);
        addListViewParameters(listView);
        Log.d("token", token);
    }

    private void addListViewParameters(ListView listView) {
        listView.setAdapter(mainPageAdapter);
        listView.setOnItemClickListener(mainPageListViewListener);
    }



}
