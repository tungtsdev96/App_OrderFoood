package com.ptpmcn.orderfood.googlemap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.restaurant.DetailRestaurantActivity;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.google.PathGoogleResponse;
import com.ptpmcn.orderfood.utils.constant.Constant;

/**
 * Created by tungts on 12/11/2017.
 */

public class CurrentLocation implements PermissionUtils.PermissionResultCallback ,OnSuccessListener<LocationSettingsResponse>, OnFailureListener {

    public interface CurrentAddress{
        void currentLocation(String address);
    }

    CurrentAddress currentAddress;

    Context context;

    public CurrentLocation(Context context,CurrentAddress currentLocation) {
        this.context = context;
        this.currentAddress = currentLocation;
        setUpMyLocation(context);
    }

    boolean mLocationPermissionsGranted;
    private Task<LocationSettingsResponse> task;
    private SettingsClient mSettingsClient;
    private FusedLocationProviderClient mFusedLocationClient;

    private void setUpMyLocation(Context context) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mSettingsClient = LocationServices.getSettingsClient(context);

        createLocationCallback();
        buidLocationRequest();
        buildLocationSettingsRequest();

        PermissionUtils permissionUtils = new PermissionUtils(context, this);
        permissionUtils.checkPermission();
    }

    private void getUpdateCurrentLocation() {
        if (mLocationPermissionsGranted) {
            try {
                task = mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                        .addOnSuccessListener((Activity) context, this)
                        .addOnFailureListener((Activity) context, this);
                task.isSuccessful();
            } catch (SecurityException e) {

            }
        }
    }

    private void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private LocationCallback mLocationCallback;

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                String address = GoogleMapUtils.getAddress((Activity) context,location);
                if (currentAddress != null){
                    currentAddress.currentLocation(address);
                }
                stopLocationUpdates();
            }
        };
    }

    LocationSettingsRequest mLocationSettingsRequest;
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    LocationRequest mLocationRequest;
    private void buidLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void permissionGranted() {
        mLocationPermissionsGranted = true;
        getUpdateCurrentLocation();
    }

    @Override
    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }

    private final static int REQUEST_CHECK_SETTINGS = 2000;
    @Override
    public void onFailure(@NonNull Exception e) {
        int statusCode = ((ApiException) e).getStatusCode();
        switch (statusCode) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    ResolvableApiException rae = (ResolvableApiException) e;
                    rae.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException ignored) {
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }
}
