package com.ptpmcn.orderfood.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 5/12/2018.
 */

public class CodeIntroDialog extends DialogFragment implements View.OnClickListener {

    Customer customer;
    TextView tv_send;
    EditText edt_intro_code;

    public static CodeIntroDialog newInstance(Customer customer, NotyfiInputCode onNotyfiInputCode){
        CodeIntroDialog dialog = new CodeIntroDialog();
        dialog.customer = customer;
        dialog.onNotyfiInputCode = onNotyfiInputCode;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CodeIntroDialog;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_code_intro, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_send = view.findViewById(R.id.tv_send);
        edt_intro_code = view.findViewById(R.id.edt_intro_code);
        tv_send.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_send:
                String introcode = edt_intro_code.getText().toString();
                if (!introcode.isEmpty()){
                    ApiFactory.getApiCustomer().inputIntroCode(customer.getCustomer_id(), introcode).enqueue(new BaseCallBack<ResponseBody>(getActivity()) {
                        @Override
                        public void onSuccess(ResponseBody result) {
                            try {
                                JSONObject object = new JSONObject(result.string());
                                if (object.get("result").equals("OK")){
                                    if (onNotyfiInputCode != null){
                                        onNotyfiInputCode.onSuccess(true);
                                    }
                                    dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Code không hợp lệ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Không được để trống" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    NotyfiInputCode onNotyfiInputCode;

    public interface NotyfiInputCode{
        void onSuccess(boolean isSuccess);
    }
}
