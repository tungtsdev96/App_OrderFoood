package com.ptpmcn.orderfood.model;

/**
 * Created by tungts on 9/27/2017.
 */

public class CategorySystem {


    /**
     * id : 1
     * name : Đồ ăn nhanh
     * description : No
     * image : http://laodong.com.vn/Uploaded/phamthuhien/2014_10_10/cam%20(17)_AGDE.jpg
     */

    private int id;
    private String name;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
