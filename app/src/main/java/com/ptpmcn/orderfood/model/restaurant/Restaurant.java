package com.ptpmcn.orderfood.model.restaurant;

import java.io.Serializable;

/**
 * Created by tungts on 9/27/2017.
 */

public class Restaurant implements Serializable{

    /**
     * restaurent_id : 1
     * restaurent_name : Royal Tea
     * restaurent_address : Số 10 Tạ Quang Bửu - Hai Bà Trưng - Hà Nội
     * restaurent_type : 1
     * restaurent_image : http://www.ozarlington.com/wp-content/uploads/2017/04/bar-buffet.jpg
     * restaurent_introduction : Intro1qweqweqwe
     * restaurent_number_table : 30
     * restaurent_latitude : 100
     * restaurent_longitude : 100
     */

    private int restaurent_id;
    private String restaurent_name;
    private String restaurent_address;
    private String restaurent_type;
    private String restaurent_image;
    private String restaurent_introduction;
    private int restaurent_number_table;
    private double restaurent_latitude;
    private double restaurent_longitude;

    public int getRestaurent_id() {
        return restaurent_id;
    }

    public void setRestaurent_id(int restaurent_id) {
        this.restaurent_id = restaurent_id;
    }

    public String getRestaurent_name() {
        return restaurent_name;
    }

    public void setRestaurent_name(String restaurent_name) {
        this.restaurent_name = restaurent_name;
    }

    public String getRestaurent_address() {
        return restaurent_address;
    }

    public void setRestaurent_address(String restaurent_address) {
        this.restaurent_address = restaurent_address;
    }

    public String getRestaurent_type() {
        return restaurent_type;
    }

    public void setRestaurent_type(String restaurent_type) {
        this.restaurent_type = restaurent_type;
    }

    public String getRestaurent_image() {
        return restaurent_image;
    }

    public void setRestaurent_image(String restaurent_image) {
        this.restaurent_image = restaurent_image;
    }

    public String getRestaurent_introduction() {
        return restaurent_introduction;
    }

    public void setRestaurent_introduction(String restaurent_introduction) {
        this.restaurent_introduction = restaurent_introduction;
    }

    public int getRestaurent_number_table() {
        return restaurent_number_table;
    }

    public void setRestaurent_number_table(int restaurent_number_table) {
        this.restaurent_number_table = restaurent_number_table;
    }

    public double getRestaurent_latitude() {
        return restaurent_latitude;
    }

    public void setRestaurent_latitude(double restaurent_latitude) {
        this.restaurent_latitude = restaurent_latitude;
    }

    public double getRestaurent_longitude() {
        return restaurent_longitude;
    }

    public void setRestaurent_longitude(double restaurent_longitude) {
        this.restaurent_longitude = restaurent_longitude;
    }

}
