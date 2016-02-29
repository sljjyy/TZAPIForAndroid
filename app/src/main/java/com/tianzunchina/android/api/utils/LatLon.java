package com.tianzunchina.android.api.utils;

public class LatLon {
	private double lat;
	private double lon;
	public LatLon(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
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
	@Override
	public String toString() {
		return "LatLon [lat=" + lat + ", lon=" + lon + "]";
	}
}
