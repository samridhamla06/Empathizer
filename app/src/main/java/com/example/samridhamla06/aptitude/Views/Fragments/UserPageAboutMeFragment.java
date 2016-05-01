package com.example.samridhamla06.aptitude.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Utility.UserRelated;

public class UserPageAboutMeFragment extends Fragment {


    private TextView aboutMeTextView;
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView ageTextView;

    public static UserPageAboutMeFragment newInstance(String userInfo) {
        UserPageAboutMeFragment myFragment = new UserPageAboutMeFragment();
        Bundle args = new Bundle();
        Log.e("user_info_putFragment",userInfo);
        args.putString(Constants.USER_INFO, userInfo);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_page_about_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialiseTextViews();
        mapTextViews();
    }

    private void mapTextViews() {
        User userSelected = getUserSelected();
        aboutMeTextView.setText(userSelected.getAboutMe());
        nameTextView.setText(userSelected.getName());
        locationTextView.setText(userSelected.getLocation());
        ageTextView.setText(Integer.toString(userSelected.getAge()));//VERY IMPORTNAT...WASTED LODS OF TIME..BAKCHODI
    }

    private User getUserSelected() {
        String userInfo = getArguments().getString(Constants.USER_INFO);
        return UserRelated.getUserObjectFromJson(userInfo);
    }

    private void initialiseTextViews() {
        aboutMeTextView = (TextView)getView().findViewById(R.id.aboutMe);
        nameTextView = (TextView)getView().findViewById(R.id.email);
        locationTextView = (TextView)getView().findViewById(R.id.location);
        ageTextView = (TextView)getView().findViewById(R.id.age);
    }
}
