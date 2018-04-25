package com.ptpmcn.orderfood.fragment.auth;

import android.annotation.SuppressLint;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.fragment.BaseFragment;

/**
 * Created by tungts on 11/27/2017.
 */

public class LoginFragment extends BaseFragment {

    private static LoginFragment loginFragment;

    @SuppressLint("ValidFragment")
    private LoginFragment() {
    }

    public static LoginFragment newInstance(String title) {
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        return loginFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected void addControls() {

    }

    @Override
    protected void innitData() {

    }
}
