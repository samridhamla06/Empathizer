package com.example.samridhamla06.aptitude.Views.Fragments;

public class MainPageMyGroupsFragment extends MainPageGroupListAbstractFragments {


    public MainPageMyGroupsFragment() {
        super();
    }

    @Override
    protected void actionOnServer() {
        mainPageServices.getMyGroupsFromTheServer();
    }

}
