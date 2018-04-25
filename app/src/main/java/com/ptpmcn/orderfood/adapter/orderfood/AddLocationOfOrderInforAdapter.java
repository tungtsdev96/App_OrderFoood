package com.ptpmcn.orderfood.adapter.orderfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.AddressOrder;
import com.ptpmcn.orderfood.model.orderfood.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 12/3/2017.
 */

public class AddLocationOfOrderInforAdapter extends RecyclerView.Adapter<AddLocationOfOrderInforAdapter.LocationViewHolder> {

    Context context;
    List<AddressOrder> arr_address;
    LayoutInflater layoutInflater;
    SparseBooleanArray selectedItems;

    public AddLocationOfOrderInforAdapter(Context context, ArrayList<AddressOrder> arr_address) {
        this.context = context;
        this.arr_address = arr_address;
        this.layoutInflater = LayoutInflater.from(context);
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(layoutInflater.inflate(R.layout.item_rcv_add_location,null));
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        AddressOrder addressOrder = arr_address.get(position);
        holder.tv_id_location.setText(String.format(context.getString(R.string.id_address),position));
        holder.tv_name_address.setText(addressOrder.getAddress());
        holder.img_choose_location.setImageResource(
                selectedItems.get(position)?R.drawable.ic_choose_location:0
        );
    }

    @Override
    public int getItemCount() {
        return arr_address.size();
    }

    protected class LocationViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id_location,tv_name_address;
        ImageView img_choose_location;

        public LocationViewHolder(View itemView) {
            super(itemView);
            tv_id_location = itemView.findViewById(R.id.tv_id_location);
            tv_name_address = itemView.findViewById(R.id.tv_name_address);
            img_choose_location = itemView.findViewById(R.id.img_choose_location);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelected(getAdapterPosition());
                    Cart.getInstance().setOrderAddress(arr_address.get(getAdapterPosition()).getAddress());
                }
            });
        }
    }

    int last_pos = -1;
    private void setSelected(int position) {
        if (selectedItems.get(position)){
            return;
        }
        selectedItems.put(position,true);
        if (last_pos != -1){
            selectedItems.delete(last_pos);
            notifyItemChanged(last_pos);
        }
        last_pos = position;
        notifyItemChanged(position);
    }
}
