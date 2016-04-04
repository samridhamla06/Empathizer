package com.example.samridhamla06.aptitude.MappersOrAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.samridhamla06.aptitude.Modals.User;
import com.example.samridhamla06.aptitude.R;

import java.util.List;
public class GroupPageAdapter extends ArrayAdapter<User> {

    private Context groupPageContext;
    private TextView name;
    private TextView userAge;
    private TextView location;
    private List<User> userList;
    private long userId;
    public static final int USER_ID = 123;

    public GroupPageAdapter(Context context, List<User> userList) {
        super(context, R.layout.group_page_list_view_layout,userList);
        this.groupPageContext = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) groupPageContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.group_page_list_view_layout,null,false);
        initializeTextViewsForSelectedView(rowView);
        mapViewsWithUserList(position);
        rowView.setTag(R.string.UserID,userId);
        return rowView;
    }

    private void initializeTextViewsForSelectedView(View rowView) {
        name = (TextView)rowView.findViewById(R.id.name);
        userAge = (TextView)rowView.findViewById(R.id.userAge);
        location = (TextView)rowView.findViewById(R.id.location);
    }

    private void mapViewsWithUserList(int position) {
        User selectedUser = userList.get(position);
        name.setText(selectedUser.getName());
        userAge.setText("age : "+ Integer.toString(selectedUser.getAge()));//  *****VERY IMPORTANT**********
        location.setText(selectedUser.getLocation());
        userId = selectedUser.getId();
    }



}
