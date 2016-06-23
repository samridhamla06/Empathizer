package com.example.samridhamla06.aptitude.Views.Activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Models.Group;
import com.example.samridhamla06.aptitude.Models.Location;
import com.example.samridhamla06.aptitude.Models.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.AddGroupPageServices;
import com.example.samridhamla06.aptitude.Utility.GroupRelated;
import com.example.samridhamla06.aptitude.Utility.SharedPreferencesRelated;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddGroupPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final int REQUEST_CODE_FOR_AUTO_COMPLETE = 2;
    private String TAG = AddGroupPage.class.getSimpleName();

    //TEXTVIEWS
    private EditText groupName;
    private TextView location;
    private EditText groupDescription;

    //LOCAL VARIABLES TO BE SENT
    private double latitude, longitude;

    //OTHER OBJECTS

    private Group newGroup;
    private AddGroupPageServices addGroupPageServices;
    private JSONObject groupJSONObject;
    private SharedPreferences sharedPreferences;
    private JSONArray userJsonArray;
    private User currentUser;
    private GoogleApiClient googleApiClient;
    private Intent intentToPlacesAutoComplete;
    private List<Double> coordinates;
    private List<User> users;
    private Location loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_group_page);
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        groupName = (EditText) findViewById(R.id.groupName);
        newGroup = new Group();
        location = (TextView) findViewById(R.id.location);
        onClickingLocation();
        groupDescription = (EditText) findViewById(R.id.groupDescription);
        groupJSONObject = new JSONObject();
        addGroupPageServices = new AddGroupPageServices(this);
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(getBaseContext());
        userJsonArray = new JSONArray();
        coordinates = new ArrayList<>();
        instantiateCurrentUser();
        instantiateUsersFieldForNewGroup();
        setUpGoogleApiClient();
    }

    private void instantiateUsersFieldForNewGroup() {
        users = new ArrayList<>();
        users.add(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_AUTO_COMPLETE) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlaceAutocomplete.getPlace(this, data);
                showAddressOnScreen(selectedPlace);
                setUpLocationParameters(selectedPlace);
            }
        }


    }

    private void showAddressOnScreen(Place selectedPlace) {
        location.setText(selectedPlace.getAddress().toString());
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "GOOGLE API CONNECTED");
    }

    @Override
    public void onConnectionSuspended(int i) {
        connectGoogleApiClient();
    }


    private void onClickingLocation() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    intentToPlacesAutoComplete = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(AddGroupPage.this);
                    startActivityForResult(intentToPlacesAutoComplete, REQUEST_CODE_FOR_AUTO_COMPLETE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUpLocationParameters(Place selectedPlace) {
        //retrieving location parameters
        LatLng latLng = selectedPlace.getLatLng();
        latitude = latLng.latitude;
        longitude = latLng.longitude;

        //saving them into variables
        setCoordinatesForThisGroup();
        instantiateLocField();
    }

    private void instantiateLocField() {
        loc = new Location(coordinates);
    }

    private void setCoordinatesForThisGroup() {
        coordinates.add(longitude);
        coordinates.add(latitude);
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
            Toast.makeText(this, "Google play services couldnt be connected", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void onClickingAddGroup(View view) {
        try {
            instantiateNewGroupObject();
            sendGroupDataToServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendGroupDataToServer() {
        String groupInfo = getGroupInfo();
        addGroupToServer(groupInfo);
    }

    private void addGroupToServer(String groupInfo) {
        Log.d("groupJSONObject_SENT", groupInfo);
        addGroupPageServices.addGroupToServer(groupInfo);
    }

    private String getGroupInfo() {
        return GroupRelated.getJsonFromGroupObject(newGroup);
    }

    private void instantiateNewGroupObject() throws JSONException {
        newGroup.setName(getValueForTextView(groupName));
        newGroup.setDescription(getValueForTextView(groupDescription));
        newGroup.setLocation(getValueForTextView(location));
        newGroup.setLoc(loc);
        newGroup.setSufferingName(getSufferingNameForCurrentUser());
        newGroup.setCreatorId(getCurrentUserId());
        newGroup.setUsers(users);
    }

    private String getCurrentUserId() {
        return UserRelated.getUserIdForCurrentUser(sharedPreferences);
    }

    private void populateUserJsonArray() throws JSONException {
        userJsonArray.put(new JSONObject(UserRelated.getJsonStringForCurrentUser(sharedPreferences)));
    }

    private void instantiateCurrentUser() {
        currentUser = getUserPOJOForCurrentUser();
}

    private User getUserPOJOForCurrentUser() {
        return UserRelated.getUserPOJOForCurrentUser(sharedPreferences);
    }

    private String getSufferingNameForCurrentUser() {
        return currentUser.getSufferingName();
    }


    private String getValueForTextView(TextView view) {
        return view.getText().toString();
    }


}
