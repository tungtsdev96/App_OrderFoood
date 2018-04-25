package com.ptpmcn.orderfood.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.interfaces.AlertListener;

/**
 * Created by tungts on 9/27/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        innitToolbar();
        addControls();
    }

    public abstract int getLayoutResourceId();

    protected abstract void addControls();

    protected void setLeftActionIcon(int resId) {
        toolbar.setNavigationIcon(resId);
    }

    public void innitToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    static ProgressDialog dialog;
    public static void showProgressDialog(Context nContext) {
        try {
            hideProgressDialog();
            dialog = new ProgressDialog(nContext);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(1f);
            dialog.show();
            dialog.setContentView(R.layout.progress_bar_loading);
        } catch (Exception e) {
        }
    }

    public static void hideProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {

        }
    }

    public static boolean isShowProgressDialog() {
        return dialog.isShowing();
    }


    protected void showKeyboard(View target) {
        if (target == null) {
            return;
        }
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void showAlert(final String title, final String message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(BaseActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.ok, null).create()
                        .show();
            }
        });
    }

    protected void showAlert(final String title, final String message, final String yesTitle, final String noTitle, final AlertListener listener) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
              AlertDialog dialog =  new AlertDialog.Builder(BaseActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(false)
                        .setIcon(getResources().getDrawable(R.mipmap.icon_app))
                        .setPositiveButton(yesTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null) {
                                    listener.onYesClicked();
                                }
                            }
                        })
                        .setNegativeButton(noTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null) {
                                    listener.onNoClicked();
                                }
                            }
                        }).create();
                        dialog.show();
                        Button buttonbackground = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);;
                        buttonbackground.setTextColor(getResources().getColor(R.color.colorgreen));

                        Button buttonbackground1 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        buttonbackground1.setTextColor(getResources().getColor(R.color.colorgreen));
            }
        });
    }

}
