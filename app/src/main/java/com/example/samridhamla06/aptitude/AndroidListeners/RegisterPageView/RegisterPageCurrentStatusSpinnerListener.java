package com.example.samridhamla06.aptitude.AndroidListeners.RegisterPageView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.samridhamla06.aptitude.Views.RegisterPage;


public class RegisterPageCurrentStatusSpinnerListener implements AdapterView.OnItemSelectedListener {

    private final RegisterPage registerPage;

    public RegisterPageCurrentStatusSpinnerListener(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String currentStatus = parent.getItemAtPosition(position).toString();
        Log.d("STATUS_SELECTED", currentStatus);
        registerPage.setCurrentStatus(currentStatus);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        registerPage.setCurrentStatus(parent.getItemAtPosition(0).toString());
    }
}
