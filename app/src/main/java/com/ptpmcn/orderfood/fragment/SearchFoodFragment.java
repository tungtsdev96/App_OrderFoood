package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.SearchActivity;
import com.ptpmcn.orderfood.activity.DetailFoodActivity;
import com.ptpmcn.orderfood.adapter.food.FoodLinearAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;

import java.util.ArrayList;

/**
 * Created by tungts on 12/7/2017.
 */

public class SearchFoodFragment extends BaseFragment implements SearchActivity.SearchFood {

    RecyclerView rcv_food;
    FoodLinearAdapter foodLinearAdapter;
    ArrayList<Food> arr_food = new ArrayList<>();

    public static SearchFoodFragment newInstance(){
        SearchFoodFragment fragment = new SearchFoodFragment();
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_food_main;
    }

    @Override
    protected void addControls() {
        rcv_food = root.findViewById(R.id.rcv_food);
        foodLinearAdapter = new FoodLinearAdapter(getContext(),arr_food,rcv_food);
        rcv_food.setAdapter(foodLinearAdapter);
        rcv_food.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_food.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        foodLinearAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                //Food
                Intent i = new Intent(getContext(), DetailFoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Food",arr_food.get(postion));
                i.putExtras(bundle);
                startActivity(i);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

        try {
            ((SearchActivity)getActivity()).setOnSearch(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    int page = 0;
    @Override
    protected void innitData() {
        page++;
        ApiFactory.getApiFoods().getListRecommendFoods(1,page).enqueue(new BaseCallBack<ArrayList<Food>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                arr_food.addAll(result);
                arr_coppy_food.addAll(arr_food);
                foodLinearAdapter.notifyDataSetChanged();
            }
        });
    }

    ArrayList<Food> arr_coppy_food= new ArrayList<>();
    @Override
    public void search(String key) {
        arr_food.clear();
        foodLinearAdapter.notifyDataSetChanged();
        ApiFactory.getApiFoods().searchFood(1,key).enqueue(new BaseCallBack<ArrayList<Food>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                arr_food.addAll(result);
                foodLinearAdapter.notifyDataSetChanged();
            }
        });
    }
}
