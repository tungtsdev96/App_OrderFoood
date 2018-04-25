package com.ptpmcn.orderfood.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.view.DividerItemDecoration;
import com.ptpmcn.orderfood.adapter.FilterFoodAdapter;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tungts on 9/30/2017.
 */

public class FilterFoodActivity extends BaseActivity {

    RelativeLayout rlt_filter_food;
    RecyclerView rcv_filter_food;

    ArrayList<String> list_filter_type;
    FilterFoodAdapter filterFoodAdapter;

    ArrayList<String> list_filter_detail;

    Intent intent;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_filter_food;
    }

    @Override
    protected void addControls() {
        rcv_filter_food = (RecyclerView) findViewById(R.id.rcv_filter_food);
        rlt_filter_food = (RelativeLayout) findViewById(R.id.rlt_filter_food);
        list_filter_type = new ArrayList<>();
        list_filter_detail = new ArrayList<>(); list_filter_detail.add(getString(R.string.all));
        intent = getIntent();
        if (intent != null && intent.getAction().equals(Constant.Action.ACTION_FILTER_FOOD)){
            setTitle(getString(R.string.filter));
            setLeftActionIcon(R.drawable.ic_close);
            Collections.addAll(list_filter_type,getResources().getStringArray(R.array.filter_food_by));
            filterFoodAdapter = new FilterFoodAdapter(FilterFoodAdapter.TYPE_FILTER_TYPE);
            filterFoodAdapter.setItems(list_filter_type);
            rcv_filter_food.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
            rcv_filter_food.setAdapter(filterFoodAdapter);
            rcv_filter_food.setLayoutManager(new LinearLayoutManager(this));
            filterFoodAdapter.setOnRecycleViewItemClick(new RecycleViewItemClick() {
                @Override
                public void onItemClickRecycleView(int postion) {
                    filterFoodAdapter = new FilterFoodAdapter(FilterFoodAdapter.TYPE_FILTER_DETAIL_OF_TYPE);
                    filterFoodAdapter.setItems(list_filter_detail);
                    rlt_filter_food.setAnimation(AnimationUtils.loadAnimation(FilterFoodActivity.this,R.anim.fade_in));
                    setTitle(getString(R.string.filter));
                    setLeftActionIcon(R.drawable.ic_back_white);
                    rcv_filter_food.addItemDecoration(new DividerItemDecoration(FilterFoodActivity.this,DividerItemDecoration.VERTICAL_LIST));
                    rcv_filter_food.setAdapter(filterFoodAdapter);
                    rcv_filter_food.setLayoutManager(new LinearLayoutManager(FilterFoodActivity.this));
                    switch (postion){
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                }

                @Override
                public void onItemLongClickRecycleView(int postion) {

                }
            });
        } else {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
