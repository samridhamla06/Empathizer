package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.example.samridhamla06.aptitude.AndroidListeners.RegisterPageView.RegisterPageCurrentStatusSpinnerListener;
import com.example.samridhamla06.aptitude.AndroidListeners.RegisterPageView.RegisterPageSufferingSpinnerListener;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.RegisterPageServices;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage extends AppCompatActivity {

    //TEXT VIEWS-----------------
    private EditText userName;
    private EditText password;
    private EditText location;
    private EditText age;
    private EditText email;
    private EditText aboutMe;

    //SPINNERS,BUTTONS-----------------
    private Spinner currentStatusDropDown;//drop box
    private Spinner sufferingsDropMenu;//drop box
    private RadioGroup genderGroup;
    private RadioButton gender_male;
    private RadioButton gender_female;
    private RadioButton gender_other;

    //VARIABLES-------------------
    private String currentStatus;
    private String suffering;

    //CONSTANTS--------------------
    private final String MALE = "Male";
    private final String FEMALE = "Female";
    private final String OTHER = "Other";


    //OTHER OBJECTS----------
    private JSONObject user_JSON_object;
    private RegisterPageSufferingSpinnerListener registerPageSufferingSpinnerListener;
    private RegisterPageCurrentStatusSpinnerListener registerPageCurrentStatusSpinnerListener;
    private RegisterPageServices registerPageServices;
    private Intent intentToLoginPage;


    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    public void setSuffering(String suffering) {
        this.suffering = suffering;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        initializeLocalVariables();
    }

    private void initializeLocalVariables() {
        email = (EditText)findViewById(R.id.email);
        userName = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        location = (EditText)findViewById(R.id.location);
        age = (EditText)findViewById(R.id.age);
        registerPageSufferingSpinnerListener = new RegisterPageSufferingSpinnerListener(this);
        registerPageCurrentStatusSpinnerListener = new RegisterPageCurrentStatusSpinnerListener(this);
        sufferingsDropMenu = (Spinner)findViewById(R.id.suffering);
        sufferingsDropMenu.setOnItemSelectedListener(registerPageSufferingSpinnerListener);
        currentStatusDropDown = (Spinner)findViewById(R.id.status);
        currentStatusDropDown.setOnItemSelectedListener(registerPageCurrentStatusSpinnerListener);
        aboutMe =(EditText)findViewById(R.id.aboutMe);
        genderGroup = (RadioGroup)findViewById(R.id.gender);
        gender_male = (RadioButton)findViewById(R.id.male);
        gender_female = (RadioButton)findViewById(R.id.female);
        gender_other = (RadioButton)findViewById(R.id.other);
        user_JSON_object = new JSONObject();
        intentToLoginPage = new Intent(getBaseContext(),LoginPage.class);
    }

    public void onSignUp(View view){
        try {
            mapUserInfoToUserJSONObject();
            sendUserDataToServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void sendUserDataToServer() {
        Log.d("JSON_SENT",user_JSON_object.toString());
        instantiateRegisterPageServices();
        registerPageServices.sendUserDataToServer();
    }

    private void instantiateRegisterPageServices() {
        registerPageServices = new RegisterPageServices(this,user_JSON_object);
    }

    private void mapUserInfoToUserJSONObject() throws JSONException {
        user_JSON_object.put(Constants.USER_NAME, getValueForEditTextView(userName));
        user_JSON_object.put("password", getValueForEditTextView(password));
        user_JSON_object.put("location", getValueForEditTextView(location));
        user_JSON_object.put("age", getValueForEditTextView(age));
        user_JSON_object.put("email", getValueForEditTextView(email));
        user_JSON_object.put("aboutMe", getValueForEditTextView(aboutMe));
        user_JSON_object.put(Constants.SUFFERING_NAME, suffering);
        user_JSON_object.put("currentStatus", currentStatus);
        user_JSON_object.put("gender", getValueForRadioGroup(genderGroup));//radio
    }


    private String getValueForRadioGroup(RadioGroup genderGroup) {
        int selectedId = genderGroup.getCheckedRadioButtonId();
        if(selectedId == gender_male.getId()){
            return MALE;
        }else if(selectedId == gender_female.getId()){
            return FEMALE;
        }else if(selectedId == gender_other.getId()){
            return OTHER;
        }else{
            return null;
        }
    }

    private String getValueForEditTextView(EditText view) {
         return view.getText().toString();
    }


    public void resetAllViews() {
        //userName.setText(null);
        password.setText(null);
        //location.setText(null);
       // age.setText(null);
        email.setText(null);
        //aboutMe.setText(null);
        password.requestFocus();
    }

    public void goToLogInPage() {
        startActivity(intentToLoginPage);
    }
}
