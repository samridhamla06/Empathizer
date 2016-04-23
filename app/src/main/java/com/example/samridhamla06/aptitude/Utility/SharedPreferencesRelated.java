package com.example.samridhamla06.aptitude.Utility;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesRelated {

    public static SharedPreferences getInstanceOfSharedPreferences(Context context){
        return context.getSharedPreferences("PREFERENCES",Context.MODE_PRIVATE);
    }
}
