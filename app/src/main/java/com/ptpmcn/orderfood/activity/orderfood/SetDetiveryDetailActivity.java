package com.ptpmcn.orderfood.activity.orderfood;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.AddLocationActivity;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.activity.MainActivity;
import com.ptpmcn.orderfood.adapter.orderfood.AddLocationOfOrderInforAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.AlertListener;
import com.ptpmcn.orderfood.interfaces.DatePickerListener;
import com.ptpmcn.orderfood.interfaces.TimePickerListener;
import com.ptpmcn.orderfood.model.AddressOrder;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.TimeDistanceUtils;
import com.ptpmcn.orderfood.utils.Utils;
import com.ptpmcn.orderfood.view.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by tungts on 11/25/2017.
 */

public class SetDetiveryDetailActivity extends BaseActivity implements View.OnClickListener,TimePickerListener, DatePickerListener {

    TextView btn_done,btn_search_location;
    RelativeLayout rlt_search_location;
    EditText edt_name_customer,edt_phone_customer;
    ImageView img_contact;
    LinearLayout ll_date_receive,ll_time_receive;
    TextView tv_date,tv_time;

    RecyclerView rcv_location;
    ArrayList<AddressOrder> arr_address = new ArrayList<>();
    AddLocationOfOrderInforAdapter adapterLocation;

    String address="";
    boolean haveDate = false;
    boolean haveTime = false;

    @Override
    public int getLayoutResourceId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_infor_order;
    }

    @Override
    protected void addControls() {
        innitView();
    }

    private void innitView() {
        btn_done = (TextView) findViewById(R.id.tv_done);
        rlt_search_location = (RelativeLayout) findViewById(R.id.rlt_search_location);
        btn_search_location = (TextView) findViewById(R.id.btn_search_location);
        edt_name_customer = (EditText) findViewById(R.id.edt_name_customer);
        edt_name_customer.setText(AccountUtil.fakeCustomer().getCustomer_name());
        edt_phone_customer = (EditText) findViewById(R.id.edt_phone_customer);
        if (Cart.getInstance().getPhoneNumber() != null){
            edt_phone_customer.setText(Cart.getInstance().getPhoneNumber());
        } else {
            edt_phone_customer.setText(AccountUtil.fakeCustomer().getCustomer_phone());
        }
        img_contact = (ImageView) findViewById(R.id.img_contact);
        ll_date_receive = (LinearLayout) findViewById(R.id.ll_date_receive);
        tv_date = (TextView) findViewById(R.id.tv_date);
        if (Cart.getInstance().getDate() != null){
            tv_date.setText(Cart.getInstance().getDate());
        } else {
            tv_date.setText("Chọn ngày");
        }
        ll_time_receive = (LinearLayout) findViewById(R.id.ll_time_receive);
        tv_time = (TextView) findViewById(R.id.tv_time);
        if (Cart.getInstance().getTime() != null) {
            tv_time.setText(Cart.getInstance().getTime());
        }
        rcv_location = (RecyclerView) findViewById(R.id.rcv_location);
        innitRcvLocationOfCustomer();
        addEvents();
    }

    private void addEvents() {
        img_contact.setOnClickListener(this);
        btn_search_location.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        ll_date_receive.setOnClickListener(this);
        ll_time_receive.setOnClickListener(this);
    }

    private void innitRcvLocationOfCustomer() {
        adapterLocation = new AddLocationOfOrderInforAdapter(this,arr_address);
        rcv_location.setAdapter(adapterLocation);
//        rcv_location.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        rcv_location.setLayoutManager(new LinearLayoutManager(this));
        ApiFactory.getApiCustomer().getListAddress(AccountUtil.getInstance(this).getCustomer().getCustomer_id()).enqueue(new BaseCallBack<ArrayList<AddressOrder>>(this) {
            @Override
            public void onSuccess(ArrayList<AddressOrder> result) {
                arr_address.addAll(result);
                adapterLocation.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_time_receive:
                com.ptpmcn.orderfood.view.TimePicker timePicker = new com.ptpmcn.orderfood.view.TimePicker(this);
                timePicker.show(getFragmentManager(),"Time Picker");
                break;
            case R.id.ll_date_receive:
                com.ptpmcn.orderfood.view.DatePicker datePicker = new com.ptpmcn.orderfood.view.DatePicker(this);
                datePicker.show(getFragmentManager(),"Date Picker");
                break;
            case R.id.tv_done:
                if (!updateCartInfor()){
                    return;
                }
                setResult(3333);
                finish();
                break;
            case R.id.btn_search_location:
                Intent intent = new Intent(SetDetiveryDetailActivity.this, AddLocationActivity.class);
                startActivityForResult(intent,666);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == 777){
            arr_address.add((AddressOrder) data.getExtras().getSerializable("Address"));
            adapterLocation.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            showAlert("", "Hủy đăt hàng", "Đồng ý", "Quay lại đặt thêm món", new AlertListener() {
                @Override
                public void onYesClicked() {
                    Intent i = new Intent(SetDetiveryDetailActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

                @Override
                public void onNoClicked() {
                    onBackPressed();
                }
            });
        }
        return true;
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean updateCartInfor() {
        //check time
        String date = tv_date.getText().toString();
        String time = tv_time.getText().toString();
        if (TimeDistanceUtils.checkDate(date) && TimeDistanceUtils.checkTime(time)){
            if (TimeDistanceUtils.checkDateWithCurrentDate(date)){
                Cart.getInstance().setDeliveryTime(tv_date.getText() + " " + tv_time.getText());
            } else {
                showAlert("Thông báo","Chọn sai ngày");
                return false;
            }
        } else {
            showAlert("Thông báo","Hãy chọn ngày và giờ bạn muốn nhận hàng");
            return false;
        }

        //check address
        address = Cart.getInstance().getOrderAddress();
        if (address == null){
            showAlert("Thông báo","Hãy thêm địa chỉ bằng cách chọn địa chỉ hoặc bấm vào ô tìm kiếm địa chỉ");
            return false;
        }

        //check phone number
        if (!edt_phone_customer.getText().toString().isEmpty()){
            Cart.getInstance().setPhoneNumber(edt_phone_customer.getText().toString());
        } else {
            showAlert("Thông báo","Hãy thêm số điện thoại");
            return false;
        }
        return true;
    }

    @Override
    public void time(String time) {
        haveDate = true;
        Cart.getInstance().setTime(time);
        tv_time.setText(time);
    }

    @Override
    public void date(String date) {
        haveTime = true;
        Cart.getInstance().setDate(date);
        tv_date.setText(date);
    }

}
