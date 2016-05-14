package com.example.samridhamla06.aptitude.ViewHolders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.samridhamla06.aptitude.AndroidListeners.MainPageView.MainPageRecyclerViewListener;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Views.Activities.GroupPage;

import java.util.List;


/**
 * Created by samridhamla06 on 14/05/16.
 */
public class MainPageViewHolder extends RecyclerView.ViewHolder {
    private TextView groupName;
    private TextView location;
    private String groupId;
    private Intent intentToGroupPage;
    private Context mainPageContext;

    public MainPageViewHolder(View itemView) {
        super(itemView);
        mapViewsWithXML(itemView);
        instantiateLocalVariables(itemView);
        setItemViewListener(itemView);
    }

    private void instantiateLocalVariables(View itemView) {
        instantiateIntentToMainPage(itemView);
        mainPageContext = itemView.getContext();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private void setItemViewListener(final View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToGroupPage.putExtra(Constants.GROUP_ID,groupId);
                intentToGroupPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mainPageContext.startActivity(intentToGroupPage);
            }
        });

    }

    private void instantiateIntentToMainPage(View itemView) {
        intentToGroupPage = new Intent(itemView.getContext(),GroupPage.class);
    }

    public TextView getGroupName() {
        return groupName;
    }

    public void setGroupNameValue(String value) {
        groupName.setText(value);
    }

    public void setLocationValue(String value) {
        location.setText(value);
    }

    private void mapViewsWithXML(View itemView) {
        groupName = (TextView) itemView.findViewById(R.id.groupName);
        location = (TextView) itemView.findViewById(R.id.location);
    }

    public TextView getLocation() {
        return location;
    }
}
