package com.ptpmcn.orderfood.model.restaurant;

import java.io.Serializable;

/**
 * Created by tungts on 10/28/2017.
 */

public class ResCategory implements Serializable {


    /**
     * category_id : 1
     * category_name : Milk Tea
     * category_description : 1
     * restaurent_id : 1
     * system_category_id : 1
     */

    private int category_id;
    private String category_name;
    private String category_description;
    private int system_category_id;

    public ResCategory() {
    }

    public ResCategory(int category_id, String category_name, String category_description) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_description = category_description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public int getSystem_category_id() {
        return system_category_id;
    }

    public void setSystem_category_id(int system_category_id) {
        this.system_category_id = system_category_id;
    }

}
