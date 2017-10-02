package com.codepath.assignment.mytweets.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by saip92 on 10/1/2017.
 */

public class QueryPreferences {

    private static final String SAVE_USER_TOKEN = "SAVE_USER_TOKEN";


    public static String getCurrentUserSessionId(Context context, String userToken){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SAVE_USER_TOKEN,userToken);
    }

    public static void setCurrentUserSessionId(Context context, String userToken){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SAVE_USER_TOKEN,userToken)
                .apply();
    }
}
