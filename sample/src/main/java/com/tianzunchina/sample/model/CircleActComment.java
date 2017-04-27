package com.tianzunchina.sample.model;

import com.tianzunchina.android.api.util.TimeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class CircleActComment {
	private int id;
	private int circleActID;
	private String userName;
	private String commentContent;
	private Long commentTime;
	private String headImage;

	public CircleActComment(JSONObject jsonObject) {
		try {
			this.setId(jsonObject.getInt("CCID"));
			this.circleActID = jsonObject.getInt("CAID");
			this.userName = jsonObject.getString("UserName");
			this.commentContent = jsonObject.getString("CCContent");
			this.commentTime = jsonObject.getLong("CommentsTime") * 1000;
			this.headImage = jsonObject.getString("UserHeader");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCircleActID() {
		return circleActID;
	}

	public void setCircleActID(int circleActID) {
		this.circleActID = circleActID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Long getCommentTime() {
		return commentTime;
	}

	public String getCommentTimeTimeStr() {
		return TimeConverter.date2Str(commentTime);
	}

	public void setCommentTime(Long commentTime) {
		this.commentTime = commentTime;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
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
		CircleActComment other = (CircleActComment) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
