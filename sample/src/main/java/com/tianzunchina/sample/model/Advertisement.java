package com.tianzunchina.sample.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Advertisement {
	private int adID;
	private int adTypeID;
	private String adPicURL;
	private int newsID;
	private String newsTitle;
	private String newsURL;

	public Advertisement(JSONObject json) {
		try {
			this.adID = json.getInt("ADID");
			this.adTypeID = json.getInt("ADTypeID");
			this.adPicURL = json.getString("ADPicUrl");
			this.newsID = json.getInt("NewsID");
			this.newsTitle = json.getString("NewsTitle");
			this.newsURL = json.getString("UrlForPhone");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getAdID() {
		return adID;
	}

	public void setAdID(int adID) {
		this.adID = adID;
	}

	public int getAdTypeID() {
		return adTypeID;
	}

	public void setAdTypeID(int adTypeID) {
		this.adTypeID = adTypeID;
	}

	public String getAdPicURL() {
		return adPicURL;
	}

	public void setAdPicURL(String adPicURL) {
		this.adPicURL = adPicURL;
	}

	public int getNewsID() {
		return newsID;
	}

	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsURL() {
		return newsURL;
	}

	public void setNewsURL(String newsURL) {
		this.newsURL = newsURL;
	}

}
