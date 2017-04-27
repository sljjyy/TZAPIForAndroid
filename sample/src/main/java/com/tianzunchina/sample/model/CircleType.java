package com.tianzunchina.sample.model;

import org.json.JSONObject;

import java.io.Serializable;

public class CircleType implements Serializable {
	private static final long serialVersionUID = 1L;
	private int circleTypeID;
	private String circleTypeName;
	private String circleTypeDescrible;
	private String path;

	public CircleType(int circleTypeID, String circleTypeName,
                      String circleTypeDescrible, String path) {
		super();
		this.circleTypeID = circleTypeID;
		this.circleTypeName = circleTypeName;
		this.circleTypeDescrible = circleTypeDescrible;
		this.path = path;
	}

	public CircleType(JSONObject json) {
		try {
			circleTypeID = json.getInt("CircleTypeID");
			circleTypeName = json.getString("CircleTypeName");
			circleTypeDescrible = json.getString("CircleTypeDescrible");
			path = json.getString("Path");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCircleTypeID() {
		return circleTypeID;
	}

	public void setCircleTypeID(int circleTypeID) {
		this.circleTypeID = circleTypeID;
	}

	public String getCircleTypeName() {
		return circleTypeName;
	}

	public void setCircleTypeName(String circleTypeName) {
		this.circleTypeName = circleTypeName;
	}

	public String getCircleTypeDescrible() {
		return circleTypeDescrible;
	}

	public void setCircleTypeDescrible(String circleTypeDescrible) {
		this.circleTypeDescrible = circleTypeDescrible;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + circleTypeID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CircleType other = (CircleType) obj;
		if (circleTypeID != other.circleTypeID)
			return false;
		return true;
	}

}
