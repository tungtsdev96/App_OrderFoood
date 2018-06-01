package com.ptpmcn.orderfood.api.nodejs;

import com.ptpmcn.orderfood.model.AddressOrder;
import com.ptpmcn.orderfood.model.Customer;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tungts on 11/24/2017.
 */

public interface ApiCustomer {

    //get customer by id
    @GET("/api/customer")
    Call<Customer> getCustomerById(@Query("id") int id);

    @GET("/api/address-history")
    Call<ArrayList<AddressOrder>> getListAddress(@Query("customerId") int customerId);

    @POST("/api/address-history")
    @FormUrlEncoded
    Call<ResponseBody> addLocation(@Field("customerId") int customerId,
                                   @Field("address") String address);

    @POST("/api/like/post-like")
    @FormUrlEncoded
    Call<ResponseBody> like(@Field("productId") int productId,
                            @Field("customerId") int customerId);



    @POST("/api/like/post-unlike")
    @FormUrlEncoded
    Call<ResponseBody> unLike(@Field("productId") int productId,
                            @Field("customerId") int customerId);

    //login
    @POST("/api/customer/login")
    @FormUrlEncoded
    Call<ArrayList<Customer>> login(@Field("username") String user,
                         @Field("password") String pass);

    //register
    @POST("/api/customer/login")
    @FormUrlEncoded
    Call<ResponseBody> register(@Field("username") String user,
                          @Field("password") String pass,
                          @Field("firstname") String firstname,
                          @Field("lastname") String lastname,
                          @Field("name") String name,
                          @Field("phone") String phone,
                          @Field("email") String email);

    ///input intro
    @GET("/api/customer/input_intro_key")
    Call<ResponseBody> inputIntroCode(@Query("customer_id") int customerId,
                                      @Query("intro_key") String intro_key);

    //update coint
    @GET("/api/customer/update-coin")
    Call<ResponseBody> updateCoin(@Query("customer_id") int customerId,
                                  @Query("coin") int coin);

}
