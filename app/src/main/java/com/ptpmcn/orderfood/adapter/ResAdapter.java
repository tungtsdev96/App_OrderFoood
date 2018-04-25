package com.ptpmcn.orderfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.googlemap.CurrentLocation;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.google.PathGoogleResponse;
import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.GlideHelper;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.ArrayList;

/**
 * Created by tungts on 12/7/2017.
 */

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.SearchResViewHoler> {

    private Context context;
    private ArrayList<Restaurant> arr_res;
    private LayoutInflater layoutInflater;
    private int type;
    private RecycleViewItemClick onRecycleViewItemClick;

    public void setOnRecycleViewItemClick(RecycleViewItemClick onRecycleViewItemClick) {
        this.onRecycleViewItemClick = onRecycleViewItemClick;
    }

    public ResAdapter(Context context, ArrayList<Restaurant> arr_res) {
        this.context = context;
        this.arr_res = arr_res;
        layoutInflater = LayoutInflater.from(context);
    }

    public ResAdapter(Context context, ArrayList<Restaurant> arr_res,int type) {
        this.context = context;
        this.arr_res = arr_res;
        this.type = type;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SearchResViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResViewHoler(layoutInflater.inflate(R.layout.item_restaurant,null));
    }

    @Override
    public void onBindViewHolder(SearchResViewHoler holder, int position) {
        final Restaurant restaurant = arr_res.get(position);
        GlideHelper.loadImageByPath(context,holder.img_res,restaurant.getRestaurent_image());
        holder.tv_name_res.setText(restaurant.getRestaurent_name());
        holder.tv_address.setText(restaurant.getRestaurent_address());
        if (type == Constant.Key.KEY_RES){
            holder.ll_distance.setVisibility(View.VISIBLE);
            final TextView tv_near_me = holder.ll_distance.findViewById(R.id.tv_distance_near_me);
            new CurrentLocation(context, new CurrentLocation.CurrentAddress() {
                @Override
                public void currentLocation(String address) {
                    ApiFactory.getApiGoogleMaps().getDirection(address,restaurant.getRestaurent_address(),context.getResources().getString(R.string.google_map_api_key)).enqueue(new BaseCallBack<PathGoogleResponse>(context) {
                        @Override
                        public void onSuccess(PathGoogleResponse result) {
                            try {
                                String distance = result.getRoutes().get(0).getLegs().get(0).getDistance().getText();
                                tv_near_me.setText(distance);
                            } catch (Exception e){

                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arr_res.size();
    }

    protected class SearchResViewHoler extends RecyclerView.ViewHolder{

        ImageView img_res;
        TextView tv_name_res,tv_address;
        LinearLayout ll_distance;

        public SearchResViewHoler(View itemView) {
            super(itemView);
            ll_distance = itemView.findViewById(R.id.ll_distance);
            img_res = itemView.findViewById(R.id.img_res);
            tv_name_res = itemView.findViewById(R.id.tv_name_res);
            tv_address = itemView.findViewById(R.id.tv_address);
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
