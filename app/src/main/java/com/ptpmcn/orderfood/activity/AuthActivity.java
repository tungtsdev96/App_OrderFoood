package com.ptpmcn.orderfood.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.adapter.ViewPagerAdapter;
import com.ptpmcn.orderfood.fragment.auth.LoginFragment;
import com.ptpmcn.orderfood.fragment.auth.RegisterAccountFragment;

/**
 * Created by tungts on 11/27/2017.
 */

public class AuthActivity extends BaseActivity{

    TabLayout tab_auth;
    ViewPagerAdapter adapter;
    ViewPager view_pager_auth;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_auth;
    }

    @Override
    protected void addControls() {
        setLeftActionIcon(R.drawable.ic_clear_white);
        tab_auth = (TabLayout) findViewById(R.id.tab_auth);
        view_pager_auth = (ViewPager) findViewById(R.id.view_pager_auth);
        innitViewPager();
    }

    private void innitViewPager() {
        adapter  = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(LoginFragment.newInstance("Login"),"Đăng nhập");
        adapter.addFrag(RegisterAccountFragment.newInstance("Register"),"Đăng kí");
        view_pager_auth.setOffscreenPageLimit(3);
        view_pager_auth.setAdapter(adapter);
        tab_auth.setupWithViewPager(view_pager_auth);
    }

}
