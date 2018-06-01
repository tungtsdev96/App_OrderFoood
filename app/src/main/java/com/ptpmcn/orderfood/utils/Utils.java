package com.ptpmcn.orderfood.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by tungts on 11/13/2017.
 */

public class Utils {

    public static boolean checkValuesInt(Object values){
        return Pattern.matches("\\d+", (CharSequence) values);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
