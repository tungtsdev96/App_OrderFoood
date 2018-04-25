package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.GsonBuilder;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.orderfood.DetailOrderActivity;
import com.ptpmcn.orderfood.adapter.orderfood.ClickDetailOrderProduct;
import com.ptpmcn.orderfood.adapter.orderfood.RcvWaitingOrderFoodAdapter;
import com.ptpmcn.orderfood.adapter.orderfood.RcvWatingOrderTableAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.ordertable.OrderTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by tungts on 12/5/2017.
 */

public class WaitingOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {

    SwipeRefreshLayout swipe_refresh_layout;
    boolean isOrderFood;
    Spinner spiner_filter_order;
    List<String> list_filter;
    ArrayAdapter<String> adapter;

    RecyclerView rcv_waiting_order;
    RcvWaitingOrderFoodAdapter waiting_order_adapter;
    ArrayList<OrderProduct> arr_order_products = new ArrayList<>();

    RecyclerView rcv_order_table;
    ArrayList<OrderTable> arr_order_tables = new ArrayList<>();
    RcvWatingOrderTableAdapter waiting_order_table_adapter;



    public static WaitingOrderFragment newInstance(){
        WaitingOrderFragment fragment = new WaitingOrderFragment();
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_order_comming;
    }

    @Override
    protected void addControls() {
        swipe_refresh_layout = root.findViewById(R.id.swipe_refresh_layout);
        innitSpinner();
        innitRecycleViewOrderFood();
        innitRecycleViewOrderTable();
        addEvents();
        ////posst khi dat ban
    }

    private void innitSpinner() {
        spiner_filter_order = root.findViewById(R.id.spiner_filter_order);
        list_filter = Arrays.asList(getResources().getStringArray(R.array.array_filter_order));
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_filter);
        spiner_filter_order.setAdapter(adapter);
        spiner_filter_order.setOnItemSelectedListener(this);
    }

    private void addEvents() {
        swipe_refresh_layout.setOnRefreshListener(this);
    }

    @Override
    protected void innitData() {

    }

    private void innitRecycleViewOrderFood() {
        rcv_waiting_order = root.findViewById(R.id.rcv_waiting_order);
        waiting_order_adapter = new RcvWaitingOrderFoodAdapter(getContext(),arr_order_products);
        rcv_waiting_order.setAdapter(waiting_order_adapter);
        rcv_waiting_order.setLayoutManager(new LinearLayoutManager(getContext()));
        waiting_order_adapter.setOnClickDetailOrderProduct(new ClickDetailOrderProduct() {
            @Override
            public void itemDetailOrderProduct(OrderProduct orderProduct) {
                Intent i = new Intent(getContext(),DetailOrderActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Order",orderProduct);
                i.putExtras(b);
                startActivity(i);
            }
        });
        innitDataOrderFood();
    }

    private void innitDataOrderFood() {
        arr_order_products.clear();
        waiting_order_adapter.notifyDataSetChanged();
        ApiFactory.getApiOrder().getListOrderProductByCustomerId(1,1).enqueue(new BaseCallBack<ArrayList<OrderProduct>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<OrderProduct> result) {
                for (OrderProduct order:result){
                    if (order.getOrder_status() == 0 || order.getOrder_status() == 1 ){
                        arr_order_products.add(order);
                        Log.e("tungts",new GsonBuilder().create().toJson(order));
                    }
                }
                waiting_order_adapter.notifyDataSetChanged();
            }
        });
    }

    private void innitRecycleViewOrderTable() {
        rcv_order_table = root.findViewById(R.id.rcv_order_table);
        waiting_order_table_adapter = new RcvWatingOrderTableAdapter(getContext(),arr_order_tables);
        rcv_order_table.setAdapter(waiting_order_table_adapter);
        rcv_order_table.setLayoutManager(new LinearLayoutManager(getContext()));
        innitDataOrderTable();
    }

    private void innitDataOrderTable(){
        arr_order_tables.clear();
        waiting_order_table_adapter.notifyDataSetChanged();
        ApiFactory.getApiOrder().getListOrderTableByIdCustomer(1).enqueue(new BaseCallBack<ArrayList<OrderTable>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<OrderTable> result) {
                for (OrderTable order:result){
                    if (order.getStatus() != 3){
                        arr_order_tables.add(order);
                        Log.e("tungts",new GsonBuilder().create().toJson(order));
                    }
                }
                waiting_order_table_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (isOrderFood){
            innitDataOrderFood();
        } else {
            innitDataOrderTable();
        }
        swipe_refresh_layout.setRefreshing(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0){
            isOrderFood = true;
            rcv_waiting_order.setVisibility(View.VISIBLE);
            rcv_order_table.setVisibility(View.GONE);
        } else if (i == 1){
            isOrderFood = false;
            rcv_order_table.setVisibility(View.VISIBLE);
            rcv_waiting_order.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
