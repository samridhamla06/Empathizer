package com.example.samridhamla06.aptitude.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by samridhamla06 on 17/04/16.
 */
public class SharedPreferencesRelated {

    public static SharedPreferences getInstanceOfSharedPreferences(Context context){
        return context.getSharedPreferences("PREFERENCES",Context.MODE_PRIVATE);
    }
}
