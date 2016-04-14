package com.example.samridhamla06.aptitude.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.AddGroupPageServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddGroupPage extends AppCompatActivity {

    //TEXTVIEWS
    private EditText groupName;
    private EditText location;
    private EditText groupDescription;

    //OTHER OBJECTS
    private AddGroupPageServices addGroupPageServices;
    private JSONObject groupJSONObject;
    private SharedPreferences sharedPreferences;
    private JSONArray userJsonArray;
    //CONSTANTS
    private final String GROUP_NAME = LoginPage.GROUP_NAME;
    private final String LOCATION = "location";
    private final String GROUP_DESCRIPTION = "description";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_page);
        initialiseLocalVariables();
    }

    private void initialiseLocalVariables() {
        groupName = (EditText)findViewById(R.id.groupName);
        location = (EditText)findViewById(R.id.location);
        groupDescription = (EditText)findViewById(R.id.groupDescription);
        groupJSONObject = new JSONObject();
        addGroupPageServices = new AddGroupPageServices(this);
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        userJsonArray = new JSONArray();
    }

    public void onClickingAddGroup(View view) {
        try {
            mapGroupInfoToGroupJSONObject();
            sendGroupDataToServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendGroupDataToServer() {
        Log.d("groupJSONObject_SENT",groupJSONObject.toString());
        addGroupPageServices.addGroupToServer(groupJSONObject);
    }


    private void mapGroupInfoToGroupJSONObject() throws JSONException {
        groupJSONObject.put(GROUP_NAME,getValueForEditTextView(groupName));
        groupJSONObject.put(LOCATION,getValueForEditTextView(location));
        groupJSONObject.put(GROUP_DESCRIPTION, getValueForEditTextView(groupDescription));

        addAdditionalInfoToGroupJSONObject();
    }

    private void addAdditionalInfoToGroupJSONObject() throws JSONException{
        groupJSONObject.put(LoginPage.SUFFERING_NAME,sharedPreferences.getString(LoginPage.SUFFERING_NAME, "000"));
        mapUserInfoToUserJSONArray();
        groupJSONObject.put("users", userJsonArray);
    }

    private void mapUserInfoToUserJSONArray() throws JSONException{
        userJsonArray.put(createUserJSONObject());
    }

    private JSONObject createUserJSONObject() throws JSONException{
        JSONObject userJson = new JSONObject();
        String userName = sharedPreferences.getString(LoginPage.USER_NAME, "000");
        String email = sharedPreferences.getString(LoginPage.EMAIL, "000");
        String suffering = sharedPreferences.getString(LoginPage.SUFFERING_NAME,"000");
        String userId = sharedPreferences.getString(LoginPage.USER_ID,"000");
        String user_location = sharedPreferences.getString(LoginPage.LOCATION, "000");
        userJson.put(LoginPage.USER_NAME,userName);
        userJson.put(LoginPage.EMAIL,email);
        userJson.put(LoginPage.SUFFERING_NAME,suffering);
        userJson.put(LoginPage.USER_ID,userId);
        userJson.put(LoginPage.LOCATION,user_location);
        return userJson;
    }




    private String getValueForEditTextView(EditText view) {
        return view.getText().toString();
    }

}
