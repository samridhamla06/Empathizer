package com.example.samridhamla06.aptitude.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samridhamla06.aptitude.AndroidListeners.MainPageView.MainPageRecyclerViewListener;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.ViewHolders.MainPageViewHolder;

import java.util.List;


public class MainPageRecyclerViewAdapter extends RecyclerView.Adapter<MainPageViewHolder>{

    private List<Group> groupList;

    public MainPageRecyclerViewAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public MainPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.main_page_list_view_layout, parent, false);
        return new MainPageViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MainPageViewHolder holder, int position) {
        giveValuesToTextViews(holder, position);
    }

    private void giveValuesToTextViews(MainPageViewHolder holder, int position) {
        holder.setGroupNameValue(getSelectedGroup(position).getName());
        holder.setLocationValue(getSelectedGroup(position).getLocation());
        holder.setGroupId(getSelectedGroup(position).getId());
    }

    private Group getSelectedGroup(int position) {
        return groupList.get(position);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
