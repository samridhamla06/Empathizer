package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.LoginServices;
import com.example.samridhamla06.aptitude.Views.CommunityPage;
import com.example.samridhamla06.aptitude.Views.RegisterPage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPage extends AppCompatActivity {

    //GLOBAL VARIABLES
    public static final String VALID = "Valid";
    public static final String INVALID = "Invalid";
    public static final String URL = "http://192.168.2.2:8000/";
    public static final String STATUS = "status";
    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String SUFFERING_NAME = "sufferingName";
    public static final String GROUP_NAME = "groupName";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String LOCATION = "location";
    public static final String MY_GROUPS = "My Groups";
    public static final String NOTIFICATIONS = "Notifications";


    //Other Objects
    private EditText email;
    private EditText password;
    private String email_android;
    private String password_android;
    private LoginServices loginServices;
    private Intent intentToRegisterPage;
    private SharedPreferences sharedPreferences;
    private Intent intentToMainPage;
    private Intent intentToCommunityPage;
    private JSONObject userLoginDetails;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        instantiateSharedPreferencesAndIntent();
        if (sharedPreferences.contains("token")) {
            startActivity(intentToMainPage);
            finish();
        } else {
            instantiateLocalVariables();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        return true;
    }

    private void instantiateSharedPreferencesAndIntent() {
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        intentToMainPage = new Intent(this, MainPage.class);
    }

    private void instantiateLocalVariables() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginServices = new LoginServices(this);
        userLoginDetails = new JSONObject();
        toolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
    }

    public void onLogIn(View view) {
        email_android = email.getText().toString();//CREATE A FUNCTION FOR THIS
        password_android = password.getText().toString();
        prepareJSONObjectToSend(email_android, password_android);
        loginServices.hitLogInRequest(userLoginDetails);
    }

    public void onRegister(View view) {
        intentToRegisterPage = new Intent(this, RegisterPage.class);
        startActivity(intentToRegisterPage);
    }

    private void prepareJSONObjectToSend(String email_android, String password_android) {
        try {
            userLoginDetails.put(LoginPage.EMAIL, email_android);
            userLoginDetails.put(LoginPage.PASSWORD, password_android);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onLogInAsGuest(View view) {
        intentToCommunityPage = new Intent(getBaseContext(), CommunityPage.class);
        startActivity(intentToCommunityPage);
    }

}
