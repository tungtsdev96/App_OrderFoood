package com.ptpmcn.orderfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptpmcn.orderfood.R;

/**
 * Created by tungts on 9/29/2017.
 */

public class ProfileFragment extends Fragment {

    private View root;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragmnet_profile,container,false);
        return root;
    }

}
