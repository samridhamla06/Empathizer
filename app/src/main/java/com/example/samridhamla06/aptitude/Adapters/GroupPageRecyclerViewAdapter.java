package com.example.samridhamla06.aptitude.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Utility.Conversions;
import com.example.samridhamla06.aptitude.Utility.UserRelated;
import com.example.samridhamla06.aptitude.ViewHolders.GroupPageViewHolder;

import java.util.Collections;
import java.util.List;


public class GroupPageRecyclerViewAdapter extends RecyclerView.Adapter<GroupPageViewHolder> {

    private List<User> userList = Collections.EMPTY_LIST;


    public GroupPageRecyclerViewAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public GroupPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.group_page_list_view_layout, null, false);
        GroupPageViewHolder groupPageViewHolder = new GroupPageViewHolder(rowView);
        return groupPageViewHolder;
    }

    @Override
    public void onBindViewHolder(GroupPageViewHolder holder, int position) {
        User selectedUser = getSelectedUser(position);
        holder.setUserNameTextView(selectedUser.getName());
        holder.setAgeTextView(Conversions.stringifyInt(selectedUser.getAge()));
        holder.setLocationTextView(selectedUser.getLocation());
        holder.setUserInfo(UserRelated.convertUserObjectToJsonString(selectedUser));
    }

    private User getSelectedUser(int position) {
        return userList.get(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
