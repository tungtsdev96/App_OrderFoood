package com.ptpmcn.orderfood.api.nodejs;

import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.Cart;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.ordertable.OrderTable;
import com.ptpmcn.orderfood.model.ordertable.Table;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tungts on 11/24/2017.
 */

public interface ApiOrder {

    //////Order Food
    ///get order product
    @GET("/api/order/get")
    Call<OrderProduct> getOrderProductById(@Query("orderId") int orderId);

    //get detail food of order product
    @GET("/api/product/get")
    Call<ArrayList<OrderDetail>> getProductByOrderId(@Query("orderId") int orderId);

    //get list order proguct by customer id
    @GET("/api/order/get")
    Call<ArrayList<OrderProduct>> getListOrderProductByCustomerId(@Query("customerId") int customerId,
                                                                  @Query("page") int page);

    //post
    @POST("/api/order/post")
    Call<ResponseBody> orderFood(@Body Cart cart);

    //get order by state
    @GET("/api/order/filter?page=1")
    Call<ArrayList<OrderProduct>> getListOrderByState(@Query("state") int state,
                                                      @Query("customerId") int customerId);

    //feedback
    @POST("/api/order/feedback")
    @FormUrlEncoded
    Call<ResponseBody> feedBack(@Field("feedBack") String feedBack,
                                @Field("orderId") int orderId);



    //////Order Table
    @GET("/api/get/order-table")
    Call<ArrayList<OrderTable>> getListOrderTableByIdCustomer(@Query("customerId") int customerId);

    @GET("/api/get/order-table")
    Call<ArrayList<OrderTable>> getListOrderTableByState(@Query("customerId") int customerId,
                                                         @Query("state") int state);
    @GET("/api/table/blank-table")
    Call<ArrayList<Table>> searchTable(@Query("restaurentId") int restaurentId,
                                       @Query("startTime") String startTime,
                                       @Query("endTime") String endTime);

    @POST("/api/table/post")
    Call<ResponseBody> postOrderTable(@Body OrderTable orderTable);

}
