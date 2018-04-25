package com.ptpmcn.orderfood.api.nodejs;


import com.ptpmcn.orderfood.model.Food;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tungts on 7/23/2017.
 */

public interface ApiFoods {

    //food by id
    @GET("/api/product/get")
    Call<ArrayList<Food>> getFoodById(@Query("productId") int productId);

    //food recommended
    @GET("/api/product/recommended")
    Call<ArrayList<Food>> getListRecommendFoods(@Query("customerId") int customerId,
                                                @Query("page") int page);

    //food recommended res
    @GET("/api/product/getBestByRestaurent")
    Call<ArrayList<Food>> getListRecommendFoodsByRes(@Query("restaurentId") int restaurentId,
                                                     @Query("page") int page);

    //lastest food
    @GET("/api/product/lastest")
    Call<ArrayList<Food>> getListNewestFoods(@Query("page") int page);

    //food of resaurant's category
    @GET("/api/product/get")
    Call<ArrayList<Food>> getListFoodOfResCategory(@Query("categoryId") int categoryId);

    //best buy
    @GET("/api/product/best-buy")
    Call<ArrayList<Food>> getListFoodBestBuy(@Query("page") int page);

    //search Food
    @GET("/api/product/search-by-name?page=1&filter=price-up")
    Call<ArrayList<Food>> searchFood(@Query("customerId") int customerId,
                                     @Query("name") String name);

    //get food by catsystem filter
    @GET("/api/product/system_category")
    Call<ArrayList<Food>> getFoodByCatSystem(@Query("systemCategoryId") int systemCategoryId,
                                             @Query("filter") String filter,
                                             @Query("page") int page);

    @GET("/api/like/get-like-by-customer")
    Call<ResponseBody> checkLike(@Query("customerId") int customerId,
                             @Query("productId") int productId);

    @GET("/api/product/like")
    Call<ArrayList<Food>> getListFoodLikedByIdCustomer(@Query("customerId") int customerId,
                                                       @Query("page") int page);

}
