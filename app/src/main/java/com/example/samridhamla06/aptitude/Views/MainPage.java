package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.samridhamla06.aptitude.MappersOrAdapters.MainPageAdapter;
import com.example.samridhamla06.aptitude.AndroidListeners.MainPageView.MainPageListViewListener;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.MainPageServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainPage extends AppCompatActivity {

    private ListView listView;
    private List<Group> groupList;
    private Context mainPageContext;
    private MainPageServices mainPageServices;
    private MainPageListViewListener mainPageListViewListener;
    private MainPageAdapter mainPageAdapter;
    private Intent intentToLoginPage;
    private Intent intentToAddGroupPage;
    private SharedPreferences sharedPreferences;
    public static final int REQUEST_CODE_FOR_ADD_GROUP_PAGE = new Random().nextInt(10);//limit [0,10]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initialiseLocalVariables();
        retrieveGroupsFromTheServer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FOR_ADD_GROUP_PAGE){
            refreshPage();
        }
    }

    private void refreshPage() {
        startActivity(getIntent());
        finish();
    }

    private void retrieveGroupsFromTheServer() {
        mainPageServices.getGroupsFromTheServer();}




    private void initialiseLocalVariables() {
        listView = (ListView)findViewById(R.id.groups);
        groupList = new ArrayList<>();
        mainPageContext = MainPage.this;
        mainPageAdapter = new MainPageAdapter(mainPageContext, groupList);
        mainPageServices = new MainPageServices(mainPageContext,mainPageAdapter, groupList);
        mainPageListViewListener = new MainPageListViewListener(mainPageContext);
        intentToLoginPage = new Intent(this,LoginPage.class);
        addListViewParameters(listView);
        sharedPreferences = getBaseContext().getSharedPreferences("PREFERENCES",Context.MODE_PRIVATE);
        intentToAddGroupPage = new Intent(this,AddGroupPage.class);
    }

    private void addListViewParameters(ListView listView) {
        listView.setAdapter(mainPageAdapter);
        listView.setOnItemClickListener(mainPageListViewListener);
    }

    public void onLogOut(View view){

        //--------------------------------------------------------ALERT BOX
        refreshSharedPreferences();//-----------------------------VERY IMPORTANT OTHERWISE YOU WILL LAND HERE ONLY EVERYTIME.
        startActivity(intentToLoginPage);
        finish();
    }

    public void onAddGroup(View view){
        startActivityForResult(intentToAddGroupPage, REQUEST_CODE_FOR_ADD_GROUP_PAGE);
    }

    private void refreshSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void onRefresh(View view){
        refreshPage();
    }



}
