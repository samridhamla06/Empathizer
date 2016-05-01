package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Intent;
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
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;
import com.example.samridhamla06.aptitude.Views.Fragments.MainPageMyGroupsFragment;
import com.example.samridhamla06.aptitude.Views.Fragments.MainPageNotificationsFragment;


public class MainPage extends AppCompatActivity {


    private Toolbar mainPageToolBar;
    private ViewPager mainPageViewPager;
    private TabLayout mainPageTabLayout;
    private Intent intentToLoginPage;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initialiseLocalVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    private void initialiseLocalVariables() {

        mainPageToolBar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mainPageToolBar);
        mainPageViewPager = (ViewPager) findViewById(R.id.main_page_viewPager);
        mainPageTabLayout = (TabLayout) findViewById(R.id.main_page_tabLayout);
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(getBaseContext());
        initialiseViewPagerAndLinkWithTabLayout();
        intentToLoginPage = new Intent(this, LoginPage.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();

        switch (selected) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_LONG).show();
                return true;

            case R.id.logoutButton:
                Toast.makeText(this, "Logging Out", Toast.LENGTH_LONG).show();
                onLogOut();
                return true;

            case R.id.refreshButton:
                Toast.makeText(this, "Refreshing", Toast.LENGTH_LONG).show();
                onRefresh();
                return true;

            default:
                Toast.makeText(this, "Maa chuda", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void onLogOut() {

        //--------------------------------------------------------ALERT BOX
        refreshSharedPreferences();//-----------------------------VERY IMPORTANT OTHERWISE YOU WILL LAND HERE ONLY EVERYTIME.
        startActivity(intentToLoginPage);
        finish();
    }


    private void initialiseViewPagerAndLinkWithTabLayout() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MainPageMyGroupsFragment(), Constants.MY_GROUPS);
        viewPagerAdapter.addFragment(new MainPageNotificationsFragment(), Constants.NOTIFICATIONS);
        mainPageViewPager.setAdapter(viewPagerAdapter);
        mainPageTabLayout.setupWithViewPager(mainPageViewPager);
    }


    private void refreshSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void onRefresh() {
        refreshPage();
    }

    private void refreshPage() {
        startActivity(getIntent());
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_FOR_ADD_GROUP_PAGE) {
            refreshPage();
        }
    }



}
