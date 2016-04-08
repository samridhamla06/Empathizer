package com.example.samridhamla06.aptitude.Views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


import com.example.samridhamla06.aptitude.Modals.User;
import com.example.samridhamla06.aptitude.R;
import com.example.samridhamla06.aptitude.Service.UserPageServices;

public class UserPage extends AppCompatActivity {

    public static final String USER_ID = "USER_ID";
    private UserPageServices userPageServices;
    private long userId;
    private TextView name ;
    private TextView aboutme ;
    private TextView location ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        initialiseLocalVariables();
        retrieveInfoFromServer();
    }

    private void retrieveInfoFromServer() {
        userPageServices.retrieveInfoFromServer();
    }

  /*  public void mapReceivedUserToPageView() {
        if(userSelected!=null){
            fillAllTextViews(userSelected);
       }else{
            Toast.makeText(getBaseContext(),"No response received from Server",Toast.LENGTH_LONG).show();
        }
    }*/

    public void fillAllTextViews(User userSelected) {
        name.setText(userSelected.getName());
        aboutme.setText("My age is /n/n/n" + Integer.toString(userSelected.getAge()));
        location.setText(userSelected.getLocation());
    }

    private void initialiseLocalVariables() {
        userId = getIntent().getLongExtra(USER_ID,0);
        Log.d("USER_ID_SELECTED", Long.toString(userId));
        userPageServices = new UserPageServices(this,userId);
        name = (TextView)findViewById(R.id.name);
        aboutme = (TextView)findViewById(R.id.aboutMe);
        location = (TextView)findViewById(R.id.location);
    }

}
