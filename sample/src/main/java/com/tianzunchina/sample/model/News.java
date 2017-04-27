package com.tianzunchina.sample.model;


import com.tianzunchina.android.api.util.TimeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int ncID;
	private String title;
	private String content;
	private String author;
	private String url;
	private boolean isTop;
	private long updateTime;
	private List<String> paths = new ArrayList<String>();
	private boolean isOnline;
	
	public News(String title, String url) {
		this.title = title;
		this.url = url;
	}

	public News(String title, String url, int typeID) {
		this.title = title;
		this.url = url;
		this.ncID = typeID;//邻居节特殊处理
	}

	public News(int id, int ncID, String title, String content, String author,
                String url, boolean isTop, long updateTime, List<String> paths,
                boolean isOnline) {
		this.id = id;
		this.ncID = ncID;
		this.title = title;
		this.content = content;
		this.author = author;
		this.url = url;
		this.isTop = isTop;
		this.updateTime = updateTime;
		this.paths = paths;
		this.isOnline = isOnline;
	}
	
	public News(JSONObject json){
		try {
			id = json.getInt("NewsID");
			ncID = json.getInt("NCID");
			title = json.getString("NewsTitle");
			content = json.getString("NewsContent");
			author = json.getString("Author");
			url = json.getString("UrlForPhone");
			JSONArray jsons = json.getJSONArray("NewAttachPaths");
			for(int i = 0; i < jsons.length(); i++){
				paths.add(jsons.getString(i));
			}
			isOnline = json.getBoolean("IsOnline");
			isTop = json.getBoolean("IsTop");
			updateTime = json.getLong("UpdateTime") * 1000;
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
	public int getNcID() {
		return ncID;
	}
	public void setNcID(int ncID) {
		this.ncID = ncID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getPaths() {
		return paths;
	}
	public String getPath() {
		if(paths != null && paths.size()>0){
			return paths.get(0);
		}
		return null;
	}
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public String getUpdateTimeStr(){
		return TimeConverter.date2Str(new Date(updateTime), "MM月dd日 HH:mm");
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public int getType(){
		int type = 0;
		switch (paths.size()) {
		case 0:
			break;
		default:
			type = 1;
			break;
		}
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ncID;
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
		News other = (News) obj;
		if (id != other.id)
			return false;
		if (ncID != other.ncID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", content=" + content + ", paths="
				+ paths.size() + "]";
	}
	
	
}