package com.ptpmcn.orderfood.fragment.auth;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.fragment.BaseFragment;
import com.ptpmcn.orderfood.model.Customer;
import com.ptpmcn.orderfood.utils.AccountUtil;

import java.util.ArrayList;

/**
 * Created by tungts on 11/27/2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    EditText edt_user_name;
    EditText edt_pass;
    CardView btn_login;

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
        edt_user_name = getRootView().findViewById(R.id.edt_user_name);
        edt_pass = getRootView().findViewById(R.id.edt_pass);
        edt_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btn_login = getRootView().findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void innitData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login){
            hideKeyboard();
            String user = edt_user_name.getText().toString();
            String pass = edt_pass.getText().toString();
            if (user.isEmpty() || pass.isEmpty()){
                Toast.makeText(getContext(), "Hãy điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            ApiFactory.getApiCustomer().login(user, pass).enqueue(new BaseCallBack<ArrayList<Customer>>(getContext()) {
                @Override
                public void onSuccess(ArrayList<Customer> result) {
                    if (result != null){
                        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        AccountUtil.getInstance(getContext()).setCustomer(result.get(0));
                        getActivity().onBackPressed();
                        return;
                    }
                    Toast.makeText(getContext(), "Sai tên tài khoản hoặc mât khẩu!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
