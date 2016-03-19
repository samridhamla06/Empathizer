package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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
import com.example.samridhamla06.aptitude.Service.GroupPageServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupPage extends AppCompatActivity {

    private long id;
    private ListView listView;
    private List<User> userList;
    private ArrayAdapter adapterForUsers;
    private GroupPageServices groupPageServices;
    public static final String GROUP_ID = "GROUP_ID";
    private Context groupPageContext;


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
        id = getIntent().getLongExtra(GROUP_ID,1);
        listView = (ListView)findViewById(R.id.users);
        userList = new ArrayList<>();
        userList.add(User.createUser("samla",12,"Ludhiana"));
        adapterForUsers = new ArrayAdapter(GroupPage.this,R.layout.support_simple_spinner_dropdown_item,userList);
        listView.setAdapter(adapterForUsers);
        groupPageContext = GroupPage.this;
        groupPageServices = new GroupPageServices(groupPageContext,adapterForUsers,userList,id);
    }

}
