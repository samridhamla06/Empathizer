package com.example.samridhamla06.aptitude.AndroidListeners.GroupPageView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Views.UserPage;

public class GroupPageListViewListener implements AdapterView.OnItemClickListener {
    private final Context groupPageContext;
    private Intent intentToUserPage;

    public GroupPageListViewListener(Context groupPageContext) {
        this.groupPageContext = groupPageContext;
        initializeVariables();
    }

    private void initializeVariables() {
        intentToUserPage = new Intent(groupPageContext, UserPage.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String userId = (String) view.getTag(R.string.UserID);
        intentToUserPage.putExtra(UserPage.USER_ID, userId);
        intentToUserPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//WHY?
        groupPageContext.startActivity(intentToUserPage);
    }
}
