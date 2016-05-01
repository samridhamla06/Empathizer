package com.example.samridhamla06.aptitude.Utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.samridhamla06.aptitude.Constants;
import com.example.samridhamla06.aptitude.Views.Activities.LoginPage;
import com.example.samridhamla06.aptitude.Views.Activities.MainPage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPageAsyncTaskRunner extends AsyncTask<String, String, String> {


    private JSONObject responseReceived;
    private ProgressDialog progressBar;
    private boolean RESPONSE_RECEIVED_FLAG = false;
    private String status;
    private String token;
    private SharedPreferences sharedPreferences;
    private final LoginPage loginPageReference;
    private Intent intentToMainPage;

    public LoginPageAsyncTaskRunner(LoginPage loginPageReference, ProgressDialog progressBar) {
        this.progressBar = progressBar;
        this.loginPageReference = loginPageReference;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if (values[0].equals(Constants.TIME_OUT_ERROR))
            Toast.makeText(loginPageReference, "Couldn't Connect to Server", Toast.LENGTH_SHORT).show();
        progressBar.dismiss();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            extractStatusAndToken();
            if (validateStatusAndToken()) {
                actOnResponse();
                logInToMainPage();
            } else
                Toast.makeText(loginPageReference, "Invalid UserName or Password", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.show();
    }

    @Override
    protected String doInBackground(String... params) {
        runTillResponseComes();
        publishProgress(Constants.VALID);
        return null;
    }

    private void actOnResponse() {

        Log.e("STATUS & TOKEN RECEIVED", status + "  ------   " + token);
        if (validateStatusAndToken()) {
            addResponseReceivedToSharedPreferences();
        } else {
            Toast.makeText(loginPageReference, "Invalid UserName or Password", Toast.LENGTH_LONG).show();
        }
    }

    private void runTillResponseComes() {
        while (!RESPONSE_RECEIVED_FLAG) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addResponseReceivedToSharedPreferences() {
        sharedPreferences = SharedPreferencesRelated.getInstanceOfSharedPreferences(loginPageReference);
        Log.d("TokenInsertion", "Passed");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.putString(Constants.USER_INFO, stringifyResponseReceived());
        editor.commit();
    }

    private String stringifyResponseReceived() {
        return responseReceived.toString();
    }

    private void extractStatusAndToken() throws JSONException {
        status = responseReceived.getString(Constants.STATUS);//COULD BE SHIFTED TO ANOTHER FUNCTION
        token = responseReceived.getString(Constants.TOKEN);
    }

    private boolean validateStatusAndToken() {
        return status.equals(Constants.VALID) && token != null;
    }

    public static LoginPageAsyncTaskRunner getInstance(LoginPage loginPageReference, ProgressDialog progressBar) {
        return new LoginPageAsyncTaskRunner(loginPageReference, progressBar);
    }

    private void logInToMainPage() {
        intentToMainPage = new Intent(loginPageReference, MainPage.class);
        intentToMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TO CALL startActivity from outside Context
        loginPageReference.startActivity(intentToMainPage);
        loginPageReference.finish();
    }

    public void acknowledgeAndSendResponse(JSONObject responseReceived) {
        this.responseReceived = responseReceived;
        this.RESPONSE_RECEIVED_FLAG = true;
    }
}
