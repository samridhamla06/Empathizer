package com.example.samridhamla06.aptitude.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.R;

import java.util.List;


public class MainPageAdapter extends ArrayAdapter<Group> {

    private TextView groupName;
    private List<Group> groupList;
    private Context mainPageContext;
    private String groupId;//as groupID could be huge one day or may be



    public MainPageAdapter(Context mainPageContext, List<Group> groupList) {
        super(mainPageContext, R.layout.main_page_list_view_layout, groupList);
        this.mainPageContext = mainPageContext;
        this.groupList = groupList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mainPageContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.main_page_list_view_layout,null,false);//to add some layout to parent
        initializeTextViewsForSelectedView(rowView);
        mapViewsWithCommunityList(position);
        rowView.setTag(R.string.GroupID, groupId);//STORED THE ID OF GROUP SELECTED IN THE TAG....CAN STORE A WHOLE OBJECT AS WELL,
        return rowView;                         //USED R.string AS THIS FUNCTION DEMANDS UNIQUENESS...THIS IS HOW ANDROID WANTS IT..FUCK
    }

    private void mapViewsWithCommunityList(int position) {
        groupName.setText(groupList.get(position).getName());
        groupId = groupList.get(position).getId();
    }

    private void initializeTextViewsForSelectedView(View rowView) {
        groupName = (TextView)rowView.findViewById(R.id.groupName);
    }
}
