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
import com.ptpmcn.orderfood.model.ordertable.OrderTable;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;

import java.util.ArrayList;

/**
 * Created by tungts on 12/12/2017.
 */

public class RcvWatingOrderTableAdapter extends RecyclerView.Adapter<RcvWatingOrderTableAdapter.WaitingOrderViewHolder> {

    private Context context;
    private ArrayList<OrderTable> arr;
    private LayoutInflater layoutInflater;

    public RcvWatingOrderTableAdapter(Context context, ArrayList<OrderTable> arr) {
        this.context = context;
        this.arr = arr;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public WaitingOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WaitingOrderViewHolder(layoutInflater.inflate(R.layout.item_rcv_waiting_order_table,null));
    }

    @Override
    public void onBindViewHolder(final WaitingOrderViewHolder holder, int position) {
        OrderTable orderTable = arr.get(position);
        ApiFactory.getApiRestaurants().getRestaurantByIdRes(orderTable.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(context) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                holder.tv_restaurant.setText(result.get(0).getRestaurent_name());
            }
        });
        holder.tv_time_start.setText(orderTable.getStart_time());
        holder.tv_number_of_people.setText(orderTable.getNumber_people()+"");
        if (orderTable.getStatus() == -1){

        } else  if (orderTable.getStatus() == 1){
//            holder.tv_confirmed.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
//            holder.tv_confirmed_.setTextColor(context.getResources().getColor(R.color.colorblack));
        } else  if (orderTable.getStatus() == 2){
            holder.tv_confirmed.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
            holder.tv_confirmed_.setTextColor(context.getResources().getColor(R.color.colorblack));
        } else  if (orderTable.getStatus() == 3){
            holder.tv_completed.setBackgroundColor(context.getResources().getColor(R.color.colordarkgreen));
            holder.tv_completed_.setTextColor(context.getResources().getColor(R.color.colorblack));

        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    protected class WaitingOrderViewHolder extends RecyclerView.ViewHolder{

        TextView tv_restaurant,tv_time_start;
        TextView tv_number_of_people;

        TextView tv_confirmed,tv_confirmed_;
        TextView tv_completed,tv_completed_;

        public WaitingOrderViewHolder(View itemView) {
            super(itemView);
            tv_restaurant = itemView.findViewById(R.id.tv_restaurant);
            tv_time_start = itemView.findViewById(R.id.tv_time_start);
            tv_number_of_people = itemView.findViewById(R.id.tv_number_of_people);

            tv_confirmed  = itemView.findViewById(R.id.tv_confirmed);
            tv_confirmed_  = itemView.findViewById(R.id.tv_confirmed_);
            tv_completed  = itemView.findViewById(R.id.tv_completed);
            tv_completed_  = itemView.findViewById(R.id.tv_completed_);
        }
    }

}
