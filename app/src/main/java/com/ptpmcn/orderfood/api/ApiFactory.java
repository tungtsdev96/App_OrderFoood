package com.ptpmcn.orderfood.api;

import android.support.annotation.NonNull;

import com.ptpmcn.orderfood.api.nodejs.ApiCategories;
import com.ptpmcn.orderfood.api.nodejs.ApiCustomer;
import com.ptpmcn.orderfood.api.nodejs.ApiFoods;
import com.ptpmcn.orderfood.api.nodejs.ApiOrder;
import com.ptpmcn.orderfood.api.nodejs.ApiRestaurants;
import com.ptpmcn.orderfood.utils.constant.ConfigApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tungts on 7/25/2017.
 */

public final class ApiFactory {

    private static Retrofit retrofitNodeJs;
    private static Retrofit retrofitGoogleMapsApi;

    private ApiFactory(){}

    @NonNull
    public static ApiCategories getApiCategory() {
        return getRetrofitApiNodeJs().create(ApiCategories.class);
    }

    @NonNull
    public static ApiFoods getApiFoods() {
        return getRetrofitApiNodeJs().create(ApiFoods.class);
    }

    @NonNull
    public static ApiRestaurants getApiRestaurants() {
        return getRetrofitApiNodeJs().create(ApiRestaurants.class);
    }

    @NonNull
    public static ApiOrder getApiOrder() {
        return getRetrofitApiNodeJs().create(ApiOrder.class);
    }

    @NonNull
    public static ApiCustomer getApiCustomer() {
        return getRetrofitApiNodeJs().create(ApiCustomer.class);
    }

    @NonNull
    public static ApiGoogle getApiGoogleMaps() {
        return getRetrofitGoogleMapApi().create(ApiGoogle.class);
    }

    private static Retrofit getRetrofitGoogleMapApi(){
        if (retrofitGoogleMapsApi == null){
            synchronized (ApiFactory.class){
                retrofitGoogleMapsApi = new Retrofit.Builder()
                        .baseUrl(ConfigApi.URL_GOOGLE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient())
                        .build();
            }
        }
        return retrofitGoogleMapsApi;
    }
    @NonNull
    private static Retrofit getRetrofitApiNodeJs() {
        if(retrofitNodeJs == null ) {
            synchronized(ApiFactory.class) {
                retrofitNodeJs = new Retrofit.Builder()
                        .baseUrl(ConfigApi.URL_NODEJS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient())
                        .build();
            }
        }
        return retrofitNodeJs;
    }
    @NonNull
    private static OkHttpClient okHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(ConfigApi.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ConfigApi.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ConfigApi.WRITE_TIMEOUT, TimeUnit.SECONDS);
        return httpClient.build();
    }

}
