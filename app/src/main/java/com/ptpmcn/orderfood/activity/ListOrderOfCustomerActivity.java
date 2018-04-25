package com.ptpmcn.orderfood.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.adapter.ViewPagerAdapter;
import com.ptpmcn.orderfood.fragment.HistoryFragment;
import com.ptpmcn.orderfood.fragment.WaitingOrderFragment;
import com.ptpmcn.orderfood.utils.constant.Constant;

/**
 * Created by tungts on 12/5/2017.
 */

public class ListOrderOfCustomerActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    TextView tab_ordering,tab_history;
    ImageView btn_search;

    ViewPager view_pager_order_history;
    ViewPagerAdapter adapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_list_order_of_customer;
    }

    @Override
    protected void addControls() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorblack));
        }
        innitViewPager();
        innitView();
        addEvents();
    }

    private void innitView() {
        tab_ordering = (TextView) findViewById(R.id.tab_ordering);
        tab_history = (TextView) findViewById(R.id.tab_history);
        if (getIntent().getAction().equals(Constant.Action.ACTION_HISTORY_ORDER)){
            tab_history.setBackgroundResource(R.drawable.bg_tab_history);
            tab_history.setTextColor(getResources().getColor(R.color.colorTextGray));
            tab_ordering.setBackgroundResource(R.drawable.bg_border_while_left);
            tab_ordering.setTextColor(getResources().getColor(R.color.colorwhite));
            view_pager_order_history.setCurrentItem(1);
        } else {
            view_pager_order_history.setCurrentItem(0);
            tab_ordering.setBackgroundResource(R.drawable.bg_tab_waitting_order);
            tab_ordering.setTextColor(getResources().getColor(R.color.colorTextGray));
            tab_history.setBackgroundResource(R.drawable.bg_border_while_right);
            tab_history.setTextColor(getResources().getColor(R.color.colorwhite));
        }
        btn_search = (ImageView) findViewById(R.id.btn_search);
    }

    private void innitViewPager() {
        view_pager_order_history = (ViewPager) findViewById(R.id.view_pager_order_history);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(WaitingOrderFragment.newInstance(),"WaitingOrderFragment");
        adapter.addFrag(HistoryFragment.newInstance(),"HistoryOrderFragment");
        view_pager_order_history.setOffscreenPageLimit(3);
        view_pager_order_history.setAdapter(adapter);
    }

    private void addEvents() {
        tab_history.setOnClickListener(this);
        tab_ordering.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        view_pager_order_history.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            tab_ordering.setBackgroundResource(R.drawable.bg_tab_waitting_order);
            tab_ordering.setTextColor(getResources().getColor(R.color.colorTextGray));
            tab_history.setBackgroundResource(R.drawable.bg_border_while_right);
            tab_history.setTextColor(getResources().getColor(R.color.colorwhite));
        } else {
            tab_history.setBackgroundResource(R.drawable.bg_tab_history);
            tab_history.setTextColor(getResources().getColor(R.color.colorTextGray));
            tab_ordering.setBackgroundResource(R.drawable.bg_border_while_left);
            tab_ordering.setTextColor(getResources().getColor(R.color.colorwhite));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_history:
                tab_history.setBackgroundResource(R.drawable.bg_tab_history);
                tab_history.setTextColor(getResources().getColor(R.color.colorTextGray));
                tab_ordering.setBackgroundResource(R.drawable.bg_border_while_left);
                tab_ordering.setTextColor(getResources().getColor(R.color.colorwhite));
                view_pager_order_history.setCurrentItem(1);
                break;
            case R.id.tab_ordering:
                view_pager_order_history.setCurrentItem(0);
                tab_ordering.setBackgroundResource(R.drawable.bg_tab_waitting_order);
                tab_ordering.setTextColor(getResources().getColor(R.color.colorTextGray));
                tab_history.setBackgroundResource(R.drawable.bg_border_while_right);
                tab_history.setTextColor(getResources().getColor(R.color.colorwhite));
                break;
            case R.id.btn_search:
                Intent intent = new Intent(ListOrderOfCustomerActivity.this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
        }
    }
}
