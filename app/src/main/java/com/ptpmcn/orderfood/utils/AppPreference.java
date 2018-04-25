package com.ptpmcn.orderfood.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by tungts on 9/30/2017.
 */

public class AppPreference {

    private SharedPreferences preferences;
    private Gson mGson;

    public void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mGson = new GsonBuilder().create();
    }


}
