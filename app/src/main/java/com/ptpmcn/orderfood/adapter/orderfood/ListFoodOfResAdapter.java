package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.utils.GlideHelper;

import java.util.ArrayList;

import static com.ptpmcn.orderfood.utils.constant.Constant.Item.ITEM_FOOD_OF_RESTAURANT;
import static com.ptpmcn.orderfood.utils.constant.Constant.Item.ITEM_LOAD_MORE;

/**
 * Created by tungts on 11/14/2017.
 */

public class ListFoodOfResAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecycleViewItemClick onRecycleViewItemClick;
    private ArrayList arr;
    private RecyclerView rcv;
    private Context context;
    LayoutInflater mLayoutInflater;

    public ListFoodOfResAdapter(Context context,RecyclerView rcv,ArrayList arr){
        this.context = context;
        this.rcv = rcv;
        this.arr = arr;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_FOOD_OF_RESTAURANT){
            return new FoodOfResViewHolder(mLayoutInflater.inflate(R.layout.item_food_of_res,null));
        } else if (viewType == ITEM_LOAD_MORE)  {
            return new LoadMoreViewHolder(mLayoutInflater.inflate(R.layout.item_load_more,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FoodOfResViewHolder){
            Food food = (Food) arr.get(position);
            ((FoodOfResViewHolder) holder).tv_name_food.setText(food.getProduct_name());
            ((FoodOfResViewHolder) holder).tv_new_price_food.setText(food.getProduct_price()+"");
            GlideHelper.loadImageByPath(context,((FoodOfResViewHolder) holder).img_food,food.getProduct_image());
        } else if (holder instanceof LoadMoreViewHolder){
            ((LoadMoreViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (arr.get(position) instanceof Food)? ITEM_FOOD_OF_RESTAURANT: ITEM_LOAD_MORE;
    }

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick) {
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    protected class FoodOfResViewHolder extends RecyclerView.ViewHolder {

        ImageView img_food;
        TextView tv_name_food;
        TextView tv_new_price_food;
        ImageView img_add_food_to_cart;

        public FoodOfResViewHolder(View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_new_price_food = itemView.findViewById(R.id.tv_new_price_food);
            img_add_food_to_cart = itemView.findViewById(R.id.btn_add_food_to_cart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewItemClick != null) {
                        onRecycleViewItemClick.onItemClickRecycleView(getAdapterPosition());
                    }
                }
            });

            img_add_food_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

    }
}
