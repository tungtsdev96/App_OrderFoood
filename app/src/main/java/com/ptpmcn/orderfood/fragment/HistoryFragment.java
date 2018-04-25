package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.orderfood.DetailOrderActivity;
import com.ptpmcn.orderfood.adapter.RcvHistoryOrderAdapter;
import com.ptpmcn.orderfood.adapter.RcvHistoryOrderTableAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.ordertable.OrderTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tungts on 12/5/2017.
 */

public class HistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    boolean isOrderfood = false;
    Spinner spiner_filter_order;
    ArrayAdapter<String> adapter_order;
    List<String> list_filter_order;

    Spinner spiner_filter;
    ArrayAdapter<String> adapter;
    List<String> list_filter = new ArrayList<>();

    //order table
    RecyclerView rcv_history_order_table;
    ArrayList<OrderTable> orderTables = new ArrayList<>();
    RcvHistoryOrderTableAdapter orderTableAdapter;

    ///order food
    RecyclerView rcv_history_order;
    ArrayList<OrderProduct> orderProducts = new ArrayList<>();
    RcvHistoryOrderAdapter historyOrderAdapter;

    SwipeRefreshLayout swipe_refresh;

    public static HistoryFragment newInstance() {
        HistoryFragment historyFragment = new HistoryFragment();
        return historyFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_history;
    }

    @Override
    protected void addControls() {
        swipe_refresh = root.findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(this);
        innitSpinerFillterOrderFood();
        innitSpinerFilterOrder();
        innitRecycleViewOrderFood();
        innitRecycleViewOrderTable();
    }

    private void innitSpinerFilterOrder() {
        spiner_filter_order = root.findViewById(R.id.spiner_filter_order);
        list_filter_order = Arrays.asList(getResources().getStringArray(R.array.array_filter_order));
        adapter_order = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_filter_order);
        spiner_filter_order.setAdapter(adapter_order);
        spiner_filter_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    //filter order food
                    isOrderfood = true;
                    rcv_history_order.setVisibility(View.VISIBLE);
                    rcv_history_order_table.setVisibility(View.GONE);
                    list_filter.clear();
                    adapter.notifyDataSetChanged();
                    list_filter.addAll(Arrays.asList(getResources().getStringArray(R.array.array_histrory_order)));
                    adapter.notifyDataSetChanged();
                    spiner_filter.setSelection(0);
                    filterHistoryOrderFood(3);
                    spiner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {
                                filterHistoryOrderFood(3);
                            } else if (i == 1) {
                                filterHistoryOrderFood(2);
                            } else if (i == 2) {
                                filterHistoryOrderFood(1);
                            } else if (i == 3) {
                                filterHistoryOrderFood(-1);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    //filter order table
                    isOrderfood = false;
                    rcv_history_order.setVisibility(View.GONE);
                    rcv_history_order_table.setVisibility(View.VISIBLE);
                    list_filter.clear();
                    adapter.notifyDataSetChanged();
                    list_filter.addAll(Arrays.asList(getResources().getStringArray(R.array.array_histrory_order_table)));
                    adapter.notifyDataSetChanged();
                    filterHistoryOrderTable(0);
                    spiner_filter.setSelection(0);
                    spiner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i==0) {
                                filterHistoryOrderTable(0);
                            } else if (i == 1){
                                filterHistoryOrderTable(1);
                            } else if (i == 3){
                                filterHistoryOrderTable(3);
                            } else if (i == 2){
                                filterHistoryOrderTable(4);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void innitSpinerFillterOrderFood() {
        spiner_filter = root.findViewById(R.id.spiner_filter);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_filter);
        spiner_filter.setAdapter(adapter);
    }

    @Override
    protected void innitData() {

    }

    private void innitRecycleViewOrderFood() {
        rcv_history_order = root.findViewById(R.id.rcv_history_order);
        historyOrderAdapter = new RcvHistoryOrderAdapter(getContext(), orderProducts);
        rcv_history_order.setAdapter(historyOrderAdapter);
        rcv_history_order.setLayoutManager(new LinearLayoutManager(getContext()));
        ApiFactory.getApiOrder().getListOrderProductByCustomerId(1, 1).enqueue(new BaseCallBack<ArrayList<OrderProduct>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<OrderProduct> result) {
                orderProducts.addAll(result);
                coppyOrders.addAll(orderProducts);
                historyOrderAdapter.notifyDataSetChanged();
            }
        });

        //click
        historyOrderAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent i = new Intent(getContext(), DetailOrderActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Order", orderProducts.get(postion));
                i.putExtras(b);
                startActivity(i);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

    }

    ArrayList<OrderProduct> coppyOrders = new ArrayList<>();
    private void filterHistoryOrderFood(int staus) {
        orderProducts.clear();
        historyOrderAdapter.notifyDataSetChanged();
        if (staus == 3) {
            orderProducts.addAll(coppyOrders);
            historyOrderAdapter.notifyDataSetChanged();
            if (swipe_refresh.isRefreshing()) {
                swipe_refresh.setRefreshing(false);
            }
            return;
        }
        ApiFactory.getApiOrder().getListOrderByState(staus, 1).enqueue(new BaseCallBack<ArrayList<OrderProduct>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<OrderProduct> result) {
                orderProducts.addAll(result);
                historyOrderAdapter.notifyDataSetChanged();
                if (swipe_refresh.isRefreshing()) {
                    swipe_refresh.setRefreshing(false);
                }
            }
        });
    }

    private void innitRecycleViewOrderTable() {
        rcv_history_order_table = root.findViewById(R.id.rcv_history_order_table);
        orderTableAdapter = new RcvHistoryOrderTableAdapter(getContext(),orderTables);
        rcv_history_order_table.setAdapter(orderTableAdapter);
        rcv_history_order_table.setLayoutManager(new LinearLayoutManager(getContext()));
        ApiFactory.getApiOrder().getListOrderTableByIdCustomer(1).enqueue(new BaseCallBack<ArrayList<OrderTable>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<OrderTable> result) {
                orderTables.addAll(result);
                coppyTables.addAll(orderTables);
                orderTableAdapter.notifyDataSetChanged();
            }
        });
    }

    ArrayList<OrderTable> coppyTables = new ArrayList<>();
    private void filterHistoryOrderTable(int staus) {
        orderTables.clear();
        orderTableAdapter.notifyDataSetChanged();
        if (staus == 0) {
            orderTables.addAll(coppyTables);
            historyOrderAdapter.notifyDataSetChanged();
            if (swipe_refresh.isRefreshing()) {
                swipe_refresh.setRefreshing(false);
            }
            return;
        }
        ApiFactory.getApiOrder().getListOrderTableByState( 1,staus).enqueue(new BaseCallBack<ArrayList<OrderTable>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<OrderTable> result) {
                orderTables.addAll(result);
                orderTableAdapter.notifyDataSetChanged();
                if (swipe_refresh.isRefreshing()) {
                    swipe_refresh.setRefreshing(false);
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        if (isOrderfood){
            orderProducts.clear();
            adapter.notifyDataSetChanged();
            int i = spiner_filter.getSelectedItemPosition();
            if (i == 0){
                filterHistoryOrderFood(3);
            } else if (i == 1){
                filterHistoryOrderFood(2);
            } else if (i == 2){
                filterHistoryOrderFood(1);
            } else if (i == 3){
                filterHistoryOrderFood(-1);
            }
        } else {
            orderTables.clear();
            orderTableAdapter.notifyDataSetChanged();
            int i = spiner_filter.getSelectedItemPosition();
            if (i==0) {
                filterHistoryOrderTable(0);
            } else if (i == 1){
                filterHistoryOrderTable(1);
            } else if (i == 3){
                filterHistoryOrderTable(3);
            } else if (i == 2){
                filterHistoryOrderTable(4);
            }
        }
    }
}
