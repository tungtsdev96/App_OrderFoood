package com.ptpmcn.orderfood.activity.ordertable;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.BaseActivity;
import com.ptpmcn.orderfood.activity.ListOrderOfCustomerActivity;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.ordertable.OrderTable;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.constant.Constant;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 12/12/2017.
 */

public class AddInforOrderTableActivity extends BaseActivity implements View.OnClickListener {

    OrderTable orderTable;

    TextView tv_done;
    ImageView img_back;

    TextView tv_time_order;
    TextView tv_number_of_people;
    EditText edt_note;

    EditText edt_name_cus,edt_phone_cus;
    TextView btn_order_table;

    public OrderTable getOrderTable(){
        orderTable = (OrderTable) getIntent().getExtras().getSerializable("OrderTable");
        return orderTable;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_infor_order_table;
    }

    @Override
    protected void addControls() {
        getOrderTable();
        Log.e("tungts",new GsonBuilder().create().toJson(orderTable));
        tv_done = (TextView) findViewById(R.id.tv_done);
        img_back = (ImageView) findViewById(R.id.img_back);

        tv_time_order = (TextView) findViewById(R.id.tv_time_order);
        tv_number_of_people = (TextView) findViewById(R.id.tv_number_of_people);
        edt_note = (EditText) findViewById(R.id.edt_note);

        edt_name_cus = (EditText) findViewById(R.id.edt_name_cus);
        edt_phone_cus = (EditText) findViewById(R.id.edt_phone_cus);
        btn_order_table = (TextView) findViewById(R.id.btn_order_table);

        innitInfor();
        addEvent();
    }

    private void addEvent() {
        img_back.setOnClickListener(this);
        tv_done.setOnClickListener(this);
        btn_order_table.setOnClickListener(this);
    }

    private void innitInfor() {
        tv_time_order.setText(orderTable.getStart_time());
        tv_number_of_people.setText(orderTable.getNumber_people()+" người");
        edt_name_cus.setText(AccountUtil.fakeCustomer().getCustomer_name());  edt_name_cus.setEnabled(false);
        edt_phone_cus.setText(AccountUtil.fakeCustomer().getCustomer_phone()); edt_phone_cus.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_done:
                postOrder();
                break;
            case R.id.btn_order_table:
                postOrder();
                break;
        }
    }

    private void postOrder() {
        ApiFactory.getApiOrder().postOrderTable(orderTable).enqueue(new BaseCallBack<ResponseBody>(this) {
            @Override
            public void onSuccess(ResponseBody result) {
                Toast.makeText(AddInforOrderTableActivity.this, "Đặt bàn thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddInforOrderTableActivity.this, ListOrderOfCustomerActivity.class);
                intent.setAction("");
                startActivity(intent);

            }
        });
    }
}
