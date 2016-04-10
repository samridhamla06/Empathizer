package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.LoginServices;


public class LoginPage extends AppCompatActivity {

    //GLOBAL VARIABLES
    public static final String VALID = "Valid";
    public static final String INVALID = "Invalid";
    public static final String URL = "http://192.168.2.2:8000/";

    //Other Objects
    private EditText userName;
    private EditText password;
    private String username_android;
    private String password_android;
    private LoginServices loginServices;
    private Intent intentToRegisterPage;
    private SharedPreferences sharedPreferences;
    private Intent intentToMainPage;


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

    private void instantiateSharedPreferencesAndIntent() {
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        intentToMainPage = new Intent(this, MainPage.class);
    }

    private void instantiateLocalVariables() {
        userName = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        loginServices = new LoginServices(this);
    }

    public void onLogIn(View view) {

        username_android = userName.getText().toString();//CREATE A FUNCTION FOR THIS
        password_android = password.getText().toString();
        loginServices.hitLogInRequest(username_android, password_android);
    }

    public void onRegister(View view) {
        intentToRegisterPage = new Intent(this, RegisterPage.class);
        startActivity(intentToRegisterPage);
    }

}
