package com.ptpmcn.orderfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.CategorySystem;
import com.ptpmcn.orderfood.utils.GlideHelper;

import java.util.List;

/**
 * Created by tungts on 9/29/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryFoodHolder> {

    private List list;
    private Context context;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private RecycleViewItemClick onRecycleViewItemClick;

    public CategoryAdapter(Context context,List list, RecyclerView rcv){
        this.context = context;
        this.list = list;
        this.recyclerView = rcv;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryFoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryFoodHolder(layoutInflater.inflate(R.layout.item_category,null));
    }

    @Override
    public void onBindViewHolder(CategoryFoodHolder holder, int position) {
        CategorySystem categorySystem = (CategorySystem) list.get(position);
        holder.tv_name_cat.setText(categorySystem.getName());
        switch (position % 3){
            case 0:
                GlideHelper.loadImageByDrawable(context,holder.img_cat,R.drawable.ic_cat1);
                break;
            case 1:
                GlideHelper.loadImageByDrawable(context,holder.img_cat,R.drawable.ic_cat2);
                break;
            case 2:
                GlideHelper.loadImageByDrawable(context,holder.img_cat,R.drawable.ic_cat3);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick){
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    class CategoryFoodHolder extends RecyclerView.ViewHolder{
        TextView tv_name_cat;
        ImageView img_cat;

        public CategoryFoodHolder(View itemView) {
            super(itemView);
            tv_name_cat = itemView.findViewById(R.id.tv_name_category);
            img_cat = itemView.findViewById(R.id.img_category_food);
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
