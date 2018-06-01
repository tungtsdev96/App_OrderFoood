package com.ptpmcn.orderfood.model;

import java.io.Serializable;

/**
 * Created by tungts on 9/27/2017.
 */

public class Customer implements Serializable {


    /**
     * customer_id : 1
     * username : linhpham
     * password : 123
     * customer_first_name : Linh
     * customer_last_name : Pham
     * customer_name : Pháº¡m Háº£i Linh
     * customer_phone : 01667645238
     * customer_email : linhphamdev96@gmail.com
     * token_id : 123456
     * intro_key : IRSVJQVX8B
     * shared : 1
     * coin : 40
     * put_key : 0
     */

    private int customer_id;
    private String username;
    private String password;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_name;
    private String customer_phone;
    private String customer_email;
    private String token_id;
    private String intro_key;
    private String shared;
    private int coin;
    private int put_key;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getIntro_key() {
        return intro_key;
    }

    public void setIntro_key(String intro_key) {
        this.intro_key = intro_key;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getPut_key() {
        return put_key;
    }

    public void setPut_key(int put_key) {
        this.put_key = put_key;
    }
}
