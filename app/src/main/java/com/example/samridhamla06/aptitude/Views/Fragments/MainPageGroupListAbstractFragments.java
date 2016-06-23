package com.example.samridhamla06.aptitude.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samridhamla06.aptitude.Adapters.MainPageRecyclerViewAdapter;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.MainPageServices;

import java.util.ArrayList;
import java.util.List;


public class MainPageGroupListAbstractFragments extends Fragment {

    public static final int SPAN_COUNT = 2;
    protected List<Group> groupList;
    protected Context mainPageContext;
    protected MainPageServices mainPageServices;
    protected MainPageRecyclerViewAdapter mainPageRecyclerViewAdapter;
    protected RecyclerView recyclerView;

    public MainPageGroupListAbstractFragments() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_group_layout, container, false);//DO IT
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialiseLocalVariables();
        actionOnServer();
    }


    protected void actionOnServer() {
    }

    ;

    protected void initialiseLocalVariables() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.groups);
        mainPageContext = getContext();
        groupList = new ArrayList<>();
        mainPageRecyclerViewAdapter = new MainPageRecyclerViewAdapter(groupList);
        mainPageServices = new MainPageServices(mainPageContext, mainPageRecyclerViewAdapter, groupList);
        addListViewParameters(recyclerView);
    }


    protected void addListViewParameters(RecyclerView recyclerView) {
        recyclerView.setAdapter(mainPageRecyclerViewAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
