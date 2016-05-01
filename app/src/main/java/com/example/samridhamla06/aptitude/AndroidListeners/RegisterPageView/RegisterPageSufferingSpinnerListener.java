package com.example.samridhamla06.aptitude.AndroidListeners.RegisterPageView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.samridhamla06.aptitude.Views.Activities.RegisterPage;

public class RegisterPageSufferingSpinnerListener implements AdapterView.OnItemSelectedListener {
    private RegisterPage registerPage;
    public RegisterPageSufferingSpinnerListener(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sufferingName = parent.getItemAtPosition(position).toString();
        Log.d("SUFFERING_SELECTED", sufferingName);
        registerPage.setSuffering(sufferingName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        registerPage.setSuffering(parent.getItemAtPosition(0).toString());
    }
}
