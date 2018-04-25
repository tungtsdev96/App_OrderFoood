package com.ptpmcn.orderfood.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tungts on 9/30/2017.
 */

public abstract class RecyclerArrayAdapter<M, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    private List<M> items = new ArrayList<>();

    public RecyclerArrayAdapter() {
        setHasStableIds(true);
    }

    public void add(M object) {
        items.add(object);
        notifyItemInserted(items.size()-1);
//        notifyDataSetChanged();
    }

    public void add(int index, M object) {
        items.add(index, object);
        notifyItemInserted(index);
//        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends M> collection) {
        if (collection != null) {
            int curSize = items.size();
            items.addAll(collection);
            notifyItemRangeInserted(curSize, collection.size());
//            notifyDataSetChanged();
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        items.remove(position);
//        if (position == 0)
//        notifyDataSetChanged();
//        else
        notifyItemRemoved(position);
    }

    public M getItem(int position) {
        try {
            return items.get(position);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<M> getItems() {
        return items;
    }

    public void setItems(List<M> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setItem(int index, M item){
        this.items.set(index,item);
        notifyItemChanged(index);
//        notifyDataSetChanged();
    }

    public void setItem_noupdate(int index, M item){
        this.items.set(index, item);
    }
}
