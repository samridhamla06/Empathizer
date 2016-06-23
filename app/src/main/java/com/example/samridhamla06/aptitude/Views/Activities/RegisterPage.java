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
import com.example.samridhamla06.aptitude.Models.Location;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.RegisterPageServices;
import com.example.samridhamla06.aptitude.Utility.UserRelated;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
    private User currentUser;
    private Location currentUserLoc;
    private List<Double> coordinates;
    private String suffering;
    private String currentStatus;
    private double latitude;
    private double longitude;


    //CONSTANTS--------------------
    private final String MALE = "Male";
    private final String FEMALE = "Female";
    private final String OTHER = "Other";


    //OTHER OBJECTS----------
    private RegisterPageSufferingSpinnerListener registerPageSufferingSpinnerListener;
    private RegisterPageCurrentStatusSpinnerListener registerPageCurrentStatusSpinnerListener;
    private RegisterPageServices registerPageServices;
    private Intent intentToLoginPage;
    private Intent intentToPlaceAutoComplete;


    //GOOGLE API OBJECTS
    private GoogleApiClient googleApiClient;
    private String city;


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
                showLocationValueOnScreen(selectedPlace.getAddress().toString());
                saveLocationParameters(selectedPlace);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Toast.makeText(getApplicationContext(), "Error occured while retrieving Address from Google Auto Complete", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void saveLocationParameters(Place selectedPlace) {
        //retrieving values linked to location from Place object
        LatLng latLng = selectedPlace.getLatLng();
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        city = selectedPlace.getName().toString();
        instantiateCoordinatesList(latitude, longitude);
        currentUserLoc = new Location(coordinates);
    }

    private void instantiateCoordinatesList(double latitude, double longitude) {
        coordinates = new ArrayList<>();
        coordinates.add(longitude);
        coordinates.add(latitude);
    }

    private void showLocationValueOnScreen(String text) {
        location.setText(text);
    }

    private void initializeLocalVariables() {
        currentUser = new User();
        email = (EditText) findViewById(R.id.email);
        userName = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        location = (TextView) findViewById(R.id.location);
        showLocationValueOnScreen("CHOOSE YOUR LOCATION");
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
        intentToLoginPage = new Intent(getBaseContext(), LoginPage.class);
        registerPageServices = new RegisterPageServices(this);
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
            String userInfo = getUserInfo();
            sendUserDataToServer(userInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void sendUserDataToServer(String userInfo) {
        Log.d(TAG + " JSON_SENT", userInfo);
        registerPageServices.sendUserDataToServer(userInfo);
    }


    public void setSuffering(String suffering) {
        this.suffering = suffering;
    }


    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    private String getUserInfo() throws JSONException {
        instantiateCurrentUser();
        return UserRelated.convertUserObjectToJsonString(currentUser);
    }

    private void instantiateCurrentUser() {
        currentUser.setAboutMe(getValueForEditTextView(aboutMe));
        currentUser.setPassword(getValueForEditTextView(password));
        currentUser.setName(getValueForEditTextView(userName));
        currentUser.setLocation(getValueForEditTextView(location));
        currentUser.setAge(Integer.valueOf(getValueForEditTextView(age)));
        currentUser.setEmail(getValueForEditTextView(email));
        currentUser.setSufferingName(suffering);
        currentUser.setCurrentStatus(currentStatus);
        //location parameters set up via onActivityResult Method from Auto-Complete googl api
        currentUser.setLatitude(latitude);
        currentUser.setLongitude(longitude);
        currentUser.setCity(city);
        currentUser.setLoc(currentUserLoc);
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
