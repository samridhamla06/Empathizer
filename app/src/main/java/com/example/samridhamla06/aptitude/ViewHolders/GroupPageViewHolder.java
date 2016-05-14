package com.example.samridhamla06.aptitude.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Views.Activities.UserPage;

/**
 * Created by samridhamla06 on 14/05/16.
 */
public class GroupPageViewHolder extends RecyclerView.ViewHolder{

    private TextView userName;
    private TextView location;
    private TextView age;
    private String userInfo;
    private Intent intentToUserPage;
    private Context groupPageContext;
    private View onClickListener;


    public GroupPageViewHolder(View itemView) {
        super(itemView);
        mapTextViews(itemView);
        instantiateLocalVariables(itemView);
        setOnClickListener(itemView);
    }

    private void instantiateLocalVariables(View itemView) {
        groupPageContext = itemView.getContext();
        intentToUserPage = new Intent(itemView.getContext(), UserPage.class);
    }

    private void mapTextViews(View itemView) {
        userName = (TextView)itemView.findViewById(R.id.userName);
        location = (TextView)itemView.findViewById(R.id.location);
        age = (TextView)itemView.findViewById(R.id.userAge);
    }


    public void setOnClickListener(View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToUserPage.putExtra(Constants.USER_INFO, userInfo);
                intentToUserPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//WHY?
                groupPageContext.startActivity(intentToUserPage);
            }
        });
    }

    public TextView getUserName() {
        return userName;
    }

    public void setUserNameTextView(String text) {
        userName.setText(text);
    }

    public void setLocationTextView(String text) {
        location.setText(text);
    }

    public void setAgeTextView(String text) {
        age.setText(text);
    }

    public TextView getLocation() {
        return location;
    }



    public TextView getAge() {
        return age;
    }


    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

}
