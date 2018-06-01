package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.MainActivity;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.fragment.dialog.CodeIntroDialog;
import com.ptpmcn.orderfood.model.Customer;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.Utils;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 9/29/2017.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {


    CallbackManager callbackManager;
    ShareDialog shareDialog;

    Customer customer;
    TextView btn_logout;
    TextView tv_xu;
    TextView tv_intro_code;
    RelativeLayout my_notification; //nhap ma gioi thieu
    RelativeLayout connect_social; // chia se mang xh

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragmnet_profile;
    }

    @Override
    protected void addControls() {
        customer = AccountUtil.getInstance(getContext()).getCustomer();
        btn_logout = getRootView().findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        tv_xu = getRootView().findViewById(R.id.tv_xu); tv_xu.setText(customer.getCoin()+"");
        my_notification = getRootView().findViewById(R.id.my_notification);
        my_notification.setOnClickListener(this);
        connect_social = getRootView().findViewById(R.id.connect_social);
        connect_social.setOnClickListener(this);
        tv_intro_code = getRootView().findViewById(R.id.tv_intro_code);
        tv_intro_code.setText("Mã giới thiệu của bạn : " + Html.fromHtml("<b>" + customer.getIntro_key() + "</b>"));

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.e("callbackManager", result.getPostId() + " " +result.toString());
                customer.setCoin(customer.getCoin() + 10);
                tv_xu.setText(customer.getCoin()+"");
                AccountUtil.getInstance(getContext()).setCustomer(customer);
                ApiFactory.getApiCustomer().updateCoin(customer.getCustomer_id(), 10).enqueue(new BaseCallBack<ResponseBody>(getActivity()) {
                    @Override
                    public void onSuccess(ResponseBody result) {
                        Toast.makeText(getActivity(), "Chia sẻ thành công bạn nhận được thêm 10 xu tích lũy", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.e("callbackManager", "onCancel");

            }

            @Override
            public void onError(FacebookException e) {
                Log.e("callbackManager", "onError");

            }
        });
    }

    @Override
    protected void innitData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                AccountUtil.getInstance(getActivity()).logout();
                ((MainActivity)getActivity()).pushFragment(HomeFragment.newInstance());
                Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_notification:
                if (customer.getPut_key() == 0){
                    CodeIntroDialog.newInstance(customer, new CodeIntroDialog.NotyfiInputCode() {
                        @Override
                        public void onSuccess(boolean isSuccess) {
                            ApiFactory.getApiCustomer().updateCoin(customer.getCustomer_id(), 10).enqueue(new BaseCallBack<ResponseBody>(getActivity().getApplicationContext()) {
                                @Override
                                public void onSuccess(ResponseBody result) {
                                    customer.setCoin(customer.getCoin() + 10);
                                    AccountUtil.getInstance(getActivity()).setCustomer(customer);
                                    tv_xu.setText(customer.getCoin()+"");
                                }
                            });
                        }
                    }).show(getActivity().getFragmentManager(), "CodeIntro");
                    return;
                }
                Toast.makeText(getContext(), "Bạn đã nhập mã giới thiệu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.connect_social:
                if (Utils.isNetworkConnected(getContext())){
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id="+getActivity().getPackageName()))
                                .setContentTitle("ashdjkashdkhdaskda")
                                .setShareHashtag(new ShareHashtag.Builder()
                                        .setHashtag("#ConnectTheWorld")
                                        .build())
                                .build();
                        shareDialog.show(linkContent);
                    }

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
