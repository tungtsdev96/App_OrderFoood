package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.Customer;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tungts on 11/23/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_ACCOUNT = 888;
    private Context context;
    private List items;
    private LayoutInflater layoutInflater;
    private RecyclerView rcv;

    private ClickAddOrRemoveFoodOfOrderDetail onClickAddOrRemoveFoodOfOrderDetail;

    public CartAdapter(Context context,List items){
        this.context = context;
        this.items = items;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_ACCOUNT){
           return new ItemCustomer(layoutInflater.inflate(R.layout.item_infor_customer_in_cart,null));
        } else if (viewType == Constant.Item.ITEM_FOOD_OF_CART){
            return new ItemFoodOfCartViewHolder(layoutInflater.inflate(R.layout.item_rcv_cart,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemCustomer){
            Customer customer = (Customer) items.get(position);
            ((ItemCustomer) holder).tv_name_customer.setText(customer.getCustomer_name());
            ((ItemCustomer) holder).tv_number_of_food_order.setText("");
            GlideHelper.loadImageByDrawable(context,((ItemCustomer) holder).img_profile,R.drawable.ic_demo);
        } else if (holder instanceof ItemFoodOfCartViewHolder){
            OrderDetail orderDetail = (OrderDetail) items.get(position);
            Food food = (Food) orderDetail.getFood();
            GlideHelper.loadImageByPath(context,((ItemFoodOfCartViewHolder) holder).img_food,food.getProduct_image());
            ((ItemFoodOfCartViewHolder) holder).tv_name_food.setText(food.getProduct_name());
            ((ItemFoodOfCartViewHolder) holder).tv_number_of_food_order.setText(orderDetail.getDetail_quantity()+"");
            ((ItemFoodOfCartViewHolder) holder).tv_price.setText(String.format(context.getString(R.string.price),food.getProduct_price()));
            if (orderDetail.getDescription() != null){
                ((ItemFoodOfCartViewHolder) holder).tv_note.setText(orderDetail.getDescription());
                ((ItemFoodOfCartViewHolder) holder).tv_note.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof OrderDetail){
            return Constant.Item.ITEM_FOOD_OF_CART;
        } else if (items.get(position) instanceof Customer){
            return ITEM_ACCOUNT;
        }
        return -1;
    }

    public void setOnClickAddOrRemoveFoodOfOrderDetail(ClickAddOrRemoveFoodOfOrderDetail onClickAddOrRemoveFoodOfOrderDetail) {
        this.onClickAddOrRemoveFoodOfOrderDetail = onClickAddOrRemoveFoodOfOrderDetail;
    }

    protected class ItemFoodOfCartViewHolder extends RecyclerView.ViewHolder{

        ImageView img_food;
        TextView tv_name_food,tv_price,tv_number_of_food_order,tv_note;
        ImageView btn_remove_food,btn_add_food;

        public ItemFoodOfCartViewHolder(View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_price = itemView.findViewById(R.id.tv_new_price_food);
            btn_remove_food = itemView.findViewById(R.id.btn_remove_food);
            btn_add_food = itemView.findViewById(R.id.btn_add_food_to_cart);
            tv_number_of_food_order = itemView.findViewById(R.id.tv_number_of_food_order);
            tv_note = itemView.findViewById(R.id.tv_note);

            tv_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            btn_add_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number_of_food_order = Integer.parseInt(tv_number_of_food_order.getText().toString());
                    number_of_food_order++;
//                    tv_number_of_food_order.setText(number_of_food_order+"");
                    if (onClickAddOrRemoveFoodOfOrderDetail != null){
                        OrderDetail orderDetail = (OrderDetail) items.get(getAdapterPosition());
                        orderDetail.setDetail_quantity(number_of_food_order);
                        onClickAddOrRemoveFoodOfOrderDetail.add(getAdapterPosition(),orderDetail);
                    }
                }
            });

            btn_remove_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number_of_food_order = Integer.parseInt(tv_number_of_food_order.getText().toString());
//                    if (number_of_food_order == 1){
//                        return;
//                    }
                    number_of_food_order--;
//                    tv_number_of_food_order.setText(number_of_food_order+"");
                    if (onClickAddOrRemoveFoodOfOrderDetail != null){
                        OrderDetail orderDetail = (OrderDetail) items.get(getAdapterPosition());
                        orderDetail.setDetail_quantity(number_of_food_order);
                        onClickAddOrRemoveFoodOfOrderDetail.remove(getAdapterPosition(),orderDetail);
                    }
                }
            });
        }
    }

    protected class ItemCustomer extends RecyclerView.ViewHolder{

        CircleImageView img_profile;
        TextView tv_name_customer,tv_number_of_food_order;

        public ItemCustomer(View itemView) {
            super(itemView);
            img_profile = itemView.findViewById(R.id.img_profile);
            tv_name_customer = itemView.findViewById(R.id.tv_name_customer);
            tv_number_of_food_order = itemView.findViewById(R.id.tv_number_of_food_order);
        }
    }

}
