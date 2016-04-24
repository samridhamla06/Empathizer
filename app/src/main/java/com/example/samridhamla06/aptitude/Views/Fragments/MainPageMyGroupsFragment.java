package com.example.samridhamla06.aptitude.Views.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.samridhamla06.aptitude.AndroidListeners.MainPageView.MainPageListViewListener;
import com.example.samridhamla06.aptitude.MappersOrAdapters.MainPageAdapter;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.MainPageServices;
import com.example.samridhamla06.aptitude.Views.AddGroupPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainPageMyGroupsFragment extends Fragment {

    private ListView listView;
    private List<Group> groupList;
    private Context mainPageContext;
    private MainPageServices mainPageServices;
    private MainPageListViewListener mainPageListViewListener;
    private MainPageAdapter mainPageAdapter;
    private Intent intentToAddGroupPage;
    public static final int REQUEST_CODE_FOR_ADD_GROUP_PAGE = new Random().nextInt(10);//limit [0,10]
    private Button addGroupButton;


    public MainPageMyGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_group_layout, container, false);//DO IT
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialiseLocalVariables();
        retrieveGroupsFromTheServer();
    }


    private void retrieveGroupsFromTheServer() {
        mainPageServices.getGroupsFromTheServer();
    }

    private void initialiseLocalVariables() {
        listView = (ListView) getView().findViewById(R.id.groups);
        groupList = new ArrayList<>();
        mainPageContext = getContext();
        mainPageAdapter = new MainPageAdapter(mainPageContext, groupList);
        mainPageServices = new MainPageServices(mainPageContext, mainPageAdapter, groupList);
        mainPageListViewListener = new MainPageListViewListener(mainPageContext);
        addListViewParameters(listView);
        intentToAddGroupPage = new Intent(getActivity(), AddGroupPage.class);
        addGroupButton = (Button) getView().findViewById(R.id.addGroup);
        setUpAddGroupButton();
    }

    private void setUpAddGroupButton() {
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddGroup();
            }
        });
    }


    private void addListViewParameters(ListView listView) {
        listView.setAdapter(mainPageAdapter);
        listView.setOnItemClickListener(mainPageListViewListener);
    }

    public void onAddGroup() {
        startActivityForResult(intentToAddGroupPage, REQUEST_CODE_FOR_ADD_GROUP_PAGE);
    }


}
