package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.samridhamla06.aptitude.Adapters.ViewPagerAdapter;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.GroupPageServices;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;
import com.example.samridhamla06.aptitude.Utility.UserRelated;
import com.example.samridhamla06.aptitude.Views.Fragments.GroupPageMembersFragment;
import com.example.samridhamla06.aptitude.Views.Fragments.MainPageNotificationsFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupPage extends AppCompatActivity {

    public static final String GROUP_ID = "GROUP_ID";

    private Toolbar groupPageToolBar;
    private ViewPager groupPageViewPager;
    private TabLayout groupPageTabLayout;
    private String groupId;
    private Context groupPageContext;
    private GroupPageServices groupPageServices;
    private SharedPreferences sharedPreferences;
    private GroupPageMembersFragment groupPageMembersFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        initialiseLocalVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();

        switch (selected) {
            case R.id.joinGroup:
                Toast.makeText(this, "Joining Group ..", Toast.LENGTH_LONG).show();
                onJoinGroup();
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_LONG).show();
                return true;

            case R.id.inviteOthers:
                Toast.makeText(this, "Inviting", Toast.LENGTH_LONG).show();
                return true;

            case R.id.faqs:
                Toast.makeText(this, "FAQS showing off ..", Toast.LENGTH_LONG).show();
                return true;

            default:
                Toast.makeText(this, "Maa chuda", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }


    private void initialiseLocalVariables() {
        groupId = getIntent().getStringExtra(GROUP_ID);
        groupPageContext = getBaseContext();
        groupPageServices = new GroupPageServices(this, groupId);
        initialiseSharedPreferences();

        groupPageToolBar = (Toolbar) findViewById(R.id.group_page_toolbar);
        setSupportActionBar(groupPageToolBar);
        groupPageViewPager = (ViewPager) findViewById(R.id.group_page_viewPager);
        groupPageTabLayout = (TabLayout) findViewById(R.id.group_page_tabLayout);
        groupPageMembersFragment = GroupPageMembersFragment.newInstance(groupId);
        initialiseViewPagerAndLinkWithTabLayout();
    }

    private void initialiseViewPagerAndLinkWithTabLayout() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(groupPageMembersFragment, Constants.MEMBERS);
        viewPagerAdapter.addFragment(new MainPageNotificationsFragment(), Constants.EVENTS);
        viewPagerAdapter.addFragment(new MainPageNotificationsFragment(), Constants.RECOMMENDATIONS);
        groupPageViewPager.setAdapter(viewPagerAdapter);
        groupPageTabLayout.setupWithViewPager(groupPageViewPager);
    }


    public void onJoinGroup() {
        //ALERT BOX......REQUEST SENT TO ADMIN...probably...filhaal krde
        try {
            JSONObject userJsonObject = UserRelated.createUserJSONObjectFromSharedPreferences(sharedPreferences);
            groupPageServices.sendRequestToJoinGroup(userJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initialiseSharedPreferences() {
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(groupPageContext);
    }


}
