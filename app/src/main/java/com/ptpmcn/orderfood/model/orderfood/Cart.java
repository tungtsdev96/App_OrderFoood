package com.ptpmcn.orderfood.model.orderfood;

import com.ptpmcn.orderfood.model.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 11/23/2017.
 */

public class Cart {

    /**
     * deliveryTime : 1000-01-01 00:00:00
     * phoneNumber : 0125465
     * orderCost : 5000
     * orderAddress : 459 BM - HBT – HN
     * orderDescription : This is the description
     * customerId : 1
     * restaurentId : 2
     * orderDistance : 5
     * orderDetails : [{"productId":180,"quantity":2},{"productId":190,"quantity":3}]
     */

    private Restaurant restaurant;
    private String deliveryTime;
    private String date;
    private String time;
    private String phoneNumber;
    private int orderCost;
    private String orderAddress;
    private String orderDescription;
    private int customerId;
    private int restaurentId;
    private double orderDistance;
    private ArrayList<OrderDetail> orderDetails;

    private static Cart cart = new Cart();

    private Cart(){
        orderDetails = new ArrayList<>();
    }

    public static Cart getInstance(){
        if (cart == null){
            cart = new Cart();
            cart.orderDetails = new ArrayList<>();
        }
        return cart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void resetCart(){
        cart = null;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(int orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurentId() {
        return restaurentId;
    }

    public void setRestaurentId(int restaurentId) {
        this.restaurentId = restaurentId;
    }

    public double getOrderDistance() {
        return orderDistance;
    }

    public void setOrderDistance(double orderDistance) {
        this.orderDistance = orderDistance;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

}
