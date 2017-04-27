package com.tianzunchina.sample.model;

import com.tianzunchina.android.api.util.TimeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class CircleEvalAct {
	private int id;
	private String name;
	private Long time;
	private String content;
	private int score;
	private String headImage;

	public CircleEvalAct(JSONObject json) {
		try {
			this.setId(json.getInt("CAALID"));
			this.name = json.getString("UserName");
			this.time = json.getLong("EvaluateTime") * 1000;
			this.content = json.getString("EvaluationContent");
			this.score = json.getInt("EvaluateScore");
			this.headImage = json.getString("UserHeader");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTime() {
		return time;
	}

	public String getTimeTimeStr() {
		return TimeConverter.date2Str(time);
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
		CircleEvalAct other = (CircleEvalAct) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
