package com.ptpmcn.orderfood.adapter.food;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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

public class FoodGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List list;
    private Context context;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private RecycleViewItemClick onRecycleViewItemClick;
    private boolean isGrid;

    public FoodGridAdapter(Context context, List list, RecyclerView rcv){
        this.context = context;
        this.list = list;
        this.recyclerView = rcv;
        layoutInflater = LayoutInflater.from(context);
        isGrid = false;
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
                if (isGrid){
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItem == (totalItemCount -1) && !isLoading){
                        if (onLoadMoreRcvListener != null){
                            onLoadMoreRcvListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    private LoadMoreRcvListener onLoadMoreRcvListener;
    private boolean isLoading;

    public void setLoading() {
        isLoading = false;
    }

    public void setOnLoadMoreRcvListener(LoadMoreRcvListener onLoadMoreRcvListener) {
        this.onLoadMoreRcvListener = onLoadMoreRcvListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.Item.ITEM_FOOD){
            return new FoodViewHolder(layoutInflater.inflate(R.layout.item_food_home,null));
        } else {
            return new LoadMoreViewHolder(layoutInflater.inflate(R.layout.item_load_more,null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FoodViewHolder){
            final Food food = (Food) list.get(position);
            ApiFactory.getApiRestaurants().getRestaurantByIdRes(food.getRestaurent_id()).enqueue(new BaseCallBack<ArrayList<Restaurant>>(context) {
                @Override
                public void onSuccess(ArrayList<Restaurant> result) {
                    if (result.size() == 0){
                        ((FoodViewHolder) holder).tv_name_res.setText("Số 30 Trần Đại Nghĩa Hai Bà Trưng");
                    } else {
                        food.setRestaurant(result.get(0));
                        ((FoodViewHolder) holder).tv_name_res.setText(result.get(0).getRestaurent_address());
                    }
                }
            });
            ((FoodViewHolder) holder).tv_name_food.setText(food.getProduct_name());
            ((FoodViewHolder) holder).tv_new_price_food.setText(String.format(context.getString(R.string.price),food.getProduct_price()));
            ((FoodViewHolder) holder).tv_percent_promotion.setText("5%");
            ((FoodViewHolder) holder).tv_old_price_food.setText(String.format(context.getString(R.string.price),food.getProduct_price()));
            GlideHelper.loadImageByPath(context,((FoodViewHolder) holder).img_food,food.getProduct_image());
        } else {
            LoadMoreViewHolder loadingViewHolder = (LoadMoreViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Food){
            return Constant.Item.ITEM_FOOD;
        } else {
            return Constant.Item.ITEM_LOAD_MORE;
        }
    }

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick){
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{

        TextView tv_percent_promotion,tv_name_food,tv_new_price_food,tv_old_price_food;
        TextView tv_name_res;
        ImageView img_food;

        public FoodViewHolder(View itemView) {
            super(itemView);
            tv_percent_promotion = itemView.findViewById(R.id.tv_percent_promotion);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_new_price_food = itemView.findViewById(R.id.tv_new_price_food);
            tv_old_price_food = itemView.findViewById(R.id.tv_old_price_food);
            img_food = itemView.findViewById(R.id.img_food);
            tv_name_res = itemView.findViewById(R.id.tv_name_res);
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

    class LoadMoreViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

}
