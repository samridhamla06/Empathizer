package com.example.samridhamla06.aptitude.AndroidListeners.CommunityPageView;

/**
 * Created by samridhamla06 on 17/04/16.
 */

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.samridhamla06.aptitude.Views.CommunityPage;

public class CommunityPageSufferingSpinnerListener implements AdapterView.OnItemSelectedListener{
    private CommunityPage communityPage;

    public CommunityPageSufferingSpinnerListener(CommunityPage registerPage) {
        this.communityPage = registerPage;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String suffering = parent.getItemAtPosition(position).toString();
        Log.d("SUFFERING_SELECTED", suffering);
        communityPage.setSuffering(suffering);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        communityPage.setSuffering(parent.getItemAtPosition(0).toString());
    }

}

