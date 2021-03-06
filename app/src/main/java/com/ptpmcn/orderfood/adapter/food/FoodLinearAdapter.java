package com.ptpmcn.orderfood.adapter.food;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.LoadMoreRcvListener;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 9/29/2017.
 */

public class FoodLinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List list;
    private Context context;
    private LayoutInflater layoutInflater;

    private boolean isLoading;
    private LoadMoreRcvListener onLoadMoreRcvListener;
    private RecyclerView recyclerView;
    private RecycleViewItemClick onRecycleViewItemClick;
    private ClickDeteteLike onClickDeteteLike;
    int type;

    public FoodLinearAdapter(Context context, List list, RecyclerView rcv){
        this.context = context;
        this.list = list;
        this.recyclerView = rcv;
        layoutInflater = LayoutInflater.from(context);
        innitLoadMore();
    }

    public FoodLinearAdapter(Context context, List list, RecyclerView rcv, int type){
        this.context = context;
        this.list = list;
        this.recyclerView = rcv;
        this.type = type;
        layoutInflater = LayoutInflater.from(context);
        innitLoadMore();
    }

    private void innitLoadMore() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem == (totalItemCount -1) && !isLoading){
                    if (onLoadMoreRcvListener != null){
                        onLoadMoreRcvListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setLoading() {
        isLoading = false;
    }

    public void setOnLoadMoreRcvListener(LoadMoreRcvListener onLoadMoreRcvListener) {
        this.onLoadMoreRcvListener = onLoadMoreRcvListener;
    }

    public RecycleViewItemClick getOnRecycleViewItemClick() {
        return onRecycleViewItemClick;
    }

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick) {
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    public ClickDeteteLike getOnClickDeteteLike() {
        return onClickDeteteLike;
    }

    public void setOnClickDeteteLike(ClickDeteteLike onClickDeteteLike) {
        this.onClickDeteteLike = onClickDeteteLike;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.Item.ITEM_FOOD){
            return new FoodLinearAdapter.FoodLinearViewHolder(layoutInflater.inflate(R.layout.item_food_delivery_linear,null));
        } else {
            return new LoadMoreViewHolder(layoutInflater.inflate(R.layout.item_load_more,null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FoodLinearViewHolder){
            final Food food = (Food) list.get(position);
            ApiFactory.getApiRestaurants().getRestaurantByIdRes(food.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(context) {
                @Override
                public void onSuccess(ArrayList<Restaurant> result) {
                    if (result.size() == 0){
                        ((FoodLinearViewHolder) holder).tv_address.setText("Số 30 Trần Đại Nghĩa Hai Bà Trưng");
                        ((FoodLinearViewHolder) holder).tv_name_food.setText(food.getProduct_name());
                    } else {
                        food.setRestaurant(result.get(0));
                        ((FoodLinearViewHolder) holder).tv_address.setText(result.get(0).getRestaurent_address());
                        ((FoodLinearViewHolder) holder).tv_name_food.setText(food.getProduct_name() + " - " + result.get(0).getRestaurent_name());

                    }
                }
            });
            ((FoodLinearViewHolder) holder).tv_name_food.setText(food.getProduct_name());
            ((FoodLinearViewHolder) holder).tv_percent_promotion.setText("5%");
            ((FoodLinearViewHolder) holder).tv_new_price_food.setText(String.format(context.getString(R.string.price),food.getProduct_price()));
            ((FoodLinearViewHolder) holder).tv_old_price_food.setText(String.format(context.getString(R.string.price),food.getProduct_price()));
            GlideHelper.loadImageByPath(context,((FoodLinearViewHolder) holder).img_food,food.getProduct_image());
            if (type == Constant.Key.KEY_LIKE){
                ((FoodLinearViewHolder) holder).img_delete.setVisibility(View.VISIBLE);
                ((FoodLinearViewHolder) holder).img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onClickDeteteLike != null){
                            onClickDeteteLike.deleteLike(position);
                        }
                    }
                });
            }
        } else {
            LoadMoreViewHolder loadingViewHolder = (LoadMoreViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Food){
            return Constant.Item.ITEM_FOOD;
        } else {
            return Constant.Item.ITEM_LOAD_MORE;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class FoodLinearViewHolder extends RecyclerView.ViewHolder{

        TextView tv_percent_promotion,tv_name_food,tv_new_price_food,tv_old_price_food,tv_address;
        ImageView img_food;
        ImageView img_delete;

        public FoodLinearViewHolder(View itemView) {
            super(itemView);
            tv_percent_promotion = itemView.findViewById(R.id.tv_percent_promotion);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_new_price_food = itemView.findViewById(R.id.tv_new_price_food);
            tv_old_price_food = itemView.findViewById(R.id.tv_old_price_food);
            img_food = itemView.findViewById(R.id.img_food);
            tv_address = itemView.findViewById(R.id.tv_address);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnRecycleViewItemClick() != null){
                        onRecycleViewItemClick.onItemClickRecycleView(getAdapterPosition());
                    }
                }
            });

            img_delete = itemView.findViewById(R.id.img_delete);

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
