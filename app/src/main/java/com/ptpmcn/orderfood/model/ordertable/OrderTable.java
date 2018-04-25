package com.ptpmcn.orderfood.model.ordertable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tungts on 12/12/2017.
 */

public class OrderTable implements Serializable{


    /**
     * id : 12
     * customer_id : 1
     * restaurent_id : 2
     * order_time : 2017-12-12 02:21:54
     * start_time : 2017-12-15 17:00:00
     * end_time : 2017-12-15 18:00:00
     * number_people : 12
     * code : 389u535u
     * status : 1
     */

    private int id;
    private int customer_id;
    private int restaurent_id;
    private String order_time;
    private String start_time;
    private String end_time;
    private int number_people;
    private String code;
    private int status;
    private ArrayList<DetailOrderTable> details = new ArrayList<>();

    public ArrayList<DetailOrderTable> getOrderTables() {
        return details;
    }

    public void setOrderTables(ArrayList<DetailOrderTable> details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getNumber_people() {
        return number_people;
    }

    public void setNumber_people(int number_people) {
        this.number_people = number_people;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
