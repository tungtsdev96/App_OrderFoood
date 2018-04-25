package com.ptpmcn.orderfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.MainActivity;
import com.ptpmcn.orderfood.activity.SearchActivity;
import com.ptpmcn.orderfood.activity.ordertable.OrderTableActivity;
import com.ptpmcn.orderfood.adapter.ResAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.googlemap.CurrentLocation;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.constant.Constant;
import com.squareup.haha.perflib.Instance;

import java.util.ArrayList;

/**
 * Created by tungts on 12/11/2017.
 */

public class OrderTableFragment extends BaseFragment {

    public static OrderTableFragment newInstance(){
        OrderTableFragment orderTableFragment = new OrderTableFragment();
        return orderTableFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_view,menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("tungts",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("tungts",newText);
                return false;
            }
        });
    }

    RecyclerView rcv_restaurant;
    ArrayList<Restaurant> arr_restaurants = new ArrayList<>();
    ResAdapter resAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_order_table;
    }

    @Override
    protected void addControls() {
        rcv_restaurant = root.findViewById(R.id.rcv_);
        resAdapter = new ResAdapter(getContext(),arr_restaurants, Constant.Key.KEY_RES);
        rcv_restaurant.setAdapter(resAdapter);
        rcv_restaurant.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_restaurant.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        resAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent i = new Intent(getContext(), OrderTableActivity.class);
                i.putExtra("RestaurantID",arr_restaurants.get(postion).getRestaurent_id());
                startActivity(i);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });
    }

    @Override
    protected void innitData() {
        ApiFactory.getApiRestaurants().getListRes().enqueue(new BaseCallBack<ArrayList<Restaurant>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                arr_restaurants.addAll(result);
                resAdapter.notifyDataSetChanged();
            }
        });
    }

}
