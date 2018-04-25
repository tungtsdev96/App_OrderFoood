package com.ptpmcn.orderfood.model.google;

/**
 * Created by tungts on 12/2/2017.
 */

public class Step {

    private Distance distance;
    private Duration duration;
    private MyLatLong end_location;
    private String html_instructions;
    private MyPolyLine polyline;
    private MyLatLong start_location;
    private String travel_mode;

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

    public MyLatLong getEnd_location() {
        return end_location;
    }

    public void setEnd_location(MyLatLong end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public MyPolyLine getPolyline() {
        return polyline;
    }

    public void setPolyline(MyPolyLine polyline) {
        this.polyline = polyline;
    }

    public MyLatLong getStart_location() {
        return start_location;
    }

    public void setStart_location(MyLatLong start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }
}
