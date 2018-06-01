package com.ptpmcn.orderfood.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.ordertable.Table;

import java.util.ArrayList;

/**
 * Created by tungts on 12/12/2017.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{


    private ClickChooseTable onClickChooseTable;
    private Context context;
    private ArrayList<Table> tables;
    private LayoutInflater layoutInflater;
    private boolean isSearch;

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public TableAdapter(Context context, ArrayList<Table> tables) {
        this.context = context;
        this.tables = tables;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnClickChooseTable(ClickChooseTable onClickChooseTable) {
        this.onClickChooseTable = onClickChooseTable;
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableViewHolder(layoutInflater.inflate(R.layout.item_table,null));
    }

    @Override
    public void onBindViewHolder(final TableViewHolder holder, final int position) {
        final Table table = tables.get(position);
        holder.btn_table.setText(table.getTable_number()+"");
        if (table.isBlank()){
            if (table.isChoose()){
                holder.btn_table.setBackgroundResource(R.drawable.bg_orange);
            } else {
                holder.btn_table.setBackgroundResource(R.drawable.bg_green);
            }
        } else {
            if (!isSearch){
                holder.btn_table.setText(table.getTable_number()+"");
            } else {
                holder.btn_table.setText("x");
            }
            holder.btn_table.setBackgroundResource(R.drawable.bg_red);
        }
        holder.btn_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickChooseTable != null){
                    if (table.isBlank()){
                        if (table.isChoose()){
                            onClickChooseTable.choose(position,true);
//                            holder.btn_table.setBackgroundResource(R.drawable.bg_orange);
                        } else {
                            onClickChooseTable.choose(position,false);
//                            holder.btn_table.setBackgroundResource(R.drawable.bg_green);
                        }

                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    protected class TableViewHolder extends RecyclerView.ViewHolder{

        Button btn_table;

        public TableViewHolder(View itemView) {
            super(itemView);
            btn_table = itemView.findViewById(R.id.btn_table);
        }
    }

}
