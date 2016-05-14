package com.example.samridhamla06.aptitude.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samridhamla06.aptitude.Adapters.GroupPageRecyclerViewAdapter;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.GroupPageServices;

import java.util.ArrayList;
import java.util.List;


public class GroupPageMembersFragment extends Fragment {


    private String groupId;
    private RecyclerView recyclerView;
    private List<User> userList;
    private GroupPageRecyclerViewAdapter groupPageRecyclerViewAdapter;
    private GroupPageServices groupPageServices;
    private Context groupPageContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_members_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialiseLocalVariables(savedInstanceState);
        retrieveGroupsFromTheServer();
    }

    public static GroupPageMembersFragment newInstance(String groupId) {
        GroupPageMembersFragment myFragment = new GroupPageMembersFragment();
        Bundle args = new Bundle();
        args.putString(Constants.GROUP_ID, groupId);
        myFragment.setArguments(args);
        return myFragment;
    }

    private void initialiseLocalVariables(Bundle savedInstanceState) {
        groupId = getArguments().getString(Constants.GROUP_ID);
        recyclerView = (RecyclerView) getView().findViewById(R.id.users);
        userList = new ArrayList<>();
        groupPageContext = getContext();
        groupPageRecyclerViewAdapter = new GroupPageRecyclerViewAdapter(userList);
        groupPageServices = new GroupPageServices(getActivity(), groupPageRecyclerViewAdapter, userList, groupId);
        setRecyclerViewParameters();
    }

    private void setRecyclerViewParameters() {
        recyclerView.setAdapter(groupPageRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void retrieveGroupsFromTheServer() {
        groupPageServices.getUsersForParticularGroup();
    }
}
