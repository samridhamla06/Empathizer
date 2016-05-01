package com.example.samridhamla06.aptitude.Views.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;


public class UserPageRecommendationsFragment extends Fragment {

    public UserPageRecommendationsFragment() {
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
        return inflater.inflate(R.layout.fragment_user_page_recommendations, container, false);
    }

    public static UserPageRecommendationsFragment newInstance(String userId) {
        UserPageRecommendationsFragment myFragment = new UserPageRecommendationsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.USER_ID, userId);
        myFragment.setArguments(args);
        return myFragment;
    }


}

