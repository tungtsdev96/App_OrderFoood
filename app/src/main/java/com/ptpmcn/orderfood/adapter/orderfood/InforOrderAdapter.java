package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 12/2/2017.
 */

public class InforOrderAdapter extends RecyclerView.Adapter<InforOrderAdapter.InforOrderViewHolder> {

    List<OrderDetail> details;
    Context context;
    LayoutInflater layoutInflater;

    public InforOrderAdapter(ArrayList<OrderDetail> details, Context context) {
        this.details = details;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public InforOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InforOrderViewHolder(layoutInflater.inflate(R.layout.item_rcv_infor_order,null));
    }

    @Override
    public void onBindViewHolder(InforOrderViewHolder holder, int position) {
        OrderDetail orderDetail = details.get(position);
        holder.tv_number.setText(orderDetail.getDetail_quantity()+"");
        holder.tv_name.setText(orderDetail.getFood().getProduct_name());
        if (orderDetail.getDescription() != null){
            holder.tv_description.setVisibility(View.VISIBLE);
            holder.tv_description.setText(orderDetail.getDescription());
        } else {
            holder.tv_description.setVisibility(View.GONE);
        }
        int price = orderDetail.getDetail_quantity() * orderDetail.getFood().getProduct_price();
        holder.tv_price.setText(String.format(context.getString(R.string.price),price));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    protected class InforOrderViewHolder extends RecyclerView.ViewHolder{

        TextView tv_number,tv_name,tv_description,tv_price;

        public InforOrderViewHolder(View itemView) {
            super(itemView);
            tv_number = itemView.findViewById(R.id.tv_number_of_food_order);
            tv_name = itemView.findViewById(R.id.tv_name_food);
            tv_description = itemView.findViewById(R.id.tv_description_food);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }

}
