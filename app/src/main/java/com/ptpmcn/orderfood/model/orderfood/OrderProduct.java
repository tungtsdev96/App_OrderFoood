package com.ptpmcn.orderfood.model.orderfood;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tungts on 11/20/2017.
 */

public class OrderProduct implements Serializable {


    /**
     * order_id : 14
     * order_time : 2017-10-20T19:48:22.000Z
     * delivery_time : 0999-12-31T17:00:00.000Z
     * phone_number : 0125465
     * order_cost : 5000
     * order_address : 459 BM - HBT - HN
     * order_description : This is the description
     * order_feedback : null
     * order_status : 0
     * customer_id : 1
     * restaurent_id : 2
     * order_distance : 4.5
     * order_feedback : null
     */

    private int order_id;
    private String order_time;
    private String delivery_time;
    private String phone_number;
    private int order_cost;
    private String order_address;
    private String order_description;
    private String order_feedback;
    private int order_status;
    private int customer_id;
    private int restaurent_id;
    private double order_distance;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getOrder_cost() {
        return order_cost;
    }

    public void setOrder_cost(int order_cost) {
        this.order_cost = order_cost;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getOrder_description() {
        return order_description;
    }

    public void setOrder_description(String order_description) {
        this.order_description = order_description;
    }

    public String getOrder_feedback() {
        return order_feedback;
    }

    public void setOrder_feedback(String order_feedback) {
        this.order_feedback = order_feedback;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getRestaurent_id() {
        return restaurent_id;
    }

    public void setRestaurent_id(int restaurent_id) {
        this.restaurent_id = restaurent_id;
    }


    public double getOrder_distance() {
        return order_distance;
    }

    public void setOrder_distance(double order_distance) {
        this.order_distance = order_distance;
    }

}
