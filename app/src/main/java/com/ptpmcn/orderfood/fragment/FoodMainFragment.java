package com.ptpmcn.orderfood.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.adapter.food.FoodLinearAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.Food;

import java.util.ArrayList;

/**
 * Created by tungts on 12/6/2017.
 */

public class FoodMainFragment extends BaseFragment {

    RecyclerView rcv_food;
    FoodLinearAdapter foodLinearAdapter;
    ArrayList<Food> arr_food = new ArrayList<>();
    int type;

    public static FoodMainFragment newInstance(int type){
        FoodMainFragment fragment = new FoodMainFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_food_main;
    }

    @Override
    protected void addControls() {
        rcv_food = root.findViewById(R.id.rcv_food);
//        rcv_food.setNestedyScrollingEnabled(true);
        foodLinearAdapter = new FoodLinearAdapter(getContext(),arr_food,rcv_food);
        rcv_food.setAdapter(foodLinearAdapter);
        rcv_food.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void innitData() {
        if (type == 0){
            ApiFactory.getApiFoods().getListRecommendFoods(1,1).enqueue(new BaseCallBack<ArrayList<Food>>(getActivity()) {
                @Override
                public void onSuccess(ArrayList<Food> result) {
                    arr_food.addAll(result);
                    foodLinearAdapter.notifyDataSetChanged();
                }
            });
        } else if (type == 1){
            ApiFactory.getApiFoods().getListFoodBestBuy(1).enqueue(new BaseCallBack<ArrayList<Food>>(getActivity()) {
                @Override
                public void onSuccess(ArrayList<Food> result) {
                    arr_food.addAll(result);
                    foodLinearAdapter.notifyDataSetChanged();
                }
            });
        }
    }


}
