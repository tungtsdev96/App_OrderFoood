package com.ptpmcn.orderfood.activity.orderfood;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.activity.ListFoodActivity;
import com.ptpmcn.orderfood.activity.SearchActivity;
import com.ptpmcn.orderfood.activity.restaurant.DetailRestaurantActivity;
import com.ptpmcn.orderfood.adapter.orderfood.DetailOrderAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.restaurant.Comment;
import com.ptpmcn.orderfood.model.restaurant.ScoreRating;
import com.ptpmcn.orderfood.utils.constant.Constant;
import com.ptpmcn.orderfood.view.DividerItemDecoration;

import java.util.ArrayList;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 12/10/2017.
 */

public class DetailOrderActivity extends BaseActivity implements View.OnClickListener {

    OrderProduct orderProduct;
    TextView tv_orderId,tv_address_order,tv_phone_order,tv_delivery_time,tv_status;
    TextView btn_rate;

    RecyclerView rcv_detail_order;
    ArrayList<OrderDetail> arrOrderDetails = new ArrayList<>();
    DetailOrderAdapter detailOrderAdapter;

    TextView tv_total_order,tv_price_of_service,tv_price_ship,tv_total;

    public OrderProduct getOrderProduct(){
        return (OrderProduct) getIntent().getExtras().getSerializable("Order");
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_detail_order;
    }

    @Override
    protected void addControls() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.orderProduct = getOrderProduct();
        innitInfor();
        innitRecycleView();
        addEvents();
    }

    private void addEvents() {
        btn_rate.setOnClickListener(this);
    }

    private void innitRecycleView() {
        rcv_detail_order = (RecyclerView) findViewById(R.id.rcv_order_detail);
        detailOrderAdapter = new DetailOrderAdapter(this,arrOrderDetails);
        rcv_detail_order.setAdapter(detailOrderAdapter);
        rcv_detail_order.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        rcv_detail_order.setLayoutManager(new LinearLayoutManager(this));
        ApiFactory.getApiOrder().getProductByOrderId(orderProduct.getOrder_id()).enqueue(new BaseCallBack<ArrayList<OrderDetail>>(this) {
            @Override
            public void onSuccess(ArrayList<OrderDetail> result) {
                arrOrderDetails.addAll(result);
                detailOrderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void innitInfor() {
        btn_rate = (TextView) findViewById(R.id.btn_rate);
        tv_orderId = (TextView) findViewById(R.id.tv_orderId);
        tv_address_order = (TextView) findViewById(R.id.tv_address_order);
        tv_phone_order = (TextView) findViewById(R.id.tv_phone_order);
        tv_delivery_time = (TextView) findViewById(R.id.tv_delivery_time);
        tv_status = (TextView) findViewById(R.id.tv_status);

        tv_orderId.setText(orderProduct.getOrder_id()+"");
        tv_address_order.setText(orderProduct.getOrder_address());
        tv_phone_order.setText(orderProduct.getPhone_number());
        tv_delivery_time.setText(orderProduct.getDelivery_time());
        if (orderProduct.getOrder_status() == -1){
            tv_status.setText("Đã hủy");
        } else if (orderProduct.getOrder_status() == 0){
            tv_status.setText("Đang xác nhận");
        } else if (orderProduct.getOrder_status() == 1){
            tv_status.setText("Đang  vận chuyển");
        } else if (orderProduct.getOrder_status() == 2){
            tv_status.setText("Đã hoàn thành");
        }

        //price
        tv_total_order = (TextView) findViewById(R.id.tv_total_order);
        tv_price_of_service = (TextView) findViewById(R.id.tv_price_of_service);
        tv_price_ship  = (TextView) findViewById(R.id.tv_price_ship);
        tv_total = (TextView) findViewById(R.id.tv_total);
        int price_of_ship = (int) (Constant.Order.PRICE_SHIP * orderProduct.getOrder_distance());
        tv_price_ship.setText(String.format(getString(R.string.price),price_of_ship));
        tv_total.setText(String.format(getString(R.string.price),orderProduct.getOrder_cost()));

        int total_order = (int) ((orderProduct.getOrder_cost() - price_of_ship)/1.1);  tv_total_order.setText(String.format(getString(R.string.price),total_order));
        int price_of_service  = (int) (Constant.Order.PRECENT_SERVICE*total_order);  tv_price_of_service.setText(String.format(getString(R.string.price),price_of_service));

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
        if (view.getId() == R.id.btn_rate){
            ////rate and feed back//////
            dialogFeedBack();
        }
    }

    private void dialogFeedBack() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_feedback);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        final EditText edt_content = dialog.findViewById(R.id.edt_content);
        TextView tv_post_comment = dialog.findViewById(R.id.tv_send);
        RatingBar rating_bar = dialog.findViewById(R.id.rating_bar);
        final ScoreRating scoreRating = new ScoreRating();
        scoreRating.setCustomerId(1);
        scoreRating.setRestaurentId(orderProduct.getRestaurent_id());
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
                    ApiFactory.getApiOrder().feedBack(content,orderProduct.getOrder_id()).enqueue(new BaseCallBack<ResponseBody>(DetailOrderActivity.this) {
                        @Override
                        public void onSuccess(ResponseBody result) {
                            Toast.makeText(DetailOrderActivity.this, "Cảm ơn bạn đã phản hồi cho cửa hàng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }
}
