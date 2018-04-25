package com.ptpmcn.orderfood.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;

/**
 * Created by tungts on 9/30/2017.
 */

public class FilterFoodAdapter extends RecyclerArrayAdapter<String , RecyclerView.ViewHolder> {

    RecycleViewItemClick onRecycleViewItemClick;
    int type;
    public static final int TYPE_FILTER_TYPE = 0;
    public static final int TYPE_FILTER_DETAIL_OF_TYPE = 1;

    public FilterFoodAdapter(int type){
        super();
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == TYPE_FILTER_TYPE){
            return new FilterFoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_food,null));
        } else if (type == TYPE_FILTER_DETAIL_OF_TYPE){
            return new FilterFoodDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_food_detail,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FilterFoodViewHolder){
            ((FilterFoodViewHolder) holder).tv_name_filer_food.setText(getItem(position));
        } else if (holder instanceof FilterFoodDetailViewHolder){
            ((FilterFoodDetailViewHolder) holder).tv_filter_detail.setText(getItem(position));
        }
    }

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick) {
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    protected class FilterFoodViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name_filer_food;

        public FilterFoodViewHolder(View itemView) {
            super(itemView);
            tv_name_filer_food = itemView.findViewById(R.id.tv_name_filer_food);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewItemClick != null){
                        onRecycleViewItemClick.onItemClickRecycleView(getAdapterPosition());
                    }
                }
            });
        }
    }

    protected class FilterFoodDetailViewHolder extends RecyclerView.ViewHolder{

        TextView tv_filter_detail;
        RadioButton rd_filter;

        public FilterFoodDetailViewHolder(View itemView) {
            super(itemView);
            tv_filter_detail = itemView.findViewById(R.id.tv_filter_detail);
            rd_filter = itemView.findViewById(R.id.rd_filter);
            itemView.setOnClickListener(new View.OnClickListener() {
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
