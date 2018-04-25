package com.ptpmcn.orderfood.adapter.orderfood;

import com.ptpmcn.orderfood.model.orderfood.OrderDetail;

/**
 * Created by tungts on 11/24/2017.
 */

public interface ClickAddOrRemoveFoodOfOrderDetail {

    void remove(int postion, OrderDetail orderDetail);
    void add(int postion, OrderDetail orderDetail);

}
