package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 12/5/2017.
 */

public class RcvWaitingOrderFoodAdapter extends RecyclerView.Adapter<RcvWaitingOrderFoodAdapter.WaitingOrderViewHolder> {

    private Context context;
    private List<OrderProduct> arr;
    private LayoutInflater layoutInflater;
    private ClickDetailOrderProduct onClickDetailOrderProduct;

    public RcvWaitingOrderFoodAdapter(Context context, List<OrderProduct> arr) {
        this.context = context;
        this.arr = arr;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnClickDetailOrderProduct(ClickDetailOrderProduct onClickDetailOrderProduct) {
        this.onClickDetailOrderProduct = onClickDetailOrderProduct;
    }

    @Override
    public WaitingOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WaitingOrderViewHolder(layoutInflater.inflate(R.layout.item_rcv_waiting_order_food,null));
    }

    @Override
    public void onBindViewHolder(final WaitingOrderViewHolder holder, int position) {
        OrderProduct orderProduct = arr.get(position);
        ApiFactory.getApiRestaurants().getRestaurantByIdRes(orderProduct.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(context) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                holder.tv_restaurant.setText(result.get(0).getRestaurent_name());
            }
        });
        holder.tv_time_order.setText(orderProduct.getDelivery_time());
        holder.tv_total_of_order.setText(String.format(context.getString(R.string.price),orderProduct.getOrder_cost()));
        if (orderProduct.getOrder_status() == -1){

        } else  if (orderProduct.getOrder_status() == 0){
            holder.tv_confirmed.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
            holder.tv_confirmed_.setTextColor(context.getResources().getColor(R.color.colorblack));
        } else  if (orderProduct.getOrder_status() == 1){
            holder.tv_confirmed.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
            holder.tv_confirmed_.setTextColor(context.getResources().getColor(R.color.colorblack));
            holder.tv_shipping.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
            holder.tv_shipping_.setTextColor(context.getResources().getColor(R.color.colorblack));
        } else  if (orderProduct.getOrder_status() == 2){
            holder.tv_completed.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
            holder.tv_completed_.setTextColor(context.getResources().getColor(R.color.colorblack));

        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    protected class WaitingOrderViewHolder extends RecyclerView.ViewHolder{

        TextView tv_restaurant,tv_time_order,tv_detail_order;
        TextView tv_total_of_order;
        View v_loading;

        TextView tv_confirmed,tv_confirmed_;
        TextView tv_shipping,tv_shipping_;
        TextView tv_completed,tv_completed_;

        public WaitingOrderViewHolder(View itemView) {
            super(itemView);
            tv_restaurant = itemView.findViewById(R.id.tv_restaurant);
            tv_time_order = itemView.findViewById(R.id.tv_time_order);
            tv_detail_order = itemView.findViewById(R.id.tv_detail_order);
            v_loading = itemView.findViewById(R.id.v_loading);
            v_loading.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_loading));
            tv_total_of_order = itemView.findViewById(R.id.tv_total_of_order);
            tv_detail_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickDetailOrderProduct != null){
                        onClickDetailOrderProduct.itemDetailOrderProduct(arr.get(getAdapterPosition()));
                    }
                }
            });
            tv_confirmed  = itemView.findViewById(R.id.tv_confirmed);
            tv_confirmed_  = itemView.findViewById(R.id.tv_confirmed_);
            tv_shipping  = itemView.findViewById(R.id.tv_shipping);
            tv_shipping_  = itemView.findViewById(R.id.tv_shipping_);
            tv_completed  = itemView.findViewById(R.id.tv_completed);
            tv_completed_  = itemView.findViewById(R.id.tv_completed_);
        }
    }

}
