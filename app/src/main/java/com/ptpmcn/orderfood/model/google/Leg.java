package com.ptpmcn.orderfood.model.google;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 12/2/2017.
 */

public class Leg {

    private Distance distance;
    private Duration duration;
    private String end_address;
    private MyLatLong end_location;
    private String start_address;
    private MyLatLong start_location;
    private List<Step> steps;

    private List<LatLng> allLatLon;

    public List<LatLng> getAllLatLon() {
        List<LatLng> result = new ArrayList<>();

        for (int i=0;i<steps.size();i++){
            Step step= steps.get(i);
            result.addAll(step.getPolyline().getPointsLatLon());
        }

        return result;
    }


    public void setAllLatLon(List<LatLng> allLatLon) {
        this.allLatLon = allLatLon;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public MyLatLong getEnd_location() {
        return end_location;
    }

    public void setEnd_location(MyLatLong end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public MyLatLong getStart_location() {
        return start_location;
    }

    public void setStart_location(MyLatLong start_location) {
        this.start_location = start_location;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
