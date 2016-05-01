package com.example.samridhamla06.aptitude.Views.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.samridhamla06.aptitude.Adapters.ViewPagerAdapter;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.UserPageServices;
import com.example.samridhamla06.aptitude.Utility.UserRelated;
import com.example.samridhamla06.aptitude.Views.Fragments.UserPageAboutMeFragment;
import com.example.samridhamla06.aptitude.Views.Fragments.UserPageMyGroupsFragment;
import com.example.samridhamla06.aptitude.Views.Fragments.UserPageRecommendationsFragment;

public class UserPage extends AppCompatActivity {

    //USER INFO
    private String userName;
    private int age;
    private String location;
    private String email;
    private String aboutMe;
    private String sufferingName;
    private String currentStatus;
    private String gender;
    private String userId;

    private User userSelected;

   //OTHER - OBJECTS
    private UserPageServices userPageServices;
    private Toolbar userPageToolBar;
    private ViewPager userPageViewPager;
    private TabLayout userPageTabLayout;
    private UserPageAboutMeFragment userPageAboutMeFragment;
    private UserPageMyGroupsFragment userPageMyGroupsFragment;
    private UserPageRecommendationsFragment userPageRecommendationsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        initialiseLocalVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();

        switch (selected) {
            case R.id.logoutButton:
                Toast.makeText(this, "Logging Out", Toast.LENGTH_LONG).show();
                return true;

            case R.id.myProfile:
                Toast.makeText(this, "My_profile selected", Toast.LENGTH_LONG).show();
                return true;

            default:
                Toast.makeText(this, "Maa chuda", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }


    private void retrieveInfoFromServer() {
        userPageServices.retrieveInfoFromServer();
    }



    public void fillAllTextViews(User userSelected) {
        //nameTextView.setText(userSelected.getName());
        //locationTextView.setText(userSelected.getLocation());
    }

    private void initialiseLocalVariables() {
        initialiseUserInfoFields();
        String userInfo = getIntent().getStringExtra(Constants.USER_INFO);
        userSelected = UserRelated.getUserObjectFromJson(userInfo);
        userPageServices = new UserPageServices(this,userId);
        userPageAboutMeFragment = UserPageAboutMeFragment.newInstance(userInfo);
        userPageMyGroupsFragment = UserPageMyGroupsFragment.newInstance(userId);
        userPageRecommendationsFragment = UserPageRecommendationsFragment.newInstance(userId);
        userPageToolBar = (Toolbar) findViewById(R.id.user_page_tool_bar);
        setSupportActionBar(userPageToolBar);
        userPageViewPager = (ViewPager) findViewById(R.id.user_page_viewPager);
        userPageTabLayout = (TabLayout) findViewById(R.id.user_page_tabLayout);
        initialiseViewPagerAndLinkWithTabLayout();
    }

    private void initialiseUserInfoFields() {
        String userInfo = getIntent().getStringExtra(Constants.USER_INFO);
        userSelected = UserRelated.getUserObjectFromJson(userInfo);
        userId = userSelected.getId();
        aboutMe = userSelected.getAboutMe();
        userName = userSelected.getName();
        location = userSelected.getLocation();
        age = userSelected.getAge();

    }

    private void initialiseViewPagerAndLinkWithTabLayout() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(userPageAboutMeFragment, Constants.ABOUT_ME);
        viewPagerAdapter.addFragment(userPageMyGroupsFragment, Constants.MY_GROUPS);
        viewPagerAdapter.addFragment(userPageRecommendationsFragment, Constants.RECOMMENDATIONS);
        userPageViewPager.setAdapter(viewPagerAdapter);
        userPageTabLayout.setupWithViewPager(userPageViewPager);
    }

}
