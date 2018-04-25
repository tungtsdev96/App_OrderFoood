package com.ptpmcn.orderfood.googlemap;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.ptpmcn.orderfood.activity.AddLocationActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by tungts on 12/5/2017.
 */

public class GoogleMapUtils {

    public static Address getAddress(Activity current_activity, double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(current_activity, Locale.getDefault());

        try {
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getAddress(Activity current_activity, Location location) {
        Address locationAddress = getAddress(current_activity, location.getLatitude(), location.getLongitude());
        String currentLocation = null;

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();



            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;
                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;
            }

        }
        return currentLocation;
    }

    public synchronized static LatLng getAddress(Context context,String nameAddress){

        Geocoder geocoder = new Geocoder(context);
        List<Address> listAddress;
        LatLng latLng = null;

        try {
            listAddress = geocoder.getFromLocationName(nameAddress,5);
            if (listAddress == null){
                return null;
            }

            Address address = listAddress.get(0);
            latLng = new LatLng(address.getLatitude(),address.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

}
