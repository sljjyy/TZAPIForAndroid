package com.tianzunchina.android.api.utils.model;

/**
 * 经纬度信息实体类
 * CraetTime 2016-3-4
 * @author SunLiang
 */
public class LatLon {
    private double lat,lon;
    public LatLon(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "LatLon{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
