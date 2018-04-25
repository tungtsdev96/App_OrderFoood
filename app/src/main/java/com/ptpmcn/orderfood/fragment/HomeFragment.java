package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.ListFoodActivity;
import com.ptpmcn.orderfood.activity.DetailFoodActivity;
import com.ptpmcn.orderfood.adapter.CategoryAdapter;
import com.ptpmcn.orderfood.adapter.ViewPagerAdapter;
import com.ptpmcn.orderfood.adapter.food.FoodGridAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.CategorySystem;
import com.ptpmcn.orderfood.model.Food;

import java.util.ArrayList;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * Created by tungts on 9/27/2017.
 */

public class HomeFragment extends BaseFragment {

    private View root;

    private ProgressBar progress_bar_category;
    private RecyclerView rcv_category_food;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategorySystem> listCategorySystem;

    private ProgressBar progress_bar_best_buy;
    private RecyclerView rcv_food_best_buy;
    private ArrayList<Food> listFoodBestBuy;
    private FoodGridAdapter foodBestOrderAdapter;

    private ProgressBar progress_bar_recommended;
    private RecyclerView rcv_food_recommended;
    private ArrayList<Food> listRecommendFood;
    private FoodGridAdapter foodRecommendesAdapter;

    private TabLayout tab_main;
    private ViewPager view_pager_main;
    private ViewPagerAdapter viewPagerAdapter;

    private ViewFlipper viewFlipper;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void addControls() {
        root = getRootView();
        innitRcvCategorySystem();
        innitRcvFoodBestBuy();
        innitRcvFoodRecommend();
        innitViewPager();
        viewFlipper = root.findViewById(R.id.view_flipper);
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(1500);
        addEvents();
    }

    @Override
    protected void innitData(){
        showListCategorySystem();
        showListFoodBestBuy(1);
        showListRecommendFoodByPage(1);
    }

    private void showListCategorySystem() {
        ApiFactory.getApiCategory().getListCategorySystem().enqueue(new BaseCallBack<ArrayList<CategorySystem>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<CategorySystem> result) {
                listCategorySystem.addAll(result);
                categoryAdapter.notifyDataSetChanged();
                rcv_category_food.setVisibility(View.VISIBLE);
                progress_bar_category.setVisibility(View.GONE);

            }
        });
    }

    private void showListRecommendFoodByPage(int page) {
        ApiFactory.getApiFoods().getListRecommendFoods(1,page).enqueue(new BaseCallBack<ArrayList<Food>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                listRecommendFood.addAll(result);
                foodRecommendesAdapter.notifyDataSetChanged();
                rcv_food_recommended.setVisibility(View.VISIBLE);
                progress_bar_recommended.setVisibility(View.GONE);
            }
        });
    }

    private void showListFoodBestBuy(int page) {
        ApiFactory.getApiFoods().getListFoodBestBuy(page).enqueue(new BaseCallBack<ArrayList<Food>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                listFoodBestBuy.addAll(result);
                foodBestOrderAdapter.notifyDataSetChanged();
                rcv_food_best_buy.setVisibility(View.VISIBLE);
                progress_bar_best_buy.setVisibility(View.GONE);
            }
        });
    }

    private void innitViewPager() {
        tab_main = root.findViewById(R.id.tab_main);
        view_pager_main = root.findViewById(R.id.view_pager_main);
        tab_main.setupWithViewPager(view_pager_main);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFrag(FoodMainFragment.newInstance(0),"Đề xuất");
        viewPagerAdapter.addFrag(FoodMainFragment.newInstance(1),"Mua nhiều");
        view_pager_main.setOffscreenPageLimit(3);
        view_pager_main.setAdapter(viewPagerAdapter);
    }

    private void addEvents() {

        categoryAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent intent = new Intent(getContext(),ListFoodActivity.class);
                intent.putExtra("IdCat",listCategorySystem.get(postion).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

        foodBestOrderAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent intent = new Intent(getActivity(),DetailFoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Food",listFoodBestBuy.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

        foodRecommendesAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent intent = new Intent(getActivity(),DetailFoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Food",listRecommendFood.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

    }

    private void innitRcvCategorySystem() {
        progress_bar_category = root.findViewById(R.id.progress_bar_category);
        rcv_category_food = root.findViewById(R.id.rcv_category_food);
        listCategorySystem = new ArrayList();
        categoryAdapter = new CategoryAdapter(getContext(), listCategorySystem,rcv_category_food);
        rcv_category_food.setAdapter(categoryAdapter);
        rcv_category_food.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL,false));
    }

    private void innitRcvFoodBestBuy() {
        progress_bar_best_buy = root.findViewById(R.id.progress_bar_best_buy);
        rcv_food_best_buy = root.findViewById(R.id.rcv_food_placed_many);
        listFoodBestBuy = new ArrayList();
        foodBestOrderAdapter = new FoodGridAdapter(getContext(), listFoodBestBuy, rcv_food_best_buy);
        rcv_food_best_buy.setAdapter(foodBestOrderAdapter);
        rcv_food_best_buy.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL,false));
    }

    private void innitRcvFoodRecommend() {
        progress_bar_recommended = root.findViewById(R.id.progress_bar_recommended);
        rcv_food_recommended = root.findViewById(R.id.rcv_food_recommended);
        listRecommendFood = new ArrayList();
        foodRecommendesAdapter = new FoodGridAdapter(getContext(),listRecommendFood,rcv_food_recommended);
        rcv_food_recommended.setAdapter(foodRecommendesAdapter);
        rcv_food_recommended.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL,false));
    }

}
