package com.ptpmcn.orderfood.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.interfaces.AlertListener;

/**
 * Created by TuyenTiTon on 9/22/17.
 */

public class BaseDialogFragment extends DialogFragment {

    private ProgressDialog progressDialog;

    protected void showProgressDialog(Context nContext) {
        try {
            hideProgressDialog();
            progressDialog = new ProgressDialog(nContext);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.getWindow().setDimAmount(0f);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_bar_loading);
        } catch (Exception e) {
        }
    }

    protected void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {

        }
    }

    protected static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected void showKeyboard(final View target) {
        if (target == null || getActivity() == null) {
            return;
        }
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void showAlert(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.ok, null).create()
                        .show();
            }
        });
    }

    protected void showAlert(final String title, final String message, final String yesTitle, final String noTitle, final AlertListener listener) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(false)
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
                            }
                        }).create()
                        .show();
            }
        });
    }

    protected void setAttribute(Dialog dialog, boolean canOutside, int gravity, int animation) {
        dialog.setCanceledOnTouchOutside(canOutside);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Dialog_NoActionBar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = gravity;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0.5f);
        dialog.getWindow().getAttributes().windowAnimations = animation;
    }

    protected void setAttribute(Dialog dialog, boolean canOutside, float dimAmount, int animation) {
        dialog.setCanceledOnTouchOutside(canOutside);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Dialog_NoActionBar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(dimAmount);
        dialog.getWindow().getAttributes().windowAnimations = animation;
    }

    protected void setMarginView(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        if (!(view.getLayoutParams() instanceof RelativeLayout.LayoutParams))
            return;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

        layoutParams.setMargins(
                marginLeft,
                marginTop,
                marginRight,
                marginBottom
        );
        view.setLayoutParams(layoutParams);
    }
}
