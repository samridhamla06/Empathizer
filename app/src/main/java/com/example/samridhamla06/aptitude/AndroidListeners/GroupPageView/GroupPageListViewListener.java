package com.example.samridhamla06.aptitude.AndroidListeners.GroupPageView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Views.Activities.UserPage;

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
        String userInfo = (String) view.getTag(R.string.user_info);
        intentToUserPage.putExtra(Constants.USER_INFO, userInfo);
        intentToUserPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//WHY?
        groupPageContext.startActivity(intentToUserPage);
    }
}
