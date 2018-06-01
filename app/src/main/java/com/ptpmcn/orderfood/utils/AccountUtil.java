package com.ptpmcn.orderfood.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.Customer;

/**
 * Created by tungts on 11/23/2017.
 */

public class AccountUtil {

    private final String CUSTOMER = "user";
    private final String IS_LOGIN = "is_login";
    private static AccountUtil accountUtil;
    private Customer customer;

    private AccountUtil() {
    }

    private static SharedPreferences PREF;
    private static SharedPreferences.Editor editor;

    public static AccountUtil getInstance(Context context) {
        if (accountUtil == null)
            accountUtil = new AccountUtil();
        if (PREF == null) {
            PREF = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        }
        return accountUtil;
    }

    public void setCustomer(Customer customer) {
        editor = PREF.edit();
        editor.putString(CUSTOMER, new Gson().toJson(customer));
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }


    public boolean isLogin() {
        return PREF.getBoolean(IS_LOGIN, false);
    }

    public Customer getCustomer() {
        return new Gson().fromJson(PREF.getString(CUSTOMER, ""), Customer.class);
    }

    public void logout() {
        editor = PREF.edit();
        editor.clear();
        editor.apply();
    }

    //////////////////////////////

    private static Customer c;

    public static Customer fakeCustomer() {
        if (c == null) {
            c = new Customer();
            c.setCustomer_id(1);
            c.setUsername("tungts");
            c.setCustomer_email("tungts@gmail.com");
            c.setCustomer_name("Sơn Tùng");
            c.setCustomer_phone("01687037749");
        }
        return c;
    }
}
