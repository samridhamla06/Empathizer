package com.example.samridhamla06.aptitude.Views;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

import com.example.samridhamla06.aptitude.AndroidListeners.CommunityPageView.CommunityPageSufferingSpinnerListener;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;

public class CommunityPage extends AppCompatActivity {

    private Spinner sufferingNameDropMenu;
    private String sufferingName;
    private CommunityPageSufferingSpinnerListener communityPageSufferingSpinnerListener;
    private SharedPreferences sharedPreferences;
    private Intent intentToMainPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        sufferingNameDropMenu = (Spinner) findViewById(R.id.sufferingNameDropMenu);
        communityPageSufferingSpinnerListener = new CommunityPageSufferingSpinnerListener(this);
        sufferingNameDropMenu.setOnItemSelectedListener(communityPageSufferingSpinnerListener);
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(getBaseContext());
        intentToMainPage = new Intent(this, MainPage.class);
    }

    public void setSuffering(String sufferingName) {
        this.sufferingName = sufferingName;
    }

    public void onClickingSearch(View view) {
        addSufferingAndLocationToSharedPreferences();
        startActivity(intentToMainPage);
    }

    private void addSufferingAndLocationToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LoginPage.LOCATION, "Pune");//FOR THE TIME BEING
        editor.putString(LoginPage.SUFFERING_NAME, sufferingName);
    }
}
