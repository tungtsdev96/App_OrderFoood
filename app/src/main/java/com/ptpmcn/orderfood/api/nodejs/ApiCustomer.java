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
}
