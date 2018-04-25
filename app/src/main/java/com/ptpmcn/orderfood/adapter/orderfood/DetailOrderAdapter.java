package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.utils.GlideHelper;

import java.util.ArrayList;

/**
 * Created by tungts on 12/10/2017.
 */

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.DetailOrderViewHolder> {

    Context context;
    ArrayList<OrderDetail> orderDetails;
    LayoutInflater layoutInflater;

    public DetailOrderAdapter(Context context, ArrayList<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public DetailOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailOrderViewHolder(layoutInflater.inflate(R.layout.item_detail_order,null));
    }

    @Override
    public void onBindViewHolder(final DetailOrderViewHolder holder, int position) {
        final OrderDetail orderDetail = orderDetails.get(position);
        int id_food = orderDetail.getProduct_id();
        Log.e("detail_order",id_food+"");
        ApiFactory.getApiFoods().getFoodById(id_food).enqueue(new BaseCallBack<ArrayList<Food>>(context) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                GlideHelper.loadImageByPath(context,holder.img_food,result.get(0).getProduct_image());
                holder.tv_name_food.setText(result.get(0).getProduct_name());
                String s =  String.format(context.getString(R.string.price),result.get(0).getProduct_price()) + " x " + orderDetail.getDetail_quantity() + " = " + (String.format(context.getString(R.string.price),orderDetail.getDetail_quantity()*result.get(0).getProduct_price()));
                holder.tv_price.setText(s);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    protected class DetailOrderViewHolder extends RecyclerView.ViewHolder{

        ImageView img_food;
        TextView tv_name_food,tv_price;

        public DetailOrderViewHolder(View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }

}
