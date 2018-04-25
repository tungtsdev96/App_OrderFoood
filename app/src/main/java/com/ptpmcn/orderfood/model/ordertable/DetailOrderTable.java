package com.ptpmcn.orderfood.model.ordertable;

import java.io.Serializable;

/**
 * Created by tungts on 12/12/2017.
 */

public class DetailOrderTable implements Serializable{

    /**
     * id : 3
     * order_table_id : 3
     * table_id : 2
     * table_number : 2
     */

    private int id;
    private int order_table_id;
    private int table_id;
    private int table_number;

    public DetailOrderTable(int table_id, int table_number) {
        this.table_id = table_id;
        this.table_number = table_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_table_id() {
        return order_table_id;
    }

    public void setOrder_table_id(int order_table_id) {
        this.order_table_id = order_table_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getTable_number() {
        return table_number;
    }

    public void setTable_number(int table_number) {
        this.table_number = table_number;
    }
}
