package com.ptpmcn.orderfood.adapter.orderfood;

import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.model.orderfood.OrderDetail;

/**
 * Created by tungts on 11/23/2017.
 */

public interface ClickButtonAddToCart {
    void itemAddProductToCart(Food food, OrderDetail orderDetail);
}
