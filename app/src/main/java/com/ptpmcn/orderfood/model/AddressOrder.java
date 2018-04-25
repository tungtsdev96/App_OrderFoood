package com.ptpmcn.orderfood.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tungts on 12/3/2017.
 */

public class AddressOrder implements Serializable {


    /**
     * address_id : 1
     * customer_id : 1
     * address : Phố Vọng
     */

    private int address_id;
    private int customer_id;
    private String address;

    public AddressOrder() {}

    public AddressOrder(int address_id, int customer_id, String address) {
        this.address_id = address_id;
        this.customer_id = customer_id;
        this.address = address;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static ArrayList<AddressOrder> fakeAddress() {
        ArrayList arr = new ArrayList();
        arr.add(new AddressOrder(1, 1, "Số 30 Trần Đại Nghĩa, Hà Nội"));
        arr.add(new AddressOrder(2, 1, "Số 30 Lê Thanh Nghị, Hà Nội"));
        arr.add(new AddressOrder(3, 1, "Số 30 Tạ Quang Bửu, Hà Nội"));
        return arr;
    }

}
