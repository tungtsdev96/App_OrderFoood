package com.ptpmcn.orderfood.api.nodejs;

import com.ptpmcn.orderfood.model.CategorySystem;
import com.ptpmcn.orderfood.model.restaurant.ResCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tungts on 10/19/2017.
 */

public interface ApiCategories {

    //category system
    @GET("/api/category/system")
    Call<ArrayList<CategorySystem>> getListCategorySystem();

    //get list category by idRes
    @GET("/api/category")
    Call<ArrayList<ResCategory>> getListCategoryOfRes(@Query("restaurentId") int restaurentId);


}
