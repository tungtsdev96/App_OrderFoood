package com.ptpmcn.orderfood.fragment.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.MainActivity;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.fragment.BaseFragment;
import com.ptpmcn.orderfood.model.Customer;
import com.ptpmcn.orderfood.utils.AccountUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 11/27/2017.
 */

public class RegisterAccountFragment extends BaseFragment implements View.OnClickListener {

    EditText edt_user_name;
    EditText edt_pass;
    EditText edt_first_name;
    EditText edt_last_name;
    EditText edt_phone_email;
    CardView btn_register;

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
        edt_user_name = getRootView().findViewById(R.id.edt_user_name);
        edt_pass = getRootView().findViewById(R.id.edt_pass);
        edt_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edt_first_name = getRootView().findViewById(R.id.edt_first_name);
        edt_last_name = getRootView().findViewById(R.id.edt_last_name);
        edt_phone_email = getRootView().findViewById(R.id.edt_phone_email);
        btn_register = getRootView().findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    protected void innitData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register){
            String user = edt_user_name.getText().toString();
            String pass = edt_pass.getText().toString();
            String first_name = edt_first_name.getText().toString();
            String last_name = edt_last_name.getText().toString();
            String phone_email = edt_phone_email.getText().toString();
            if (user.isEmpty() || pass.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || phone_email.isEmpty()){
                Toast.makeText(getContext(), "Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiFactory.getApiCustomer().register(user,pass,first_name,last_name, first_name + " " + last_name,phone_email, phone_email).enqueue(new BaseCallBack<ResponseBody>(getContext()) {
                @Override
                public void onSuccess(ResponseBody result) {
                    try {
                        Log.e("tungts",result.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    try {
//                        JSONObject object = new JSONObject(result);
//                        int id = (int) object.get("customerId");
//                        ApiFactory.getApiCustomer().getCustomerById(id).enqueue(new BaseCallBack<Customer>(getContext()) {
//                            @Override
//                            public void onSuccess(Customer result) {
//                                Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
//                                AccountUtil.getInstance(getContext()).setCustomer(result);
//                                getActivity().onBackPressed();
//                            }
//                        });
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            });
        }
    }
}
