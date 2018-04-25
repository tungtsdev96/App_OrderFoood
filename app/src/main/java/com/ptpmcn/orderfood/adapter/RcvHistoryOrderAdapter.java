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
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.orderfood.OrderProduct;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;

import java.util.ArrayList;

/**
 * Created by tungts on 12/5/2017.
 */

public class RcvHistoryOrderAdapter extends RecyclerView.Adapter<RcvHistoryOrderAdapter.HistoryOrderViewHolder> {

    private Context context;
    private ArrayList<OrderProduct> orderProducts;
    LayoutInflater layoutInflater;
    private RecycleViewItemClick onRecycleViewItemClick;

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick) {
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    public RcvHistoryOrderAdapter(Context context, ArrayList<OrderProduct> orderProducts) {
        this.context = context;
        this.orderProducts = orderProducts;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HistoryOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryOrderViewHolder(layoutInflater.inflate(R.layout.item_rcv_history_order_food,null));
    }

    @Override
    public void onBindViewHolder(final HistoryOrderViewHolder holder, int position) {
        OrderProduct orderProduct = orderProducts.get(position);
        ApiFactory.getApiRestaurants().getRestaurantByIdRes(orderProduct.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(context) {
            @Override
            public void onSuccess(ArrayList<Restaurant> result) {
                holder.tv_name_res.setText(result.get(0).getRestaurent_name());
                holder.tv_address_res.setText(result.get(0).getRestaurent_address());
            }
        });
        holder.tv_delivery_time.setText(orderProduct.getOrder_time());
        holder.tv_order_time.setText(orderProduct.getDelivery_time());
        String total = "<b>"+ orderProduct.getOrder_cost()+ "đ"+"</b>" + " - Tiền mặt";
        holder.tv_total.setText(Html.fromHtml(total));
        switch (orderProduct.getOrder_status()){
            case -1:
                holder.tv_status.setText("Đã hủy");
                holder.img_status.setImageResource(R.drawable.ic_cancel);
                break;
            case 0:
                holder.tv_status.setText("Đang xác nhận");
                holder.img_status.setImageResource(R.drawable.ic_delyvery);
                break;
            case 1:
                holder.tv_status.setText("Đang vận chuyển");
                holder.img_status.setImageResource(R.drawable.ic_delyvery);
                break;
            case 2:
                holder.tv_status.setText("Đã hoàn thành");
                holder.img_status.setImageResource(R.drawable.ic_choose_location);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    protected class HistoryOrderViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name_res,tv_address_res;
        ImageView img_more;
        TextView tv_order_time,tv_delivery_time;
        TextView tv_total;
        TextView tv_status;
        ImageView img_status;
        RelativeLayout rlt_history;

        public HistoryOrderViewHolder(View itemView) {
            super(itemView);
            tv_name_res = itemView.findViewById(R.id.tv_name_res);
            tv_address_res = itemView.findViewById(R.id.tv_address_res);
            img_more = itemView.findViewById(R.id.img_more);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);
            tv_delivery_time = itemView.findViewById(R.id.tv_delivery_time);
            tv_total = itemView.findViewById(R.id.tv_total);
            tv_status = itemView.findViewById(R.id.tv_status);
            img_status = itemView.findViewById(R.id.img_status);
            rlt_history  = itemView.findViewById(R.id.rlt_history);
            rlt_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewItemClick != null){
                        onRecycleViewItemClick.onItemClickRecycleView(getAdapterPosition());
                    }
                }
            });
        }
    }

}
