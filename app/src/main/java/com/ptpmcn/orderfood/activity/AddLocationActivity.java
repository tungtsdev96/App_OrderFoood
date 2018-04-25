package com.ptpmcn.orderfood.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.googlemap.GoogleMapUtils;
import com.ptpmcn.orderfood.googlemap.PermissionUtils;
import com.ptpmcn.orderfood.model.AddressOrder;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.constant.Constant;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 12/2/2017.
 */

public class AddLocationActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback,
        PermissionUtils.PermissionResultCallback, OnSuccessListener<LocationSettingsResponse>,
        OnFailureListener {

    ImageView img_back, img_current_location,img_search;
    TextView tv_search_location;
    TextView btn_add_location;
    EditText edt_address;


    GoogleMap map;
    Location mLastLocation;
    String addressCurrentLocation;
    boolean mLocationPermissionsGranted;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_location;
    }

    @Override
    protected void addControls() {
        edt_address = (EditText) findViewById(R.id.edt_address);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_current_location = (ImageView) findViewById(R.id.img_current_location);
        img_search = (ImageView) findViewById(R.id.img_search);
        tv_search_location = (TextView) findViewById(R.id.tv_search_location);
        btn_add_location = (TextView) findViewById(R.id.btn_add_location);
        innitMap();
        addEvents();
    }

    private void addEvents() {
        img_back.setOnClickListener(this);
        img_current_location.setOnClickListener(this);
        btn_add_location.setOnClickListener(this);
        edt_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("edt",b+"");
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_address.getText().toString();
                if (name != null){
                    stopLocationUpdates();
                    LatLng latLng = GoogleMapUtils.getAddress(AddLocationActivity.this,name);
                    addressCurrentLocation  = name;
                    moveCamera(latLng,15f,name);
                    hideKeyboard();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.img_current_location:
                getUpdateCurrentLocation();
                break;
            case R.id.btn_add_location:
                addLocation();
                break;
        }
    }

    private void addLocation() {
        if (addressCurrentLocation != null){
            //post location
            final AddressOrder addressOrder = new AddressOrder();
            addressOrder.setCustomer_id(AccountUtil.fakeCustomer().getCustomer_id());
            addressOrder.setAddress(addressCurrentLocation);
            ApiFactory.getApiCustomer().addLocation(AccountUtil.fakeCustomer().getCustomer_id(),addressCurrentLocation).enqueue(new BaseCallBack<ResponseBody>(this) {
                @Override
                public void onSuccess(ResponseBody result) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Address",addressOrder);
                    intent.putExtras(bundle);
                    setResult(777,intent);
                    finish();
                }
            });
        }
    }

    private void innitMap() {
        setUpMyLocation();
        PermissionUtils permissionUtils = new PermissionUtils(this, this);
        permissionUtils.checkPermission();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    private void setUpMyLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        createLocationCallback();
        buidLocationRequest();
        buildLocationSettingsRequest();
    }

    private Task<LocationSettingsResponse> task;
    private SettingsClient mSettingsClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private void getUpdateCurrentLocation() {
        if (mLocationPermissionsGranted) {
            try {
                task = mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                        .addOnSuccessListener(this, this)
                        .addOnFailureListener(this, this);
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
                mLastLocation = locationResult.getLastLocation();
                addressCurrentLocation = GoogleMapUtils.getAddress(AddLocationActivity.this,mLastLocation);
                moveCamera(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),15f,addressCurrentLocation);
            }
        };
    }

    private final static int REQUEST_CHECK_SETTINGS = 2000;

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

    Marker current_marker;
    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d("tungts", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (current_marker != null){
            current_marker.remove();
        }
        current_marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getUpdateCurrentLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    @Override
    public void permissionGranted() {
        mLocationPermissionsGranted = true;
        getUpdateCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.RequestCode.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        Log.d("tungts", "onRequestPermissionsResult: permission failed");
                        return;
                    }
                }
                Log.d("tungts", "onRequestPermissionsResult: permission granted");
                mLocationPermissionsGranted = true;
            }
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        int statusCode = ((ApiException) e).getStatusCode();
        switch (statusCode) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    ResolvableApiException rae = (ResolvableApiException) e;
                    rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException ignored) {
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
}
