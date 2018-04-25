package com.ptpmcn.orderfood.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.adapter.food.FoodGridAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.LoadMoreRcvListener;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.view.DividerItemDecoration;
import com.ptpmcn.orderfood.adapter.food.FoodLinearAdapter;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.ArrayList;

/**
 * Created by tungts on 9/29/2017.
 */

public class ListFoodActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_sort_food,img_filter_food,img_change_screen_food;
    private LinearLayout ll_detail_food;

    private RecyclerView rcv_delivery;
    private FoodGridAdapter foodGridAdapter;
    private FoodLinearAdapter foodLinearAdapter;
    private ArrayList<Food> listFood;
    private boolean isGrid = false;
    DividerItemDecoration dividerItemDecoration;
    int id_Cat;

    @Override
    protected void onResume() {
        super.onResume();
        if (Cart.getInstance() != null){
            Cart.getInstance().resetCart();
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_list_food;
    }

    @Override
    protected void addControls() {
        setTitle(getString(R.string.navi_delivery));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ll_detail_food = (LinearLayout) findViewById(R.id.ll_detail_food);
        img_sort_food = (ImageView) findViewById(R.id.img_sort_food);
        img_filter_food = (ImageView) findViewById(R.id.img_filter_food);
        img_change_screen_food = (ImageView) findViewById(R.id.img_change_screen_food);
        innitRcvDelivery();
        addEvents();
    }

    private void addEvents() {
        img_sort_food.setOnClickListener(this);
        img_filter_food.setOnClickListener(this);
        img_change_screen_food.setOnClickListener(this);

        foodGridAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent intent = new Intent(ListFoodActivity.this,DetailFoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Food",listFood.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });
        foodLinearAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
            @Override
            public void onItemClickRecycleView(int postion) {
                Intent intent = new Intent(ListFoodActivity.this,DetailFoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Food",listFood.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickRecycleView(int postion) {

            }
        });

        foodGridAdapter.setOnLoadMoreRcvListener(new LoadMoreRcvListener() {
            @Override
            public void onLoadMore() {
                listFood.add(null);
                foodLinearAdapter.notifyDataSetChanged();
                foodGridAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (id_Cat != -1){
                            innitDataToListFood(id_Cat,current_filter);
                        } else {
                            innitDataToListFood();
                        }
                    }
                },2000);
            }
        });

        foodLinearAdapter.setOnLoadMoreRcvListener(new LoadMoreRcvListener() {
            @Override
            public void onLoadMore() {
                listFood.add(null);
                foodLinearAdapter.notifyDataSetChanged();
                foodGridAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (id_Cat != -1){
                            innitDataToListFood(id_Cat,current_filter);
                        } else {
                            innitDataToListFood();
                        }
                    }
                },2000);
            }
        });
    }

    private void innitRcvDelivery() {
        rcv_delivery = (RecyclerView) findViewById(R.id.rcv_delivery);
        listFood = new ArrayList();
        foodGridAdapter = new FoodGridAdapter(this,listFood,rcv_delivery);
        foodLinearAdapter = new FoodLinearAdapter(this,listFood,rcv_delivery);
        dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
        rcv_delivery.addItemDecoration(dividerItemDecoration);
        rcv_delivery.setAdapter(foodLinearAdapter);
        rcv_delivery.setLayoutManager(new LinearLayoutManager(this));
        id_Cat = getIntent().getIntExtra("IdCat",-1);
        if (id_Cat == -1){
            ll_detail_food.setVisibility(View.GONE);
            innitDataToListFood();
        } else {
            ll_detail_food.setVisibility(View.VISIBLE);
            innitDataToListFood(id_Cat,TIME_UP);
        }
    }

    private final String PRICE_UP = "price-up";
    private final String PRICE_DOWN = "price-down";
    private final String TIME_UP = "time-up";
    private String current_filter;
    int page = 0;
    private void innitDataToListFood(int id_Cat,String filter) {
        current_filter = filter;
        page++;
        Log.e("page",page+"");
        ApiFactory.getApiFoods().getFoodByCatSystem(id_Cat,filter,page).enqueue(new BaseCallBack<ArrayList<Food>>(this) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                if (listFood.size()>0 && listFood.get(listFood.size()-1) == null){
                    listFood.remove(listFood.size()-1);
                }
                listFood.addAll(result);
                hideProgressDialog();
                foodGridAdapter.notifyDataSetChanged();  foodGridAdapter.setLoading();
                foodLinearAdapter.notifyDataSetChanged(); foodLinearAdapter.setLoading();
            }
        });
    }

    private void innitDataToListFood(){
        page++;
        ApiFactory.getApiFoods().getListNewestFoods(page).enqueue(new BaseCallBack<ArrayList<Food>>(this) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                if (listFood.size()>0 && listFood.get(listFood.size()-1) == null){
                    listFood.remove(listFood.size()-1);
                }
                listFood.addAll(result);
                foodLinearAdapter.notifyDataSetChanged(); foodGridAdapter.setLoading();
                foodGridAdapter.notifyDataSetChanged(); foodLinearAdapter.setLoading();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.img_sort_food:
                sortFood();
                break;
            case R.id.img_filter_food:
                Intent intent = new Intent(ListFoodActivity.this,FilterFoodActivity.class);
                intent.setAction(Constant.Action.ACTION_FILTER_FOOD);
                startActivityForResult(intent,Constant.RequestCode.REQUEST_FILTER_FOOD);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.img_change_screen_food:
                if (isGrid){
                    isGrid = false;
                    Glide.with(this).load(R.drawable.ic_grid).into(img_change_screen_food);
                    rcv_delivery.addItemDecoration(dividerItemDecoration);
                    rcv_delivery.setAdapter(foodLinearAdapter);
                    rcv_delivery.setLayoutManager(new LinearLayoutManager(this));
                    rcv_delivery.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
                } else {
                    isGrid = true;
                    foodGridAdapter.setGrid(false);
                    Glide.with(this).load(R.drawable.ic_drawer).into(img_change_screen_food);
                    rcv_delivery.removeItemDecoration(dividerItemDecoration);
                    rcv_delivery.setAdapter(foodGridAdapter);
                    rcv_delivery.setLayoutManager(new GridLayoutManager(this,2));
                    rcv_delivery.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
                }
                break;
        }
    }

    private void sortFood() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sort_food);
        final RadioButton rd_price1 = dialog.findViewById(R.id.rd_price1);
        final RadioButton rd_price2 = dialog.findViewById(R.id.rd_price2);
        final RadioButton rd_newest = dialog.findViewById(R.id.rd_newest);
        RadioGroup group = dialog.findViewById(R.id.group_sort);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                page = 1;
                listFood.clear();
                foodGridAdapter.notifyDataSetChanged();
                foodLinearAdapter.notifyDataSetChanged();
                if (rd_price1.isChecked()) {
                    showProgressDialog(getApplicationContext());
                    innitDataToListFood(id_Cat, PRICE_DOWN);
                } else if (rd_price2.isChecked()) {
                    showProgressDialog(getApplicationContext());
                    innitDataToListFood(id_Cat, PRICE_UP);
                } else if (rd_newest.isChecked()){
                    showProgressDialog(getApplicationContext());
                    innitDataToListFood(id_Cat,TIME_UP);
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent(ListFoodActivity.this,SearchActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
