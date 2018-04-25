package com.ptpmcn.orderfood.model.google;

import java.util.List;

/**
 * Created by tungts on 12/2/2017.
 */

public class RouteGoogle {

    private List<Leg> legs;
    private OverviewPolyline overview_polyline;
    private List<Integer> waypoint_order;

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public List<Integer> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(List<Integer> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }
}
