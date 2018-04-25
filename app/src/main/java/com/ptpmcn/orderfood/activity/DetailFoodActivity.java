package com.ptpmcn.orderfood.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.orderfood.CartActivity;
import com.ptpmcn.orderfood.adapter.orderfood.ClickButtonAddToCart;
import com.ptpmcn.orderfood.adapter.orderfood.OrderFoodAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.model.restaurant.ResCategory;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by tungts on 10/1/2017.
 */

public class DetailFoodActivity extends BaseActivity implements View.OnClickListener,RecycleViewItemClick, ClickButtonAddToCart{

    private CollapsingToolbarLayout collapse;
    private AppBarLayout app_bar_layout;
    private Restaurant restaurant;
    private Food food;
    private boolean isLike;
    private ImageView img_backdrop;
    private FloatingActionButton fabLikeFood;
    private ProgressBar progress;

    ///cart
    private TextView btn_delete_order;
    private TextView btn_continue_order;
    private TextView tv_infor_cart;
    private RelativeLayout rlt_infor_cart;

    //food of res
    private RecyclerView rcv_order_food;
    private OrderFoodAdapter orderFoodAdapter;
    ArrayList arrRcvOrderFood;
    ArrayList<ResCategory> arrCatOfRes;
    HashMap<ResCategory,ArrayList<Food>> mapFood;

    HashMap<Food,OrderDetail> map_order_product_detail = new HashMap<>();

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_order_food;
    }

    @Override
    protected void addControls() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress = (ProgressBar) findViewById(R.id.progress);
        getFood();
        getRestaurant();

        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        img_backdrop = (ImageView) findViewById(R.id.img_backdrop);
        GlideHelper.loadImageByPath(this,img_backdrop,food.getProduct_image());
        fabLikeFood = (FloatingActionButton) findViewById(R.id.fabLikeFood);
        ApiFactory.getApiFoods().checkLike(1,food.getProduct_id()).enqueue(new BaseCallBack<ResponseBody>(this) {
            @Override
            public void onSuccess(ResponseBody result) {
                try {
                    JSONObject object = new JSONObject(result.string());
                    if (object.getInt("like") == 1){
                        Log.e("like",1+"");
                        isLike = true;
                        fabLikeFood.setImageResource(R.drawable.ic_favorite);
                    } else {
                        Log.e("like",0+"");
                        isLike = false;
                        fabLikeFood.setImageResource(R.drawable.ic_favorite_border);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        ///infor Cart
        rlt_infor_cart = (RelativeLayout) findViewById(R.id.rlt_infor_cart);
        tv_infor_cart = (TextView) findViewById(R.id.tv_infor_cart);
        btn_continue_order = (TextView) findViewById(R.id.btn_continue_order);
        btn_delete_order = (TextView) findViewById(R.id.btn_delete_order);
        updateToInforCart();

        innitRecycleViewFoodOfRes();
        addEvents();
    }

    private Food getFood(){
        if (food == null){
            food = (Food) getIntent().getExtras().get("Food");
        }
        return food;
    }

    private Restaurant getRestaurant(){
        if (restaurant == null){
            ApiFactory.getApiRestaurants().getRestaurantByIdRes(food.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(this) {
                @Override
                public void onSuccess(ArrayList<Restaurant> result) {
                    restaurant = result.get(0);
                }
            });
        }
        return restaurant;
    }

    //innit Rcv Food Of ress
    private void innitRecycleViewFoodOfRes() {
        rcv_order_food = (RecyclerView) findViewById(R.id.rcv_order_food);
        mapFood = new HashMap<>();
        arrRcvOrderFood = new ArrayList();
        arrRcvOrderFood.add(food);
        orderFoodAdapter = new OrderFoodAdapter(this, arrRcvOrderFood, rcv_order_food);
        rcv_order_food.setAdapter(orderFoodAdapter);
        rcv_order_food.addItemDecoration(new com.ptpmcn.orderfood.view.DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rcv_order_food.setLayoutManager(new LinearLayoutManager(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        },1000);
    }

    private void initData() {
        innitCategoryOfRes();
    }

    private void innitCategoryOfRes() {
        //get List Category Of Res
        ApiFactory.getApiCategory().getListCategoryOfRes(food.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<ResCategory>>(this) {
            @Override
            public void onSuccess(ArrayList<ResCategory> result) {
                arrCatOfRes = result;
                for (int i = 0;i<arrCatOfRes.size();i++) {
                    getListFoodByIdCat(arrCatOfRes.get(i),false);
                    if (i == arrCatOfRes.size() - 1){
                        getListFoodByIdCat(arrCatOfRes.get(i),true);
                    }
                }
            }
        });
    }

    private void getListFoodByIdCat(final ResCategory cat, final boolean isLast){
        ApiFactory.getApiFoods().getListFoodOfResCategory(cat.getCategory_id()).enqueue(new BaseCallBack<ArrayList<Food>>(DetailFoodActivity.this) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                Log.e("Food",result.size()+"");
                mapFood.put(cat,result);
                if (isLast){
                    updateRecycleView();
                }
            }
        });
    }

    private void updateRecycleView() {
        arrRcvOrderFood.add(restaurant);
        for (ResCategory cat: mapFood.keySet()) {
            arrRcvOrderFood.add(cat);
            arrRcvOrderFood.addAll(mapFood.get(cat));
            arrRcvOrderFood.add(cat.getCategory_id());
        }
        rcv_order_food.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        orderFoodAdapter.notifyDataSetChanged();
    }

    private void addEvents() {
        btn_continue_order.setOnClickListener(this);
        btn_delete_order.setOnClickListener(this);
        rlt_infor_cart.setOnClickListener(this);
        fabLikeFood.setOnClickListener(this);
        orderFoodAdapter.setOnRecycleViewItemClick(this);
        orderFoodAdapter.setOnClickButtonAddToCart(this);
    }

    private void showDialogOrderFood(final Food food, final boolean isOrdered) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_add_food_to_cart);
        ImageView img_product = dialog.findViewById(R.id.img_product);
        GlideHelper.loadImageByPath(this,img_product,food.getProduct_image());
        TextView tv_name_food = dialog.findViewById(R.id.tv_name_food);
        tv_name_food.setText(food.getProduct_name());
        TextView tv_price_food = dialog.findViewById(R.id.tv_new_price_food);
        tv_price_food.setText(String.format(getString(R.string.price),food.getProduct_price()));
        final TextView tv_number_of_food_order = dialog.findViewById(R.id.tv_number_of_food_order);
        final EditText edt_description = dialog.findViewById(R.id.edt_description);
        if (isOrdered){
            tv_number_of_food_order.setText(map_order_product_detail.get(food).getDetail_quantity()+"");
            edt_description.setText(map_order_product_detail.get(food).getDescription());
        }
        ImageView img_add_food = dialog.findViewById(R.id.img_add_food);
        ImageView img_remove_food = dialog.findViewById(R.id.img_remove_food);
        RelativeLayout rlt_add_to_cart = dialog.findViewById(R.id.rlt_add_to_cart);
        img_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number_of_food_order = Integer.parseInt(tv_number_of_food_order.getText().toString());
                number_of_food_order++;
                tv_number_of_food_order.setText(number_of_food_order+"");
            }
        });

        img_remove_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number_of_food_order = Integer.parseInt(tv_number_of_food_order.getText().toString());
                if (number_of_food_order == 1){
                    return;
                }
                number_of_food_order--;
                tv_number_of_food_order.setText(number_of_food_order+"");
            }
        });

        rlt_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(tv_number_of_food_order.getText().toString());
                String description = "";
                if (!edt_description.getText().toString().isEmpty()){
                    description = edt_description.getText().toString();
                }
                if (isOrdered){
                    map_order_product_detail.get(food).setDescription(description);
                    map_order_product_detail.get(food).setDetail_quantity(quantity);
                } else {
                    OrderDetail orderDetail = new OrderDetail(food.getProduct_id(),quantity);
                    orderDetail.setDescription(description);
                    map_order_product_detail.put(food,orderDetail);
                }
                updateToInforCart();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void updateToInforCart() {
        int number = 0;
        int price = 0;
        for (Food food:map_order_product_detail.keySet()){
            OrderDetail orderDetail = map_order_product_detail.get(food);
            number+= orderDetail.getDetail_quantity();
            price+= food.getProduct_price()*orderDetail.getDetail_quantity();
            orderDetail.setFood(food);
            Cart.getInstance().getOrderDetails().add(orderDetail);
        }
        tv_infor_cart.setText(String.format(getString(R.string.infor_cart),number,price));
    }

    private void setLike(){
        if (isLike){
            //post unlike
            ApiFactory.getApiCustomer().unLike(getFood().getProduct_id(), AccountUtil.fakeCustomer().getCustomer_id()).enqueue(new BaseCallBack<ResponseBody>(this) {
                @Override
                public void onSuccess(ResponseBody result) {
                    fabLikeFood.setImageResource(R.drawable.ic_favorite_border);
                    isLike = false;
                    Toast.makeText(DetailFoodActivity.this, "Đã bỏ thích", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //post like
            ApiFactory.getApiCustomer().like(getFood().getProduct_id(), AccountUtil.fakeCustomer().getCustomer_id()).enqueue(new BaseCallBack<ResponseBody>(this) {
                @Override
                public void onSuccess(ResponseBody result) {
                    fabLikeFood.setImageResource(R.drawable.ic_favorite);
                    isLike = true;
                    Toast.makeText(DetailFoodActivity.this, "Đã thích", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabLikeFood:
                setLike();
                break;
            case R.id.rlt_infor_cart:
                goToCartActivity();
                break;
            case R.id.btn_continue_order:
                goToCartActivity();
                break;
            case R.id.btn_delete_order:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToCartActivity(){
        if (map_order_product_detail.size() == 0){
            Toast.makeText(this, "Hãy thêm món ăn bạn muốn đặt đã", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(DetailFoodActivity.this,CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.Key.KEY_CART,map_order_product_detail);
        bundle.putSerializable(Constant.Key.KEY_RESTAURANT,this.restaurant);
        intent.putExtras(bundle);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onItemClickRecycleView(int postion) {
        if (arrRcvOrderFood.get(postion) instanceof Food){
            if (map_order_product_detail.get(arrRcvOrderFood.get(postion)) != null){
                showDialogOrderFood((Food) arrRcvOrderFood.get(postion),true);
                return;
            }
            showDialogOrderFood((Food) arrRcvOrderFood.get(postion),false);
        }
    }

    @Override
    public void onItemLongClickRecycleView(int postion) {

    }

    @Override
    public void itemAddProductToCart(Food food, OrderDetail orderDetail) {
        if (map_order_product_detail.get(food) != null){
            return;
        }
        map_order_product_detail.put(food,orderDetail);
        updateToInforCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map_order_product_detail.clear();
        if (Cart.getInstance().getOrderDetails().size() > 0){
            ArrayList<OrderDetail> orderDetails = Cart.getInstance().getOrderDetails();
            for (OrderDetail orderDetail:orderDetails){
                map_order_product_detail.put(orderDetail.getFood(),orderDetail);
            }
            updateToInforCart();
        }
    }
}
