package com.tianzunchina.android.api.utils.model;

/**
 * 经纬度信息实体类
 * CraetTime 2016-3-4
 * @author SunLiang
 */
public class LatLon {
    public double lat,lon;
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
}
