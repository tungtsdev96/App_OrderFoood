package com.ptpmcn.orderfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.ordertable.OrderTable;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;

import java.util.ArrayList;

/**
 * Created by tungts on 12/12/2017.
 */

public class RcvHistoryOrderTableAdapter extends RecyclerView.Adapter<RcvHistoryOrderTableAdapter.HistoryOrderViewHolder> {

    private Context context;
    private ArrayList<OrderTable> orderTables;
    LayoutInflater layoutInflater;

    public RcvHistoryOrderTableAdapter(Context context, ArrayList<OrderTable> orderTables) {
        this.context = context;
        this.orderTables = orderTables;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HistoryOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryOrderViewHolder(layoutInflater.inflate(R.layout.item_rcv_history_order_table,null));
    }

    @Override
    public void onBindViewHolder(final HistoryOrderViewHolder holder, int position) {
        OrderTable orderTable = orderTables.get(position);
        ApiFactory.getApiRestaurants().getRestaurantByIdRes(orderTable.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(context) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                holder.tv_name_res.setText(result.get(0).getRestaurent_name());
                holder.tv_address_res.setText(result.get(0).getRestaurent_address());
            }
        });
        holder.tv_time_start.setText(orderTable.getStart_time());
        holder.tv_order_time.setText(orderTable.getOrder_time());
        holder.tv_number_of_people.setText(orderTable.getNumber_people()+" người");
        switch (orderTable.getStatus()){
            case 4:
                holder.tv_status.setText("Đã hủy");
                holder.img_status.setImageResource(R.drawable.ic_cancel);
                break;
            case 5:
                holder.tv_status.setText("Đã hủy");
                holder.img_status.setImageResource(R.drawable.ic_cancel);
                break;
            case 1:
                holder.tv_status.setText("Đang xác nhận");
                holder.img_status.setImageResource(R.drawable.ic_delyvery);
                break;
            case 2:
                holder.tv_status.setText("Đã xác nhận");
                holder.img_status.setImageResource(R.drawable.ic_delyvery);
                break;
            case 3:
                holder.tv_status.setText("Đã hoàn thành");
                holder.img_status.setImageResource(R.drawable.ic_choose_location);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderTables.size();
    }

    protected class HistoryOrderViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name_res,tv_address_res;
        ImageView img_more;
        TextView tv_order_time,tv_time_start;
        TextView tv_number_of_people;
        TextView tv_status;
        ImageView img_status;
        RelativeLayout rlt_history;

        public HistoryOrderViewHolder(View itemView) {
            super(itemView);
            tv_name_res = itemView.findViewById(R.id.tv_name_res);
            tv_address_res = itemView.findViewById(R.id.tv_address_res);
            img_more = itemView.findViewById(R.id.img_more);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);
            tv_time_start = itemView.findViewById(R.id.tv_time_start);
            tv_number_of_people = itemView.findViewById(R.id.tv_number_of_people);
            tv_status = itemView.findViewById(R.id.tv_status);
            img_status = itemView.findViewById(R.id.img_status);
            rlt_history  = itemView.findViewById(R.id.rlt_history);
        }
    }


}
