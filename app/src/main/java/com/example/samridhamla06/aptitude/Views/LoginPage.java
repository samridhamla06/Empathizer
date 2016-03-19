package com.example.samridhamla06.aptitude.Views;

import android.content.Context;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.LoginServices;


public class LoginPage extends AppCompatActivity {

    private  EditText userName;
    private  EditText password;
    private Context context;
    private String username_android;
    private String password_android;
    private LoginServices loginServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        instantiateLocalVariables();
    }

    private void instantiateLocalVariables() {
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        context = getApplicationContext();
        loginServices = new LoginServices(context);
    }

    public void onLogIn(View view){

         username_android = userName.getText().toString();//CREATE A FUNCTION FOR THIS
         password_android = password.getText().toString();
         loginServices.hitLogInRequest(username_android, password_android);

    }

}
