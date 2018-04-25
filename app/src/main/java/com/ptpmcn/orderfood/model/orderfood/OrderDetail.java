package com.ptpmcn.orderfood.model.orderfood;

import com.ptpmcn.orderfood.model.Food;

import java.io.Serializable;

/**
 * Created by tungts on 11/20/2017.
 */

public class OrderDetail implements Serializable {

    private int product_id;
    private int detail_quantity;
    private String description;
    private Food food;

    public OrderDetail() {
    }

    public OrderDetail(int productId, int quantity) {
        this.product_id = productId;
        this.detail_quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getDetail_quantity() {
        return detail_quantity;
    }

    public void setDetail_quantity(int detail_quantity) {
        this.detail_quantity = detail_quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
