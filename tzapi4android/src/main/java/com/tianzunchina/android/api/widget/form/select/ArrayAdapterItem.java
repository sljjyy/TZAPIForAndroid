package com.tianzunchina.android.api.widget.form.select;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ArrayAdapterItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String val = "";
	private String parentID = null;
	private String des;

	public ArrayAdapterItem() {
	}

	public ArrayAdapterItem(String id, String parentID, String val, String des) {
		this.id = id;
		this.val = val;
		this.parentID = parentID;
		this.des = des;
	}

	public ArrayAdapterItem(int id, int parentID, String val, String des) {
		setID(id);
		setParentID(parentID);
		this.val = val;
		this.des = des;
	}

	public ArrayAdapterItem(JSONObject jsonObject) {
		try {
			this.id = jsonObject.getString("EPMID");
			this.parentID = jsonObject.getString("ParentID");
			this.val = jsonObject.getString("EPMName");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setVal(String val) {
		this.val = val;
	}
	public ArrayAdapterItem(String gridNum) {
		id = gridNum;
		val = gridNum;
	}

	public ArrayAdapterItem(int id, String val) {
		this.id = String.valueOf(id);
		this.val = val;
		this.parentID = String.valueOf(0);
	}

	public ArrayAdapterItem(int id, int parentID, String val) {
		this.id = String.valueOf(id);
		this.val = val;
		this.parentID = String.valueOf(parentID);
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(int id){
		this.parentID = String.valueOf(id);
	}


	public int getIntParentID() {
		if (parentID == null) {
			return 0;
		}
		if (parentID.contains(".")) {
			double double1 = Double.valueOf(parentID);
			return (int) double1;
		} else {
			return Integer.valueOf(parentID);
		}
	}

	public void setID(int id){
		this.id = String.valueOf(id);
	}

	public String getID() {
		return id;
	}

	public Integer getIntID() {
		if (id == null) {
			return 0;
		}
		if (id.contains(".")) {
			double double1 = Double.valueOf(id);
			return (int) double1;
		} else {
			return Integer.valueOf(id);
		}
	}

	public String getVal() {
		return val;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return val;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArrayAdapterItem that = (ArrayAdapterItem) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (val != null ? !val.equals(that.val) : that.val != null) return false;
		return parentID != null ? parentID.equals(that.parentID) : that.parentID == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (val != null ? val.hashCode() : 0);
		result = 31 * result + (parentID != null ? parentID.hashCode() : 0);
		return result;
	}
}
