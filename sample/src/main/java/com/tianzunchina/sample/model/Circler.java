package com.tianzunchina.sample.model;


import com.tianzunchina.android.api.util.TimeConverter;

import org.json.JSONObject;

import java.io.Serializable;

public class Circler extends Object implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String headImage;
	private int circleScore;
	private Long applyTime;
	private int authority;
	private String authorityName;
	private String joinActivityCount;

	public Circler(JSONObject json) {
		try {
			this.id = json.getInt("UserID");
			this.name = json.getString("UserName");
			this.headImage = json.getString("UserHeader");
			this.circleScore = json.getInt("GetScore");
			this.applyTime = json.getLong("ApplyTime") * 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Circler(int extra, JSONObject json) {
		try {
			this.id = json.getInt("UserID");
			this.name = json.getString("UserName");
			this.headImage = json.getString("UserHeader");
			this.authority = json.getInt("Authority");
			switch (this.authority) {
			case 1:
				this.authorityName = "（圈主）";
				break;
			case 2:
				this.authorityName = "（管理员）";
				break;
			case 3:
				this.authorityName = "（普通圈友）";
				break;
			default:
				this.authorityName = "";
				break;
			}
			this.joinActivityCount = json.getString("JoinActivityCount");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public int getCircleScore() {
		return circleScore;
	}

	public void setCircleScore(int circleScore) {
		this.circleScore = circleScore;
	}

	public int getAuthority() {
		return authority;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public String getApplyTimeStr() {
		return TimeConverter.date2Str(applyTime);
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getJoinActivityCount() {
		return joinActivityCount;
	}

	public void setJoinActivityCount(String joinActivityCount) {
		this.joinActivityCount = joinActivityCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Circler other = (Circler) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
