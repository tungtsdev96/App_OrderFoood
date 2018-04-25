package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.SearchActivity;
import com.ptpmcn.orderfood.activity.restaurant.DetailRestaurantActivity;
import com.ptpmcn.orderfood.adapter.ResAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;

import java.util.ArrayList;

/**
 * Created by tungts on 12/7/2017.
 */

public class SearchRestaurantFragment extends BaseFragment implements SearchActivity.SearchRes {

    RecyclerView rcv_restaurent;
    ResAdapter searchResAdapter;
    ArrayList<Restaurant> arr_restaurants = new ArrayList<>();

    public static SearchRestaurantFragment newInstance(){
        SearchRestaurantFragment fragment = new SearchRestaurantFragment();
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_food_main;
    }

    @Override
    protected void addControls() {
        rcv_restaurent = root.findViewById(R.id.rcv_food);
        searchResAdapter = new ResAdapter(getContext(),arr_restaurants);
        rcv_restaurent.setAdapter(searchResAdapter);
        rcv_restaurent.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_restaurent.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        searchResAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent i = new Intent(getContext(), DetailRestaurantActivity.class);
                i.putExtra("RestaurantID",arr_restaurants.get(postion).getRestaurent_id());
                i.setAction("Res");
                startActivity(i);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

        try {
            ((SearchActivity)getActivity()).setOnSearchRes(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void innitData() {
        ApiFactory.getApiRestaurants().getListRes().enqueue(new BaseCallBack<ArrayList<Restaurant>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                arr_restaurants.addAll(result);
                arr_coppy_res.addAll(arr_restaurants);
                searchResAdapter.notifyDataSetChanged();
            }
        });
    }

    private ArrayList<Restaurant> arr_coppy_res = new ArrayList<>();
    @Override
    public void search(String key) {
        arr_restaurants.clear();
        searchResAdapter.notifyDataSetChanged();
        ApiFactory.getApiRestaurants().searchRestaurant(key).enqueue(new BaseCallBack<ArrayList<Restaurant>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                arr_restaurants.addAll(result);
                searchResAdapter.notifyDataSetChanged();
            }
        });
    }
}
