package com.ptpmcn.orderfood.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ptpmcn.orderfood.R;

/**
 * Created by tungts on 10/19/2017.
 */

public class GlideHelper {

    public static void loadImageByDrawable(Context context, ImageView imageView, int drawableImage){
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_error)
                .error(R.drawable.ic_error);

        Glide.with(context)
                .load(drawableImage)
                .apply(options)
                .into(imageView);
    }

    public static void loadImageByPath(Context context, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_error)
                .error(R.drawable.ic_error);

        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }

}
