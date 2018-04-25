package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.restaurant.DetailRestaurantActivity;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.model.restaurant.ResCategory;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.List;

/**
 * Created by tungts on 10/1/2017.
 */

public class OrderFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_DETAIL_FOOD = 98;
    public static final int ITEM_DETAIL_RESTAURANT = 99;
    public static final int ITEM_CATEGORY_OF_RESTAURANT = 100;

    private Context context;
    private List list;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private RecycleViewItemClick onRecycleViewItemClick;
    private ClickButtonAddToCart onClickButtonAddToCart;

    public OrderFoodAdapter(Context context, List list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        layoutInflater = LayoutInflater.from(context);
    }

    public void notifyData() {
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_DETAIL_FOOD:
                return new DetailFoodViewHolder(layoutInflater.inflate(R.layout.item_order_food_detail_food, null));
            case ITEM_DETAIL_RESTAURANT:
                return new DetailResViewHolder(layoutInflater.inflate(R.layout.item_order_food_detail_res, null));
            case ITEM_CATEGORY_OF_RESTAURANT:
                return new CatOfResViewHolder(layoutInflater.inflate(R.layout.item_text, null));
            case Constant.Item.ITEM_FOOD_OF_RESTAURANT:
                return new FoodOfResViewHolder(layoutInflater.inflate(R.layout.item_food_of_res, null));
            case Constant.Item.ITEM_LOAD_MORE:
                return new LoadMoreViewHolder(layoutInflater.inflate(R.layout.item_button_load_more, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DetailFoodViewHolder) {
            Food food = (Food) list.get(position);
            ((DetailFoodViewHolder) holder).tv_name_food.setText(food.getProduct_name());
            ((DetailFoodViewHolder) holder).tv_new_price_food.setText(String.format(context.getString(R.string.price), food.getProduct_price()));
            ((DetailFoodViewHolder) holder).tv_description_food.setText(food.getProduct_description());
        } else if (holder instanceof DetailResViewHolder) {
            final Restaurant restaurant = (Restaurant) list.get(position);
            ((DetailResViewHolder) holder).tv_name_res.setText("Tên cửa hàng: " + restaurant.getRestaurent_name());
            ((DetailResViewHolder) holder).tv_address_res.setText("Địa chỉ: " + restaurant.getRestaurent_address());
            ((DetailResViewHolder) holder).tv_detail_res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailRestaurantActivity.class);
                    i.setAction("Order");
                    i.putExtra("RestaurantID",restaurant.getRestaurent_id());
                    context.startActivity(i);
                }
            });
        } else if (holder instanceof FoodOfResViewHolder) {
            Food food = (Food) list.get(position);
            ((FoodOfResViewHolder) holder).tv_name_food.setText(food.getProduct_name());
            ((FoodOfResViewHolder) holder).tv_new_price_food.setText(String.format(context.getString(R.string.price), food.getProduct_price()));
            GlideHelper.loadImageByPath(context, ((FoodOfResViewHolder) holder).img_food, food.getProduct_image());
        } else if (holder instanceof CatOfResViewHolder) {
            ResCategory resCategory = (ResCategory) list.get(position);
            ((CatOfResViewHolder) holder).tv_name_cat.setText(resCategory.getCategory_name());
        } else if (holder instanceof  LoadMoreViewHolder){
            ((LoadMoreViewHolder) holder).tv_see_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewItemClick!= null){
                        onRecycleViewItemClick.onItemClickRecycleView(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_DETAIL_FOOD;
        } else if (position == 1) {
            return ITEM_DETAIL_RESTAURANT;
        } else if (list.get(position) instanceof ResCategory) {
            return ITEM_CATEGORY_OF_RESTAURANT;
        } else if (list.get(position) instanceof Food) {
            return Constant.Item.ITEM_FOOD_OF_RESTAURANT;
        } else if (list.get(position) instanceof Integer) {
            return Constant.Item.ITEM_LOAD_MORE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick) {
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    public void setOnClickButtonAddToCart(ClickButtonAddToCart onClickButtonAddToCart) {
        this.onClickButtonAddToCart = onClickButtonAddToCart;
    }

    protected class DetailFoodViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name_food, tv_new_price_food;
        private ImageView img_decrease, img_increase;
        private TextView tv_number_of_food_order;
        private TextView tv_count_like_food;
        private TextView tv_description_food;
        private TextView img_like;
        private TextView btn_add_to_cart;

        public DetailFoodViewHolder(View itemView) {
            super(itemView);
            btn_add_to_cart = itemView.findViewById(R.id.btn_add_to_cart);
            tv_name_food = (TextView) itemView.findViewById(R.id.tv_name_food);
            tv_new_price_food = (TextView) itemView.findViewById(R.id.tv_new_price_food);
            tv_count_like_food = (TextView) itemView.findViewById(R.id.tv_count_like_food);
            tv_description_food = (TextView) itemView.findViewById(R.id.tv_description_food);
            img_decrease = (ImageView) itemView.findViewById(R.id.img_decrease);
            img_increase = (ImageView) itemView.findViewById(R.id.img_increase);
            tv_number_of_food_order = (TextView) itemView.findViewById(R.id.tv_number_of_food_order);
            img_like = (TextView) itemView.findViewById(R.id.img_like);
            btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickButtonAddToCart != null){
                        Food food = (Food) list.get(0);
                        int quantity = Integer.parseInt(tv_number_of_food_order.getText().toString());
                        onClickButtonAddToCart.itemAddProductToCart(food,new OrderDetail(food.getProduct_id(),quantity));
                    }
                }
            });

            img_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number_of_food_order = Integer.parseInt(tv_number_of_food_order.getText().toString());
                    number_of_food_order++;
                    tv_number_of_food_order.setText(number_of_food_order+"");
                }
            });

            img_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number_of_food_order = Integer.parseInt(tv_number_of_food_order.getText().toString());
                    if (number_of_food_order == 1){
                        return;
                    }
                    number_of_food_order--;
                    tv_number_of_food_order.setText(number_of_food_order+"");
                }
            });
        }
    }

    protected class DetailResViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name_res, tv_address_res, tv_detail_res;

        public DetailResViewHolder(View itemView) {
            super(itemView);
            tv_name_res = (TextView) itemView.findViewById(R.id.tv_name_res);
            tv_address_res = (TextView) itemView.findViewById(R.id.tv_address_res);
            tv_detail_res = (TextView) itemView.findViewById(R.id.tv_detail_res);

        }
    }

    protected class CatOfResViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name_cat;

        public CatOfResViewHolder(View itemView) {
            super(itemView);
            tv_name_cat = itemView.findViewById(R.id.tv_text);
        }
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

            img_add_food_to_cart.setVisibility(View.GONE);
//            img_add_food_to_cart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onClickButtonAddProductToCartOfList != null) {
//                        onClickButtonAddProductToCartOfList.itemDetailOrderProduct(getAdapterPosition());
//                    }
//                }
//            });

        }

    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        TextView tv_see_all;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            tv_see_all = itemView.findViewById(R.id.tv_see_all);
        }
    }

}
