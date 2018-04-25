package com.ptpmcn.orderfood.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.adapter.ViewPagerAdapter;
import com.ptpmcn.orderfood.fragment.SearchFoodFragment;
import com.ptpmcn.orderfood.fragment.SearchRestaurantFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    ImageView ic_clear;
    EditText edt_search;
    ImageView search;

    TabLayout tab_layout_search;
    ViewPager view_pager_search;
    ViewPagerAdapter adapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_search_food;
    }

    @Override
    protected void addControls() {
        edt_search = (EditText) findViewById(R.id.edt_search);
        ic_clear = (ImageView) findViewById(R.id.ic_clear);
        search = (ImageView) findViewById(R.id.search);
        tab_layout_search = (TabLayout) findViewById(R.id.tab_layout_search);
        view_pager_search = (ViewPager) findViewById(R.id.view_pager_search);
        tab_layout_search.setupWithViewPager(view_pager_search);
        innitViewPager();
        addEvent();
    }

    private void addEvent() {
        ic_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_search.getText().toString() != null){
                    if (onSearch != null){
                        onSearch.search(edt_search.getText().toString());
                    }
                    if (onSearchRes != null){
                        onSearchRes.search(edt_search.getText().toString());
                    }
                }
            }
        });
    }

    SearchFoodFragment fragmentFood = SearchFoodFragment.newInstance();
    SearchRestaurantFragment fragmentRes = SearchRestaurantFragment.newInstance();
    private void innitViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(fragmentFood,"Món ăn");
        adapter.addFrag(fragmentRes,"Nhà hàng");
        view_pager_search.setAdapter(adapter);
        view_pager_search.setOffscreenPageLimit(3);
    }


    private SearchFood onSearch;

    public void setOnSearch(SearchFood onSearch) {
        this.onSearch = onSearch;
    }

    public interface SearchFood{
        void search(String key);
    }

    private SearchRes onSearchRes;

    public void setOnSearchRes(SearchRes onSearchRes) {
        this.onSearchRes = onSearchRes;
    }

    public interface SearchRes{
        void search(String key);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_r,R.anim.slide_out_r);
    }
}
