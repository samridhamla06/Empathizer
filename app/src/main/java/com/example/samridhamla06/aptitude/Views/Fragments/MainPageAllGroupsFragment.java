package com.example.samridhamla06.aptitude.Views.Fragments;


public class MainPageAllGroupsFragment extends MainPageGroupListAbstractFragments {

    @Override
    protected void actionOnServer() {
        mainPageServices.getAllGroupsFromTheServer();
    }

    public MainPageAllGroupsFragment() {
        super();
    }
}
