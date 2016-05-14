package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.samridhamla06.aptitude.Adapters.ViewPagerAdapter;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;
import com.example.samridhamla06.aptitude.Utility.UserRelated;
import com.example.samridhamla06.aptitude.Views.Fragments.MainPageMyGroupsFragment;
import com.example.samridhamla06.aptitude.Views.Fragments.MainPageNotificationsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private static final String TAG = MainPage.class.getSimpleName();
    private Toolbar mainPageToolBar;
    private ViewPager mainPageViewPager;
    private TabLayout mainPageTabLayout;
    private Intent intentToLoginPage;
    private SharedPreferences sharedPreferences;
    private Button currentLocationView;
    private double latitude = Constants.DEFAULT_LATITUDE_VALUE;
    private double longitude = Constants.DEFAULT_LONGITUDE_VALUE;
    private Geocoder geocoder;
    private Address address;



    //GOOGLE API OBJECTS
    private GoogleApiClient googleApiClient;
    private Intent intentToAddGroupPage;


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
        setUpToolBar();
        mainPageViewPager = (ViewPager) findViewById(R.id.main_page_viewPager);
        mainPageTabLayout = (TabLayout) findViewById(R.id.main_page_tabLayout);
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(getBaseContext());
        initialiseViewPagerAndLinkWithTabLayout();
        intentToLoginPage = new Intent(this, LoginPage.class);
        currentLocationView = (Button) findViewById(R.id.currentLocationView);
        intentToAddGroupPage = new Intent(getBaseContext(), AddGroupPage.class);
        geocoder = new Geocoder(this, Locale.getDefault());
        setUpGoogleApiClient();
        setValueForCurrentLocation();
    }

    private void setUpToolBar() {
        setSupportActionBar(mainPageToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setValueForCurrentLocation() {
        validateAndInitialiseLatitudeAndLongitude();
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addressList.isEmpty()) {
                address = addressList.get(0);//you get one address list
                String locality = address.getLocality();
                currentLocationView.setText(Constants.LOCATION + " : " + locality);
                Toast.makeText(this, "you are in " + locality, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Latitude longitude not stored :(", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateAndInitialiseLatitudeAndLongitude() {
        latitude = UserRelated.getLatitudeForCurrentUser(sharedPreferences);
        longitude = UserRelated.getLongitudeForCurrentUser(sharedPreferences);
    }


    private void setUpGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        connectGoogleApiClient();
    }

    private void connectGoogleApiClient() {
        if (checkGooglePlayServices()) {
            googleApiClient.connect();
        }
    }

    private boolean checkGooglePlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            Toast.makeText(getApplicationContext(), "This device is not supported. ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
            case R.id.addGroup:
                Toast.makeText(this, "Add group Selected", Toast.LENGTH_LONG).show();
                onAddGroup();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_FOR_ADD_GROUP_PAGE) {
            refreshPage();
        }
    }

    public void onCurrentLocationClicked(View view) {
        //start new Activity Place Auto complete
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        connectGoogleApiClient();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, Constants.REQUEST_CODE_FOR_RESOLUTION_GOOGLE_API);
        } catch (IntentSender.SendIntentException e) {
            Log.d(TAG, "GOOGLE API disconnected");
            e.printStackTrace();
        }
    }

    public void onAddGroup() {
        startActivityForResult(intentToAddGroupPage, Constants.REQUEST_CODE_FOR_ADD_GROUP_PAGE);
    }

}

