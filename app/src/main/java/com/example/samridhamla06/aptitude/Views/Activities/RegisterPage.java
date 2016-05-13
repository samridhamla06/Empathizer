package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samridhamla06.aptitude.AndroidListeners.RegisterPageView.RegisterPageCurrentStatusSpinnerListener;
import com.example.samridhamla06.aptitude.AndroidListeners.RegisterPageView.RegisterPageSufferingSpinnerListener;
import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.RegisterPageServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = RegisterPage.class.getSimpleName();
    private static final int REQUEST_CODE_FOR_AUTO_COMPLETE = 2;
    //TEXT VIEWS-----------------
    private EditText userName;
    private EditText password;
    private TextView location;
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
    private double latitude;
    private double longitude;

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
    private Intent intentToPlaceAutoComplete;


    //GOOGLE API OBJECTS
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        initializeLocalVariables();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_AUTO_COMPLETE) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlaceAutocomplete.getPlace(this, data);
                Log.d(TAG + " Name", selectedPlace.getName().toString());
                Log.d(TAG + " latlng", selectedPlace.getLatLng().toString());
                Toast.makeText(getApplicationContext(), "AUTO-Address: " + selectedPlace.getName(), Toast.LENGTH_LONG).show();
                saveLocationParameters(selectedPlace);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Toast.makeText(getApplicationContext(), "Error occured while retrieving Address from Google Auto Complete", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void saveLocationParameters(Place selectedPlace) {
        location.setText(selectedPlace.getAddress().toString());
        latitude = selectedPlace.getLatLng().latitude;
        longitude = selectedPlace.getLatLng().longitude;
    }

    private void initializeLocalVariables() {
        email = (EditText) findViewById(R.id.email);
        userName = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        location = (TextView) findViewById(R.id.location);
        location.setText("CHOOSE YOUR LOCATION");
        age = (EditText) findViewById(R.id.age);
        registerPageSufferingSpinnerListener = new RegisterPageSufferingSpinnerListener(this);
        registerPageCurrentStatusSpinnerListener = new RegisterPageCurrentStatusSpinnerListener(this);
        sufferingsDropMenu = (Spinner) findViewById(R.id.suffering);
        sufferingsDropMenu.setOnItemSelectedListener(registerPageSufferingSpinnerListener);
        currentStatusDropDown = (Spinner) findViewById(R.id.status);
        currentStatusDropDown.setOnItemSelectedListener(registerPageCurrentStatusSpinnerListener);
        aboutMe = (EditText) findViewById(R.id.aboutMe);
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        gender_male = (RadioButton) findViewById(R.id.male);
        gender_female = (RadioButton) findViewById(R.id.female);
        gender_other = (RadioButton) findViewById(R.id.other);
        user_JSON_object = new JSONObject();
        intentToLoginPage = new Intent(getBaseContext(), LoginPage.class);
        setUpGoogleApiClient();
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

    public void onLocationClicked(View view) {
        try {
            intentToPlaceAutoComplete = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intentToPlaceAutoComplete, REQUEST_CODE_FOR_AUTO_COMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
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

    public void onSignUp(View view) {
        try {
            mapUserInfoToUserJSONObject();
            sendUserDataToServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void sendUserDataToServer() {
        Log.d("JSON_SENT", user_JSON_object.toString());
        instantiateRegisterPageServices();
        registerPageServices.sendUserDataToServer();
    }



    private void instantiateRegisterPageServices() {
        registerPageServices = new RegisterPageServices(this, user_JSON_object);
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
        user_JSON_object.put(Constants.LATITUDE,latitude);
        user_JSON_object.put(Constants.LONGITUDE,longitude);
    }


    private String getValueForRadioGroup(RadioGroup genderGroup) {
        int selectedId = genderGroup.getCheckedRadioButtonId();
        if (selectedId == gender_male.getId()) {
            return MALE;
        } else if (selectedId == gender_female.getId()) {
            return FEMALE;
        } else if (selectedId == gender_other.getId()) {
            return OTHER;
        } else {
            return null;
        }
    }

    private String getValueForEditTextView(TextView view) {
        return view.getText().toString();
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setSuffering(String suffering) {
        this.suffering = suffering;
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "GOOGLE API CONNECTED");
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
}