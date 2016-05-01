package com.example.samridhamla06.aptitude.Views.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.LoginServices;
import com.example.samridhamla06.aptitude.Utility.LoginPageAsyncTaskRunner;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPage extends AppCompatActivity {


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
    private ProgressDialog progressBar;
    private LoginPageAsyncTaskRunner loginPageAsyncTaskRunner;
    private Boolean RESPONSE_RECEIVED_FLAG = false;
    private JSONObject responseReceived;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        instantiateSharedPreferencesAndIntent();
        if (containsToken()) {
            startActivity(intentToMainPage);
            finish();
        } else {
            instantiateLocalVariables();
        }
    }

    private boolean containsToken() {
        return sharedPreferences.contains("token");
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
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(getBaseContext());
        intentToMainPage = new Intent(this, MainPage.class);
    }

    private void instantiateLocalVariables() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginServices = new LoginServices(this);
        userLoginDetails = new JSONObject();
        responseReceived = new JSONObject();
        toolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        progressBar = new ProgressDialog(this);
        setUpProgressBar();
    }

    private void setUpProgressBar() {
        progressBar.setMessage("Loading... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);

    }

    public void onLogIn(View view) {
        loginPageAsyncTaskRunner = LoginPageAsyncTaskRunner.getInstance(this, progressBar);
        loginPageAsyncTaskRunner.execute();
        email_android = getToString(email);//CREATE A FUNCTION FOR THIS
        password_android = getToString(password);
        prepareJSONObjectToSend(email_android, password_android);
        loginServices.hitLogInRequest(userLoginDetails);
    }

    private String getToString(EditText editText) {
        return editText.getText().toString();
    }

    public void onRegister(View view) {
        intentToRegisterPage = new Intent(this, RegisterPage.class);
        startActivity(intentToRegisterPage);
    }

    private void prepareJSONObjectToSend(String email_android, String password_android) {
        try {
            userLoginDetails.put(Constants.EMAIL, email_android);
            userLoginDetails.put(Constants.PASSWORD, password_android);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onLogInAsGuest(View view) {
        intentToCommunityPage = new Intent(getBaseContext(), CommunityPage.class);
        startActivity(intentToCommunityPage);
    }

    public void sendResponseReceivedToLoginPage(JSONObject responseReceived) {
        this.responseReceived = responseReceived;
        RESPONSE_RECEIVED_FLAG = true;//to intimate asynctaskrunner
        loginPageAsyncTaskRunner.acknowledgeAndSendResponse(responseReceived);
    }


}
