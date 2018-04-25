package com.ptpmcn.orderfood.fragment.auth;

import android.annotation.SuppressLint;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.fragment.BaseFragment;

/**
 * Created by tungts on 11/27/2017.
 */

public class RegisterAccountFragment extends BaseFragment {

    private static RegisterAccountFragment registerAccountFragment;

    @SuppressLint("ValidFragment")
    private RegisterAccountFragment(){}

    public static RegisterAccountFragment newInstance(String title){
        if (registerAccountFragment == null){
            registerAccountFragment = new RegisterAccountFragment();
        }
        return registerAccountFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_register;
    }

    @Override
    protected void addControls() {

    }

    @Override
    protected void innitData() {

    }

}
