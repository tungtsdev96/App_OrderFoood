package com.ptpmcn.orderfood.api;

import com.ptpmcn.orderfood.model.google.PathGoogleResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tungts on 12/2/2017.
 */

public interface ApiGoogle {

    @GET("/maps/api/directions/json")
    Call<PathGoogleResponse> getDirection(@Query("origin") String origin,
                                          @Query("destination") String destination,
                                          @Query("key") String key);

}
