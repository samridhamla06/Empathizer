package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.samridhamla06.aptitude.AndroidListeners.GroupPageView.GroupPageListViewListener;
import com.example.samridhamla06.aptitude.MappersOrAdapters.GroupPageAdapter;
import com.example.samridhamla06.aptitude.Modals.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.GroupPageServices;

import java.util.ArrayList;
import java.util.List;

public class GroupPage extends AppCompatActivity {

    private String groupId;
    private ListView userListView;
    private List<User> userList;
    private GroupPageAdapter groupPageAdapter;
    private GroupPageServices groupPageServices;
    public static final String GROUP_ID = "GROUP_ID";
    private Context groupPageContext;
    private GroupPageListViewListener groupPageListViewListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        initialiseLocalVariables();
        retrieveGroupsFromTheServer();
    }

    private void retrieveGroupsFromTheServer() {
        groupPageServices.getUsersForParticularGroup();
    }

    private void initialiseLocalVariables() {
        groupId = getIntent().getStringExtra(GROUP_ID);
        userListView = (ListView) findViewById(R.id.users);
        userList = new ArrayList<>();
        groupPageContext = GroupPage.this;
        groupPageAdapter = new GroupPageAdapter(groupPageContext, userList);
        groupPageServices = new GroupPageServices(groupPageContext, groupPageAdapter, userList, groupId);
        groupPageListViewListener = new GroupPageListViewListener(groupPageContext);
        setListViewParameters();
    }

    private void setListViewParameters() {
        userListView.setAdapter(groupPageAdapter);
        userListView.setOnItemClickListener(groupPageListViewListener);
    }


}
