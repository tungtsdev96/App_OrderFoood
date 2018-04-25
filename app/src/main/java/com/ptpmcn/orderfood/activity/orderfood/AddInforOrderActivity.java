package com.ptpmcn.orderfood.activity.orderfood;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.GsonBuilder;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.activity.ListOrderOfCustomerActivity;
import com.ptpmcn.orderfood.adapter.orderfood.InforOrderAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.googlemap.CurrentLocation;
import com.ptpmcn.orderfood.model.Customer;
import com.ptpmcn.orderfood.model.google.Leg;
import com.ptpmcn.orderfood.model.google.MyLatLong;
import com.ptpmcn.orderfood.model.google.PathGoogleResponse;
import com.ptpmcn.orderfood.model.google.RouteGoogle;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.TimeDistanceUtils;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 11/25/2017.
 */

public class AddInforOrderActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    Restaurant restaurant;
    double distance;
    ArrayList<OrderDetail> orderDetails = Cart.getInstance().getOrderDetails();
    int totalOfPrice = 0;
    int numberOfPartOrder = 0;
    int priceOfShip = 0;
    int priceService = 0;
    int total = 0;

    private void getTotalAndNumber() {
        for (OrderDetail orderDetail : orderDetails) {
            numberOfPartOrder += orderDetail.getDetail_quantity();
            totalOfPrice += orderDetail.getFood().getProduct_price() * orderDetail.getDetail_quantity();
        }
    }

    TextView tv_config;
    NestedScrollView scroll_view;
    RelativeLayout rlt_content;
    TextView tv_name_res, tv_address_res;
    TextView tv_customer, tv_address_cus;
    TextView tv_infor_time_ship, tv_distance;
    TextView tv_number_of_food_order, tv_total_price;
    TextView tv_price_of_service;
    TextView tv_distance_ship, tv_price_ship;
    ImageView img_help;
    TextView tv_total;
    TextView tv_total_of_order;
    EditText edt_add_note;

    //rcv Order product detail
    RecyclerView rcv_infor_order;
    InforOrderAdapter inforOrderAdapter;
    RelativeLayout btn_order;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_infor_order;
    }

    @Override
    protected void addControls() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initScrollView();
        getRestaurant();
        getTotalAndNumber();
        innitResInfor();
        innitCustomerInfor();
        innitTimeAndDistanceShip();
        innitPrice();
        innitRecycleView();
        innitMap();

        //btn order
        btn_order = (RelativeLayout) findViewById(R.id.btn_order);
        btn_order.setOnClickListener(this);

        //btn config
        tv_config = (TextView) findViewById(R.id.tv_config);
        tv_config.setOnClickListener(this);

    }

    private void innitRecycleView() {
        rcv_infor_order = (RecyclerView) findViewById(R.id.rcv_infor_order);
        inforOrderAdapter = new InforOrderAdapter(orderDetails,this);
        rcv_infor_order.setAdapter(inforOrderAdapter);
        rcv_infor_order.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initScrollView() {
        scroll_view = (NestedScrollView) findViewById(R.id.scroll_view);
        rlt_content = (RelativeLayout) findViewById(R.id.rlt_content);
        rlt_content.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }

        });
    }

    private void innitPrice() {
        //so luong phan dc dat
        tv_number_of_food_order = (TextView) findViewById(R.id.tv_number_of_food_order);
        tv_number_of_food_order.setText(String.format(getString(R.string.number_of_food_order), numberOfPartOrder));

        //tong tien
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_total_price.setText(String.format(getString(R.string.price), totalOfPrice));

        ///tien dich vu
        tv_price_of_service = (TextView) findViewById(R.id.tv_price_of_service);
        priceService = (int) (Constant.Order.PRECENT_SERVICE * totalOfPrice);
        tv_price_of_service.setText(String.format(getString(R.string.price), priceService));

        //tien ship
        tv_distance_ship = (TextView) findViewById(R.id.tv_distance_ship);
        tv_price_ship = (TextView) findViewById(R.id.tv_price_ship);
        if (distance != 0) {
            priceOfShip = (int) (distance * Constant.Order.PRICE_SHIP);
            tv_distance_ship.setText(String.format(getString(R.string.delivery_distance), distance + ""));
            tv_price_ship.setText(String.format(getString(R.string.price), priceOfShip));
        }

        img_help = (ImageView) findViewById(R.id.img_help);
        total = priceOfShip + priceService + totalOfPrice + 10000;
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_total_of_order = (TextView) findViewById(R.id.tv_total_of_order);
        tv_total.setText(String.format(getString(R.string.price), total));
        tv_total_of_order.setText(String.format(getString(R.string.price), total));

    }

    private void innitTimeAndDistanceShip() {
        tv_infor_time_ship = (TextView) findViewById(R.id.tv_infor_time_ship);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_infor_time_ship.setText(String.format(getString(R.string.delivery_time), "Sớm nhất có thể", TimeDistanceUtils.getDateEarliestPossibleDeliveryTime()));
        if (distance != 0) {
            tv_distance.setText(String.format(getString(R.string.delivery_distance), distance + ""));
        }
    }

    private void innitCustomerInfor() {
        Customer customer = AccountUtil.fakeCustomer();
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_address_cus = (TextView) findViewById(R.id.tv_address_cus);
        tv_customer.setText(String.format(getString(R.string.infor_customer_to_receive), customer.getCustomer_name(), customer.getCustomer_phone()));
    }

    private void innitResInfor() {
        tv_name_res = (TextView) findViewById(R.id.tv_name_res);
        tv_address_res = (TextView) findViewById(R.id.tv_address_res);
        tv_name_res.setText(getRestaurant().getRestaurent_name());
        tv_address_res.setText(getRestaurant().getRestaurent_address());
    }

    private Restaurant getRestaurant() {
        if (restaurant == null) {
            restaurant = Cart.getInstance().getRestaurant();
        }
        Log.e("tungts",new GsonBuilder().create().toJson(restaurant));
        return restaurant;
    }

    private void updatePrice(double distance) {
        //tien ship
        priceOfShip = (int) (distance * Constant.Order.PRICE_SHIP);
        tv_price_ship.setText(String.format(getString(R.string.price), priceOfShip));
        total = priceOfShip + priceService + totalOfPrice;
        tv_total.setText(String.format(getString(R.string.price), total));
        tv_total_of_order.setText(String.format(getString(R.string.price), total));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_order:
                postOrder();
                Intent i = new Intent(AddInforOrderActivity.this, ListOrderOfCustomerActivity.class);
                i.setAction(Constant.Action.ACTION_WAITING_ORDER);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
            case R.id.tv_config:
                Intent intent = new Intent(AddInforOrderActivity.this,SetDetiveryDetailActivity.class);
                startActivityForResult(intent,2222);
                overridePendingTransition(R.anim.slide_up,R.anim.slide_up);
                hideKeyboard();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2222 && resultCode == 3333){
            if (Cart.getInstance().getOrderAddress() != null){
                Log.e("tungts","have address");
                tv_address_cus.setText(Cart.getInstance().getOrderAddress());
                findPath(Cart.getInstance().getOrderAddress(), "Số 124 Đại La, Hà Nội");
            }
        }
    }


    private void postOrder() {
        edt_add_note = (EditText) findViewById(R.id.edt_add_note);
        if (!edt_add_note.getText().toString().isEmpty()){
            Cart.getInstance().setOrderDescription(edt_add_note.getText().toString());
        }
        Cart.getInstance().getDeliveryTime();
        Cart.getInstance().getPhoneNumber() ;
        Cart.getInstance().setOrderCost(total);
        Cart.getInstance().getOrderAddress();
        Cart.getInstance().setCustomerId(AccountUtil.fakeCustomer().getCustomer_id());;
        Cart.getInstance().setRestaurentId(restaurant.getRestaurent_id());
        Cart.getInstance().setOrderDistance(distance);
        Cart.getInstance().getOrderDetails();
        Log.e("cart",new GsonBuilder().create().toJson(Cart.getInstance()));
        ApiFactory.getApiOrder().orderFood(Cart.getInstance()).enqueue(new BaseCallBack<ResponseBody>(this) {
            @Override
            public void onSuccess(ResponseBody result) {
                Toast.makeText(AddInforOrderActivity.this, "Đặt thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //google map
    boolean mapReady = false;
    GoogleMap map;
    ProgressBar progress_load_map;
    private List<Polyline> polylinePaths = new ArrayList<>();
    private List<Marker> makers = new ArrayList<>();

    ////google map/////////
    private void innitMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(AddInforOrderActivity.this);
        progress_load_map = (ProgressBar) findViewById(R.id.progress_load_map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mapReady = true;
        new CurrentLocation(this, new CurrentLocation.CurrentAddress() {
            @Override
            public void currentLocation(String address) {
                findPath(address,restaurant.getRestaurent_address());
            }
        });
    }

    private void findPath(String origin,String destination) {
        removePaths();
        progress_load_map.setVisibility(View.VISIBLE);
        ApiFactory.getApiGoogleMaps().getDirection(origin,destination,getResources().getString(R.string.google_map_api_key)).enqueue(new BaseCallBack<PathGoogleResponse>(getApplicationContext()) {
            @Override
            public void onSuccess(PathGoogleResponse result) {
                ArrayList<RouteGoogle> routeGoogles = result.getRoutes();
                drawDirection(routeGoogles.get(0).getLegs());
            }
        });
    }

    private void drawDirection(List<Leg> legs) {
        for (Leg leg : legs){
            distance = leg.getDistance().getValue()*1.0/1000;
            updatePrice(distance);
            tv_distance.setText(leg.getDistance().getText());
            tv_distance_ship.setText(leg.getDistance().getText());

            Log.e("path","time " + leg.getDuration().getText() + " distance " + leg.getDistance().getText());

            LatLng latLng = new LatLng(legs.get(0).getStart_location().getLat(),legs.get(0).getStart_location().getLng());
            moveCamera(latLng,13f);

            makers.add(addMaker(leg.getStart_address(),leg.getStart_location(),R.drawable.ic_location));
            makers.add(addMaker(leg.getEnd_address(),leg.getEnd_location(),R.drawable.ic_location_restuarant));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (LatLng point : leg.getAllLatLon()){
                polylineOptions.add(point);
            }
            polylinePaths.add(map.addPolyline(polylineOptions));
        }
        progress_load_map.setVisibility(View.GONE);
    }

    private void removePaths() {
        if (makers != null){
            for (Marker marker:makers){
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    private Marker addMaker(String title,MyLatLong latLng, int resId){
        hideKeyboard();
        return map.addMarker(new MarkerOptions()
                .title(title)
                .position(new LatLng(latLng.getLat(),latLng.getLng()))
                .icon(BitmapDescriptorFactory.fromResource(resId)));
    }

    private void moveCamera(LatLng latLng, float zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        hideKeyboard();
    }

}
