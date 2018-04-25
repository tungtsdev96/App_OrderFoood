package com.ptpmcn.orderfood.activity.orderfood;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.adapter.orderfood.CartAdapter;
import com.ptpmcn.orderfood.adapter.orderfood.ClickAddOrRemoveFoodOfOrderDetail;
import com.ptpmcn.orderfood.model.Customer;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.constant.Constant;
import com.ptpmcn.orderfood.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tungts on 11/23/2017.
 */

public class CartActivity extends BaseActivity implements View.OnClickListener, ClickAddOrRemoveFoodOfOrderDetail {

    RecyclerView rcv_cart;
    CartAdapter cartAdapter;
    ArrayList list;

    HashMap<Food,OrderDetail> mapDetailFoodOrder;
    TextView tv_continue,tv_continue_order;
    Restaurant restaurant;
    public Restaurant getRestaurant(){
        if (restaurant == null){
            restaurant = (Restaurant) getIntent().getExtras().getSerializable(Constant.Key.KEY_RESTAURANT);
        }
        return restaurant;
    }

    public HashMap<Food,OrderDetail> getMapDetailFoodOrder(){
        if (mapDetailFoodOrder == null){
            mapDetailFoodOrder = (HashMap<Food, OrderDetail>) getIntent().getExtras().getSerializable(Constant.Key.KEY_CART);
        }
        return mapDetailFoodOrder;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void addControls() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getRestaurant();
        getMapDetailFoodOrder();
        tv_continue = (TextView) findViewById(R.id.tv_continue);
        tv_continue_order = (TextView) findViewById(R.id.tv_continue_order);
        innitRecycleView();
        addEvents();
    }

    private void innitRecycleView() {
        rcv_cart = (RecyclerView) findViewById(R.id.rcv_cart);
        list = new ArrayList();
        list.add(AccountUtil.fakeCustomer());
        for (Food food : mapDetailFoodOrder.keySet()){
            OrderDetail orderDetail = mapDetailFoodOrder.get(food);
            orderDetail.setFood(food);
            list.add(orderDetail);
        }
        cartAdapter = new CartAdapter(this,list);
        rcv_cart.setAdapter(cartAdapter);
        rcv_cart.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        rcv_cart.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addEvents() {
        tv_continue.setOnClickListener(this);
        tv_continue_order.setOnClickListener(this);
        cartAdapter.setOnClickAddOrRemoveFoodOfOrderDetail(this);
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
            case R.id.tv_continue:
//                list.remove(0);
//                ArrayList<OrderDetail> arr = new ArrayList();
//                arr.addAll(list);
//                Cart.getInstance().setOrderDetails(arr);
//                Cart.getInstance().setCustomerId(1);
//                Cart.getInstance().setDeliveryTime("Thích lúc nào cũng được");
//                Cart.getInstance().setOrderAddress("Nhà của tao");
//                Cart.getInstance().setOrderCost(100000);
//                Cart.getInstance().setOrderDescription("Không trả tiền có đc k");
//                Cart.getInstance().setOrderDistance(5);
//                Cart.getInstance().setRestaurentId(arr.get(0).getFood().getRestaurent_id());
//                ApiFactory.getApiOrder().orderFood(Cart.getInstance()).enqueue(new BaseCallBack<ResponseBody>(this) {
//                    @Override
//                    public void onSuccess(ResponseBody result) {
//                        Toast.makeText(CartActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
//                    }
//                });
                Customer c = (Customer) list.get(0);
                list.remove(0);
                ArrayList arr = new ArrayList();
                arr.addAll(list);
                Cart.getInstance().setOrderDetails(arr);
                Cart.getInstance().setRestaurant(getRestaurant());
                list.add(0,c);
                Intent intent = new Intent(CartActivity.this,AddInforOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_continue_order:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Customer c = (Customer) list.get(0);
        list.remove(0);
        ArrayList arr = new ArrayList();
        arr.addAll(list);
        Cart.getInstance().setOrderDetails(arr);
        Cart.getInstance().setRestaurant(getRestaurant());
        list.add(0,c);
//        Intent intent = new Intent();
//        setResult(3333);
    }

    @Override
    public void remove(int postion, OrderDetail orderDetail) {
        Log.e("tungts",orderDetail.getDetail_quantity()+"");
        if (list.size() == 2 && (orderDetail.getDetail_quantity() == 0)){
            Toast.makeText(this, "Đơn hàng phải có ít nhất một sản phẩm", Toast.LENGTH_SHORT).show();
            ((OrderDetail)list.get(postion)).setDetail_quantity(1);
            cartAdapter.notifyItemChanged(postion);
            return;
        }
        if (orderDetail.getDetail_quantity() == 0){
            list.remove(postion);
            cartAdapter.notifyItemRemoved(postion);
        } else {
            ((OrderDetail)list.get(postion)).setDetail_quantity(orderDetail.getDetail_quantity());
            cartAdapter.notifyItemChanged(postion);
        }
    }

    @Override
    public void add(int postion, OrderDetail orderDetail) {
        mapDetailFoodOrder.get(orderDetail.getFood()).setDetail_quantity(orderDetail.getDetail_quantity());
        ((OrderDetail)list.get(postion)).setDetail_quantity(orderDetail.getDetail_quantity());
        cartAdapter.notifyItemChanged(postion);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
