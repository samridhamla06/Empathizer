package com.example.samridhamla06.aptitude.MappersOrAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.samridhamla06.aptitude.Modals.Community;
import com.example.samridhamla06.aptitude.R;

import java.util.List;

/**
 * Created by samridhamla06 on 20/03/16.
 */
public class MainPageAdapter extends ArrayAdapter<Community> {

    private TextView groupName;
    private List<Community> communityList;
    private Context mainPageContext;
    private long groupId;//as groupID could be huge one day or may be

    public MainPageAdapter(Context mainPageContext, List<Community> communityList) {
        super(mainPageContext, R.layout.main_page_list_view_layout, communityList);
        this.mainPageContext = mainPageContext;
        this.communityList = communityList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mainPageContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.main_page_list_view_layout,null,false);//to add some layout to parent
        initializeTextViewsForSelectedView(rowView);
        mapViewsWithCommunityList(position);
        rowView.setTag(R.string.GroupID,groupId);//STORED THE ID OF GROUP SELECTED IN THE TAG....CAN STORE A WHOLE OBJECT AS WELL,
        return rowView;                         //USED R.string AS THIS FUNCTION DEMANDS UNIQUENESS...THIS IS HOW ANDROID WANTS IT..FUCK
    }

    private void mapViewsWithCommunityList(int position) {
        groupName.setText(communityList.get(position).getName());
        groupId = communityList.get(position).getId();
    }

    private void initializeTextViewsForSelectedView(View rowView) {
        groupName = (TextView)rowView.findViewById(R.id.groupName);
    }
}
