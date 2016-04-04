package com.example.samridhamla06.aptitude.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.samridhamla06.aptitude.R;

public class RegisterPage extends AppCompatActivity {

    private EditText name;
    private EditText username;
    private EditText password;
    private EditText location;
    private EditText age;
    private EditText occupation;
    private EditText disease;//drop box
    private EditText email;
    private EditText story;
    private EditText currentStatus;//drop box
    private RadioGroup gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        initializeLocalVariables();
    }

    private void initializeLocalVariables() {
        name = (EditText)findViewById(R.id.name);


    }


}
