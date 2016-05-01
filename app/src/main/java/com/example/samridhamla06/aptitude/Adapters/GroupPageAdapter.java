package com.example.samridhamla06.aptitude.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Utility.UserRelated;

import java.util.List;

public class GroupPageAdapter extends ArrayAdapter<User> {

    private Context groupPageContext;
    private TextView userNameTextView;
    private TextView ageTextView;
    private TextView locationTextView;
    private List<User> userList;


    public GroupPageAdapter(Context context, List<User> userList) {
        super(context, R.layout.group_page_list_view_layout,userList);
        this.groupPageContext = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) groupPageContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.group_page_list_view_layout, null, false);
        initializeTextViewsForSelectedView(rowView);
        User selectedUser = getSelectedUser(position);
        mapViewsWithUserList(selectedUser);
        setUserInfoIntoViewTag(rowView, selectedUser);
        return rowView;
    }

    private void setUserInfoIntoViewTag(View rowView, User selectedUser) {
        String userInfo = UserRelated.convertUserObjectToJsonString(selectedUser);
        rowView.setTag(R.string.user_info,userInfo);
    }

    private User getSelectedUser(int position) {
        return userList.get(position);
    }

    private void initializeTextViewsForSelectedView(View rowView) {
        userNameTextView = (TextView)rowView.findViewById(R.id.userName);
        ageTextView = (TextView)rowView.findViewById(R.id.userAge);
        locationTextView = (TextView)rowView.findViewById(R.id.location);
    }

    private void mapViewsWithUserList(User selectedUser) {
        userNameTextView.setText(selectedUser.getName());
        ageTextView.setText("age : " + Integer.toString(selectedUser.getAge()));//  *****VERY IMPORTANT**********
        locationTextView.setText(selectedUser.getLocation());
    }
}
