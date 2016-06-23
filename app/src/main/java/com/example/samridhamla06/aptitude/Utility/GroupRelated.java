package com.example.samridhamla06.aptitude.Utility;

import android.content.SharedPreferences;

import com.example.samridhamla06.aptitude.Models.Group;
import com.google.gson.Gson;

/**
 * Created by samridhamla06 on 14/05/16.
 */
public class GroupRelated {

    public static Group getGroupObjectFromJson(String communityJsonString) {
        return new Gson().fromJson(communityJsonString, Group.class);
    }

    public static String getJsonFromGroupObject(Group newGroup) {
        return new Gson().toJson(newGroup);
    }
}
