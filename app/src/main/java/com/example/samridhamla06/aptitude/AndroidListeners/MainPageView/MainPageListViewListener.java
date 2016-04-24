package com.example.samridhamla06.aptitude.AndroidListeners.MainPageView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Views.Activities.GroupPage;

public class MainPageListViewListener implements AdapterView.OnItemClickListener{
    private Intent intentToGroupPage;
    private Context mainPageContext;

    public MainPageListViewListener(Context mainPageContext) {
        this.mainPageContext = mainPageContext;
        initialiseVariables();
    }

    private void initialiseVariables() {
        intentToGroupPage = new Intent(mainPageContext,GroupPage.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("id selected:", Long.toString(id) + " position Selected: " + Integer.toString(position));
        String groupId = retrieveGroupIdFromViewSelected(view,position);
        intentToGroupPage.putExtra(GroupPage.GROUP_ID, groupId);
        intentToGroupPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainPageContext.startActivity(intentToGroupPage);
    }

    private String retrieveGroupIdFromViewSelected(View view, int position) {
        return (String)view.getTag(R.string.GroupID);
    }
}
