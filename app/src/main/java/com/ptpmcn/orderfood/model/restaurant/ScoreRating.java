package com.ptpmcn.orderfood.model.restaurant;

/**
 * Created by tungts on 12/9/2017.
 */

public class ScoreRating {

    private int customerId;
    private int restaurentId;
    private double score;
    private double sc;

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getSc() {
        return sc;
    }

    public void setSc(double sc) {
        this.sc = sc;
    }
}
