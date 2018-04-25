package com.ptpmcn.orderfood.activity.ordertable;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.activity.restaurant.DetailRestaurantActivity;
import com.ptpmcn.orderfood.adapter.ClickChooseTable;
import com.ptpmcn.orderfood.adapter.RcvCommentAdapter;
import com.ptpmcn.orderfood.adapter.TableAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.googlemap.CurrentLocation;
import com.ptpmcn.orderfood.interfaces.DatePickerListener;
import com.ptpmcn.orderfood.interfaces.TimePickerListener;
import com.ptpmcn.orderfood.model.google.PathGoogleResponse;
import com.ptpmcn.orderfood.model.ordertable.DetailOrderTable;
import com.ptpmcn.orderfood.model.ordertable.OrderTable;
import com.ptpmcn.orderfood.model.ordertable.Table;
import com.ptpmcn.orderfood.model.restaurant.Comment;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.model.restaurant.ScoreRating;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.TimeDistanceUtils;
import com.ptpmcn.orderfood.view.DatePicker;
import com.ptpmcn.orderfood.view.DividerItemDecoration;
import com.ptpmcn.orderfood.view.TimePicker;

import java.util.ArrayList;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 12/12/2017.
 */

public class OrderTableActivity extends BaseActivity implements View.OnClickListener,ClickChooseTable {

    OrderTable orderTable;
    Restaurant restaurant;
    private ImageView img_backdrop;
    TextView btn_order_table;

    TextView tv_name_res;
    TextView tv_number_of_comment,tv_score,btn_call;

    TextView tv_address_res,tv_distance,tv_description_res;

    TextView tv_comment,tv_score_average;
    RecyclerView rcv_comment;
    ArrayList<Comment> arrComments = new ArrayList<>();
    RcvCommentAdapter rcvCommentAdapter;

    RecyclerView rcv_table;
    ArrayList<Table> arrTable = new ArrayList<>();
    TableAdapter tableAdapter;

    LinearLayout ll_add_comment,ll_share,ll_add_rate;
    LinearLayout ll_date,ll_time;
    TextView tv_date,tv_time;
    EditText edt_number_of_people;
    Button btn_search;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_order_table;
    }

    @Override
    protected void addControls() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderTable = new OrderTable();
        btn_order_table = (TextView) findViewById(R.id.btn_order_table);
        img_backdrop = (ImageView) findViewById(R.id.img_cover_res);
        ll_add_comment = (LinearLayout) findViewById(R.id.ll_add_comment);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_add_rate =(LinearLayout)findViewById(R.id.ll_add_rate);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        tv_date = (TextView) findViewById(R.id.tv_date);
        ll_time = (LinearLayout) findViewById(R.id.ll_time);
        tv_time = (TextView) findViewById(R.id.tv_time);
        btn_search = (Button) findViewById(R.id.btn_search_table);
        edt_number_of_people = (EditText) findViewById(R.id.edt_number_of_people);
        showProgressDialog(this);
        int id = getIntent().getIntExtra("RestaurantID",1);
        ApiFactory.getApiRestaurants().getRestaurantByIdRes(1).enqueue(new BaseCallBack<ArrayList<Restaurant>>(this) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                restaurant = result.get(0);
                orderTable.setRestaurent_id(restaurant.getRestaurent_id());
                hideProgressDialog();
                GlideHelper.loadImageByPath(OrderTableActivity.this,img_backdrop,restaurant.getRestaurent_image());
                innitViewInfor();
                innitRecycleViewComment();
                innitRecycleViewTable();
                addEvents();
            }
        });
    }

    ArrayList<Table> coppyTables = new ArrayList<>();
    private void innitRecycleViewTable() {
        rcv_table = (RecyclerView) findViewById(R.id.rcv_table);
        tableAdapter = new TableAdapter(this,arrTable);
        rcv_table.setAdapter(tableAdapter);
        rcv_table.setLayoutManager(new GridLayoutManager(this,5));
        tableAdapter.setOnClickChooseTable(this);
        ApiFactory.getApiRestaurants().getListTableByResId(restaurant.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Table>>(this) {
            @Override
            public void onSuccess(ArrayList<Table> result) {
                arrTable.addAll(result);
                coppyTables.addAll(arrTable);
                tableAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addEvents() {
        btn_search.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        ll_add_comment.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_add_rate.setOnClickListener(this);
        btn_order_table.setOnClickListener(this);
        ll_date.setOnClickListener(this);
        ll_time.setOnClickListener(this);
    }

    //comment
    private void innitRecycleViewComment() {
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        rcv_comment = (RecyclerView) findViewById(R.id.rcv_comment);
        rcvCommentAdapter = new RcvCommentAdapter(this,arrComments);
        rcv_comment.setAdapter(rcvCommentAdapter);
        rcv_comment.setLayoutManager(new LinearLayoutManager(this));
        rcv_comment.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        ApiFactory.getApiRestaurants().getComment(restaurant.getRestaurent_id(),1).enqueue(new BaseCallBack<ArrayList<Comment>>(OrderTableActivity.this) {
            @Override
            public void onSuccess(ArrayList<Comment> result) {
                arrComments.addAll(result);
                rcvCommentAdapter.notifyDataSetChanged();
                tv_number_of_comment.setText(arrComments.size()+"");
                tv_comment.setText(arrComments.size()+" Bình luận");
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
        new CurrentLocation(this, new CurrentLocation.CurrentAddress() {
            @Override
            public void currentLocation(String address) {
                ApiFactory.getApiGoogleMaps().getDirection(address,restaurant.getRestaurent_address(),getString(R.string.google_map_api_key)).enqueue(new BaseCallBack<PathGoogleResponse>(OrderTableActivity.this) {
                    @Override
                    public void onSuccess(PathGoogleResponse result) {
                        try {
                            String distance = result.getRoutes().get(0).getLegs().get(0).getDistance().getText();
                            String s = "<b>"+distance + "</b>" + " (Từ vị trí hiện tại)";
                            tv_distance.setText(Html.fromHtml(s));
                        } catch (Exception e){
                            Log.e(OrderTableActivity.this.getLocalClassName(),"Loi");
                        }
                    }
                });
            }
        });
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
                if (AccountUtil.getInstance(OrderTableActivity.this).isLogin()){

                } else {

                }
                dialogComment();
                break;
            case  R.id.btn_order_table:
                checkInforOrder();
                break;
            case R.id.ll_date:
                DatePicker datePicker = new DatePicker(new DatePickerListener() {
                    @Override
                    public void date(String date) {
                        tv_date.setText(TimeDistanceUtils.changePatternDate(date));
                    }
                });
                datePicker.show(getFragmentManager(),"Date Picker");
                break;
            case R.id.ll_time:
                TimePicker timePicker = new TimePicker(new TimePickerListener() {
                    @Override
                    public void time(String time) {
                        tv_time.setText(time);
                    }
                });
                timePicker.show(getFragmentManager(),"Time Picker");
                break;
            case R.id.btn_search_table:
                if (tv_date.getText().toString() == null || tv_time.getText().toString() == null){
                    showAlert("Thông báo ", "Hãy nhập ngày giờ");
                    return;
                } else {
                    try {
                        arrTable.clear(); tableAdapter.notifyDataSetChanged();
                        arrTable.addAll(coppyTables); tableAdapter.notifyDataSetChanged();
                        String start_time = tv_date.getText().toString() + " " + tv_time.getText().toString();
                        String startTime = TimeDistanceUtils.convertToLong(start_time);
                        String endTime = String.valueOf(Long.parseLong(startTime) + 5000*60*60);
                        ApiFactory.getApiOrder().searchTable(restaurant.getRestaurent_id(),startTime,endTime).enqueue(new BaseCallBack<ArrayList<Table>>(this) {
                            @Override
                            public void onSuccess(ArrayList<Table> result) {
                                Log.e("table_blank",new GsonBuilder().create().toJson(result));
                                for (int i = 0;i<arrTable.size();i++){
                                    Table table = arrTable.get(i);
                                    for (Table tb : result){
                                        if (tb.getTable_id() == table.getTable_id()){
                                            table.setBlank(true);
                                            tableAdapter.notifyItemChanged(i);
                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception e){

                    }
                }

                break;
        }
    }

    private void checkInforOrder() {
        orderTable.setCustomer_id(AccountUtil.fakeCustomer().getCustomer_id());
        orderTable.setRestaurent_id(restaurant.getRestaurent_id());
        if (tv_date.getText().toString() == null || tv_time.getText().toString() == null){
            showAlert("Thông báo ", "Hãy nhập ngày giờ");
            return;
        } else {
            String start_time = tv_date.getText().toString() + " " + tv_time.getText().toString();
            orderTable.setStart_time(TimeDistanceUtils.getStartTime(start_time));
            orderTable.setEnd_time(TimeDistanceUtils.getEndTime(start_time));
        }
        if (edt_number_of_people.getText().toString() != null){
            int number = Integer.parseInt(edt_number_of_people.getText().toString());
            orderTable.setNumber_people(number);
        } else {
            showAlert("","Hãy thêm số người");
            return;
        }
        Intent i = new Intent(OrderTableActivity.this,AddInforOrderTableActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("OrderTable",orderTable);
        i.putExtras(b);
        startActivity(i);
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
                    ApiFactory.getApiRestaurants().addComent(1,restaurant.getRestaurent_id(),content).enqueue(new BaseCallBack<ResponseBody>(OrderTableActivity.this) {
                        @Override
                        public void onSuccess(ResponseBody result) {
                            Toast.makeText(OrderTableActivity.this, "Cảm ơn bạn đã góp ý bình luận", Toast.LENGTH_SHORT).show();
                            ApiFactory.getApiRestaurants().getComment(restaurant.getRestaurent_id(),1).enqueue(new BaseCallBack<ArrayList<Comment>>(OrderTableActivity.this) {
                                @Override
                                public void onSuccess(ArrayList<Comment> result) {
                                    arrComments.clear();
                                    arrComments.addAll(result);
                                    rcvCommentAdapter.notifyDataSetChanged();
                                }
                            });
                            if (scoreRating.getScore() >0){
                                ApiFactory.getApiRestaurants().rating(scoreRating).enqueue(new BaseCallBack<ResponseBody>(OrderTableActivity.this) {
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

    ArrayList<DetailOrderTable> arr = new ArrayList<>();
    @Override
    public void choose(int pos, boolean isChoose) {
        arrTable.get(pos).setChoose(!isChoose);
        tableAdapter.notifyItemChanged(pos);
        orderTable.getOrderTables().add(new DetailOrderTable(arrTable.get(pos).getTable_id(),arrTable.get(pos).getTable_number()));
//        int index = -1;
//        for (int i = 0;i<arr.size();i++){
//            DetailOrderTable detailOrderTable = arr.get(i);
//            if (detailOrderTable.getTable_id() == arrTable.get(pos).getTable_id()){
//                index = i;
//                break;
//            }
//        }
//        arr.remove(index);
    }
}
