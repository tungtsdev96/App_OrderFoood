package com.ptpmcn.orderfood.googlemap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ptpmcn.orderfood.utils.constant.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.ptpmcn.orderfood.utils.constant.Constant.RequestCode.LOCATION_PERMISSION_REQUEST_CODE;

/**
 * Created by tungts on 12/5/2017.
 */

public class PermissionUtils {

    Context context;
    Activity current_activity;
    PermissionResultCallback permissionResultCallback;

    public PermissionUtils(Context context, PermissionResultCallback permissionResultCallback) {
        this.context = context;
        this.current_activity = (Activity) context;
        this.permissionResultCallback = permissionResultCallback;
    }

    public void checkPermission() {

        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(current_activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(current_activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (permissionResultCallback != null) {
                            permissionResultCallback.permissionGranted();
                        }
                        Log.i("all permissions", "granted");
                    } else {
                        ActivityCompat.requestPermissions(current_activity, permissions, LOCATION_PERMISSION_REQUEST_CODE);
                    }
                } else {
                    ActivityCompat.requestPermissions(current_activity, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            if (permissionResultCallback != null) {
                permissionResultCallback.permissionGranted();
            }
            Log.i("all permissions", "granted");
        }
    }

    public interface PermissionResultCallback {
        void permissionGranted();
    }

}
