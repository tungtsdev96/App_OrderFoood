package com.ptpmcn.orderfood.model.google;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungts on 12/2/2017.
 */

public class MyPolyLine {

    /**
     * points : q~ttG|i}_M_AjDs@vCm@vBy@tCADEPw@rB]`ASf@Ud@q@zA{@pCg@|A[jA
     */

    private String points;
    private List<LatLng> pointsLatLon;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<LatLng> getPointsLatLon() {
        return decodePolyLine(points);
    }

    public void setPointsLatLon(List<LatLng> pointsLatLon) {
        this.pointsLatLon = pointsLatLon;
    }

    public List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
