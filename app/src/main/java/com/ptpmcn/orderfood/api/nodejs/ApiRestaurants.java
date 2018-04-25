package com.ptpmcn.orderfood.api.nodejs;

import android.widget.RatingBar;

import com.ptpmcn.orderfood.model.ordertable.Table;
import com.ptpmcn.orderfood.model.restaurant.Comment;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.model.restaurant.ScoreRating;

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
 * Created by tungts on 10/19/2017.
 */

public interface ApiRestaurants {

    //get res by id
    @GET("/api/restaurent/search")
    Call<ArrayList<Restaurant>> getRestaurantByIdRes(@Query("restaurentId") int restaurentId);

    //post comment
    @POST("/api/comment/post")
    @FormUrlEncoded
    Call<ResponseBody> addComent(@Field("customerId") int customerId,
                                 @Field("restaurentId") int restaurentId,
                                 @Field("content") String content);

    //get comment
    @GET("/api/comment/get")
    Call<ArrayList<Comment>> getComment(@Query("restaurentId") int restaurentId,
                                        @Query("page") int page);

    //get List restaurent
    @GET("/api/restaurent/get")
    Call<ArrayList<Restaurant>> getListRes();


    //search Res
    @GET("/api/restaurent/search")
    Call<ArrayList<Restaurant>> searchRestaurant(@Query("restaurentName") String restaurentName);

    //posst rating
    @POST("/api/rating/post")
    Call<ResponseBody> rating(@Body ScoreRating rating);

    //score average rating
    @GET("/api/rating/get")
    Call<ScoreRating> getScoreAverageOfRes(@Query("restaurentId") int restaurentId);

    @GET("/api/table/get-all")
    Call<ArrayList<Table>> getListTableByResId(@Query("restaurentId") int restaurentId);
}
