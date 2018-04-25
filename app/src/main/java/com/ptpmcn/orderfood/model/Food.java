package com.ptpmcn.orderfood.model;

import com.ptpmcn.orderfood.model.restaurant.Restaurant;
import com.ptpmcn.orderfood.utils.constant.ConfigApi;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tungts on 9/27/2017.
 */

public class Food implements Serializable {

    /**
     * product_id : 188
     * product_name : Diet Pepsi
     * product_description : 8
     * product_image : http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg
     * product_price : 12000
     * category_id : 2
     * restaurent_id : 1
     */

    private int product_id;
    private String product_name;
    private String product_description;
    private String product_image;
    private int product_price;
    private int category_id;
    private int restaurent_id;
    private Restaurant restaurant;

    public Food(){}

    public Food(int product_id, String product_name, String product_description, String product_image, int product_price, int category_id, int restaurent_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_image = product_image;
        this.product_price = product_price;
        this.category_id = category_id;
        this.restaurent_id = restaurent_id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_image() {
        return this.product_image.replaceFirst("http://localhost:3000", ConfigApi.URL_NODEJS);
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getRestaurent_id() {
        return restaurent_id;
    }

    public void setRestaurent_id(int restaurent_id) {
        this.restaurent_id = restaurent_id;
    }

    public static ArrayList<Food> fakeData(){
        ArrayList<Food> arr = new ArrayList();
        arr.add(new Food(180,"Diet Pepsi 1","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,1,1));
        arr.add(new Food(181,"Diet Pepsi 2","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,2,2));
        arr.add(new Food(182,"Diet Pepsi 3","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,3,3));
        arr.add(new Food(183,"Diet Pepsi 4","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,1,1));
        arr.add(new Food(184,"Diet Pepsi 5","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,2,2));
        arr.add(new Food(185,"Diet Pepsi 6","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,3,3));
        arr.add(new Food(186,"Diet Pepsi 7","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,1,1));
        arr.add(new Food(187,"Diet Pepsi 8","Description","http://vuakhuyenmai.vn/wp-content/uploads/2016/04/15-3.jpg",12000,2,2));
        return arr;
    }
}
