package com.ptpmcn.orderfood.activity.restaurant;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.AddLocationActivity;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.adapter.RcvCommentAdapter;
import com.ptpmcn.orderfood.adapter.food.FoodLinearAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.ApiGoogle;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.googlemap.GoogleMapUtils;
import com.ptpmcn.orderfood.googlemap.PermissionUtils;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.google.PathGoogleResponse;
import com.ptpmcn.orderfood.model.restaurant.Comment;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.model.restaurant.ScoreRating;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.constant.Constant;
import com.ptpmcn.orderfood.view.DividerItemDecoration;

import java.util.ArrayList;

import okhttp3.ResponseBody;

import static com.ptpmcn.orderfood.utils.constant.Constant.Key.KEY_RESTAURANT;
import static com.ptpmcn.orderfood.utils.constant.Constant.RequestCode.LOCATION_PERMISSION_REQUEST_CODE;

/**
 * Created by tungts on 12/7/2017.
 */

public class DetailRestaurantActivity extends BaseActivity implements View.OnClickListener, PermissionUtils.PermissionResultCallback, OnSuccessListener<LocationSettingsResponse>,
        OnFailureListener {

    Restaurant restaurant;
    private ImageView img_backdrop;

    TextView tv_name_res;
    TextView tv_number_of_comment,tv_score,btn_call;

    TextView tv_address_res,tv_distance,tv_description_res;

    RecyclerView rcv_best_buy_of_res;
    FoodLinearAdapter foodLinearAdapter;
    ArrayList<Food> arr_food = new ArrayList<>();
    TextView tv_order_food;

    TextView tv_comment,tv_score_average;
    RecyclerView rcv_comment;
    ArrayList<Comment> arrComments = new ArrayList<>();
    RcvCommentAdapter rcvCommentAdapter;

    LinearLayout ll_add_comment,ll_share,ll_add_rate;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_detail_restaurant;
    }

    @Override
    protected void addControls() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img_backdrop = (ImageView) findViewById(R.id.img_cover_res);
        ll_add_comment = (LinearLayout) findViewById(R.id.ll_add_comment);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_add_rate =(LinearLayout)findViewById(R.id.ll_add_rate);
        showProgressDialog(this);
        int id = getIntent().getIntExtra("RestaurantID",0);
        ApiFactory.getApiRestaurants().getRestaurantByIdRes(id).enqueue(new BaseCallBack<ArrayList<Restaurant>>(this) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                DetailRestaurantActivity.this.restaurant = result.get(0);
                hideProgressDialog();
                GlideHelper.loadImageByPath(DetailRestaurantActivity.this,img_backdrop,restaurant.getRestaurent_image());
                innitViewInfor();
                innnitRecycleViewBestBuy();
                innitRecycleViewComment();
                addEvents();
                setUpMyLocation();
            }
        });
    }

    private void addEvents() {
        btn_call.setOnClickListener(this);
        ll_add_comment.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_add_rate.setOnClickListener(this);
        tv_order_food.setOnClickListener(this);
        foodLinearAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Food food = arr_food.get(postion);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });
    }

    //comment
    private void innitRecycleViewComment() {
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        rcv_comment = (RecyclerView) findViewById(R.id.rcv_comment);
        rcvCommentAdapter = new RcvCommentAdapter(this,arrComments);
        rcv_comment.setAdapter(rcvCommentAdapter);
        rcv_comment.setLayoutManager(new LinearLayoutManager(this));
        rcv_comment.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        ApiFactory.getApiRestaurants().getComment(restaurant.getRestaurent_id(),1).enqueue(new BaseCallBack<ArrayList<Comment>>(DetailRestaurantActivity.this) {
            @Override
            public void onSuccess(ArrayList<Comment> result) {
                arrComments.addAll(result);
                rcvCommentAdapter.notifyDataSetChanged();
                tv_number_of_comment.setText(arrComments.size()+"");
                tv_comment.setText(arrComments.size()+" Bình luận");
            }
        });
    }

    //food best buy
    private void innnitRecycleViewBestBuy() {
        tv_order_food = (TextView) findViewById(R.id.tv_order_food);
        rcv_best_buy_of_res = (RecyclerView) findViewById(R.id.rcv_best_buy_of_res);
        foodLinearAdapter = new FoodLinearAdapter(this,arr_food,rcv_best_buy_of_res);
        rcv_best_buy_of_res.setAdapter(foodLinearAdapter);
        rcv_best_buy_of_res.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));
        rcv_best_buy_of_res.setLayoutManager(new LinearLayoutManager(this));
        ApiFactory.getApiFoods().getListRecommendFoodsByRes(restaurant.getRestaurent_id(),1).enqueue(new BaseCallBack<ArrayList<Food>>(this) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                arr_food.addAll(result);
                foodLinearAdapter.notifyDataSetChanged();
            }
        });
    }

    //view infor
    private void innitViewInfor() {
        tv_name_res = (TextView) findViewById(R.id.tv_name_res); tv_name_res.setText(restaurant.getRestaurent_name());
        tv_number_of_comment = (TextView) findViewById(R.id.tv_number_of_comment);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_score_average = (TextView) findViewById(R.id.tv_score_average);
        updateScore();
        btn_call = (TextView) findViewById(R.id.btn_call);
        tv_address_res = (TextView) findViewById(R.id.tv_address_res);
        tv_address_res.setText(restaurant.getRestaurent_address());
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_description_res = (TextView) findViewById(R.id.tv_description_res);
        tv_description_res.setText(restaurant.getRestaurent_introduction());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    boolean call = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_call:
                call();
                break;
            case R.id.ll_share:
                share();
                break;
            case R.id.ll_add_comment:
                if (AccountUtil.getInstance(DetailRestaurantActivity.this).isLogin()){

                } else {

                }
                dialogComment();
                break;
            case R.id.ll_add_rate:
                break;
            case R.id.tv_order_food:
                if (getIntent().getAction().equals("Order")){
                    onBackPressed();
                } else {
                    Intent i = new Intent(DetailRestaurantActivity.this,OrderOfRestaurantActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_RESTAURANT,restaurant);
                    i.putExtras(bundle);
                    startActivity(i);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 222){
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        call = false;
                        return;
                    }
                }
                call = true;
            }
        } else if (requestCode == Constant.RequestCode.LOCATION_PERMISSION_REQUEST_CODE) {
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

    private void call() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 222);
        } else {
            Uri uri = Uri.parse("tel:" + "096968888");
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, restaurant.getRestaurent_name());
        sharingIntent.putExtra(Intent.EXTRA_TEXT,restaurant.getRestaurent_name() + " "+ restaurant.getRestaurent_address());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void dialogComment() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_comment);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        final EditText edt_content = dialog.findViewById(R.id.edt_content);
        TextView tv_post_comment = dialog.findViewById(R.id.tv_post_comment);
        RatingBar rating_bar = dialog.findViewById(R.id.rating_bar);
        final ScoreRating scoreRating = new ScoreRating();
        scoreRating.setCustomerId(1);
        scoreRating.setRestaurentId(restaurant.getRestaurent_id());
        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
               scoreRating.setScore(v*2.5f);
            }
        });
        tv_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_content.getText().toString() != null){
                    //posst and load comment
                    String content = edt_content.getText().toString();
                    ApiFactory.getApiRestaurants().addComent(1,restaurant.getRestaurent_id(),content).enqueue(new BaseCallBack<ResponseBody>(DetailRestaurantActivity.this) {
                        @Override
                        public void onSuccess(ResponseBody result) {
                            Toast.makeText(DetailRestaurantActivity.this, "Cảm ơn bạn đã góp ý bình luận", Toast.LENGTH_SHORT).show();
                            ApiFactory.getApiRestaurants().getComment(restaurant.getRestaurent_id(),1).enqueue(new BaseCallBack<ArrayList<Comment>>(DetailRestaurantActivity.this) {
                                @Override
                                public void onSuccess(ArrayList<Comment> result) {
                                    arrComments.clear();
                                    arrComments.addAll(result);
                                    rcvCommentAdapter.notifyDataSetChanged();
                                }
                            });
                            if (scoreRating.getScore() >0){
                                ApiFactory.getApiRestaurants().rating(scoreRating).enqueue(new BaseCallBack<ResponseBody>(DetailRestaurantActivity.this) {
                                    @Override
                                    public void onSuccess(ResponseBody result) {
                                        Log.e(DetailRestaurantActivity.class.getName()+"- rating","Rating success");
                                        updateScore();
                                    }
                                });
                            }
                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }

    private void updateScore(){
        ApiFactory.getApiRestaurants().getScoreAverageOfRes(restaurant.getRestaurent_id()).enqueue(new BaseCallBack<ScoreRating>(this) {
            @Override
            public void onSuccess(ScoreRating result) {
                tv_score.setText(result.getSc()+"");
                tv_score_average.setText(result.getSc()+" khá tốt");
            }
        });
    }

    ////map
    boolean mLocationPermissionsGranted;
    private void setUpMyLocation() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        createLocationCallback();
        buidLocationRequest();
        buildLocationSettingsRequest();

        PermissionUtils permissionUtils = new PermissionUtils(this, this);
        permissionUtils.checkPermission();
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
                Location location = locationResult.getLastLocation();
                String addressCurrentLocation = GoogleMapUtils.getAddress(DetailRestaurantActivity.this,location);
                ApiFactory.getApiGoogleMaps().getDirection(addressCurrentLocation,restaurant.getRestaurent_address(),getString(R.string.google_map_api_key)).enqueue(new BaseCallBack<PathGoogleResponse>(DetailRestaurantActivity.this) {
                    @Override
                    public void onSuccess(PathGoogleResponse result) {
                        try {
                            String distance = result.getRoutes().get(0).getLegs().get(0).getDistance().getText();
                            String s = "<b>"+distance + "</b>" + " (Từ vị trí hiện tại)";
                            tv_distance.setText(Html.fromHtml(s));
                            stopLocationUpdates();
                        } catch (Exception e){
                            Log.e(DetailRestaurantActivity.this.getLocalClassName(),"Loi");
                        }
                    }
                });
//                moveCamera(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),15f,addressCurrentLocation);
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
