package com.ptpmcn.orderfood.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tungts on 10/19/2017.
 */

public abstract class BaseCallBack<T> implements Callback<T> {

    Context context;

    public BaseCallBack(Context context){
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()){
            if (response.body() != null){
                Log.e("response - " + context.getClass().getSimpleName(), new Gson().toJson(response.body()));
                onSuccess(response.body());
            } else {
                Log.e("BaseCallback - " + context.getClass().getSimpleName(), "Null");
            }
        } else {
            Log.e("BaseCallback - " + context.getClass().getSimpleName(), "Not Success");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure((T) t);
    }

    public abstract void onSuccess(T result);

    public void onFailure(T result) {
        if (result != null)
            Log.e("BaseCallback - "+ context.getClass().getSimpleName(), result.toString());
    }
}
