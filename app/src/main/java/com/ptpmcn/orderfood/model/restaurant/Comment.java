package com.ptpmcn.orderfood.model.restaurant;

import java.util.ArrayList;

/**
 * Created by tungts on 12/7/2017.
 */

public class Comment {

    private String customer_name;
    private String content;
    /**
     * customer_id : 1
     */

    private int customer_id;

    public Comment(String customer_name, String content) {
        this.customer_name = customer_name;
        this.content = content;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public static ArrayList<Comment> fake(){
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Sơn Tùng","dm mmmmmsadasdasdsadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddm"));
        comments.add(new Comment("Sơn Tùng","dm mmmmmsadasdasdsadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddm"));
        comments.add(new Comment("Sơn Tùng","dm mmmmmsadasdasdsadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddm"));
        comments.add(new Comment("Sơn Tùng","dm mmmmmsadasdasdsadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddm"));
        comments.add(new Comment("Sơn Tùng","dm mmmmmsadasdasdsadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddm"));
        comments.add(new Comment("Sơn Tùng","dm mmmmmmmmsadasdasdsadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddmmmm"));
        return comments;
    }
}
