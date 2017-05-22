package com.tianzunchina.sample.model;

import com.tianzunchina.sample.widget.GVItem;

import org.json.JSONObject;

public class Circle extends GVItem {
	private static final long serialVersionUID = 1L;
	private String countDetail = "圈友$1名，活动$2次";
	private Long createTime;
	private int createAccess;
	private int authority;
	private int isApply;
	private String smallPath;
	private String actCount;
	private String circleMemCount;
	private String content;

	private final static String[] APPLY_STATUS = {"未加入", "已加入", "审核中"};

	public Circle(int id,String title,long createTime,int createAccess,int authority,int isApply,String smallPath
			,String actCount,String circleMemCount,String content){
		this.id = id;
		this.title =title;
		this.createTime = createTime;
		this.createAccess = createAccess;
		this.authority = authority;
		this.isApply = isApply;
		this.smallPath = smallPath;
		this.actCount = actCount;
		this.circleMemCount = circleMemCount;
		this.content = content;
	}

	public Circle(JSONObject json) {
		try {
			this.id = json.getInt("CircleID");
			this.title = json.getString("CircleName");
			this.createTime = json.getLong("CreateTime");
			this.createAccess = json.getInt("CreateAccess");
			setAuthority(json.getInt("CAUID"));
			this.isApply = json.getInt("IsApply");
			this.attachPath = json.getString("MainAttach");
			this.smallPath = json.getString("SmallAttach");
			this.actCount = json.getString("ActivityCount");
			this.circleMemCount = json.getString("CircleMemberCount");
			this.description = countDetail.replace("$1", this.circleMemCount)
					.replace("$2", this.actCount);
			this.content = json.getString("CircleContent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public int getCreateAccess() {
		return createAccess;
	}

	public void setCreateAccess(int createAccess) {
		this.createAccess = createAccess;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public int getIsApply() {
		return isApply;
	}
	public String getIsApplyStr(){
		return APPLY_STATUS[isApply];
	}

	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}

	public String getSmallPath() {
		return smallPath;
	}

	public void setSmallPath(String smallPath) {
		this.smallPath = smallPath;
	}

	public String getActCount() {
		return actCount;
	}

	public void setActCount(String actCount) {
		this.actCount = actCount;
	}

	public String getCircleMemCount() {
		return circleMemCount;
	}

	public void setCircleMemCount(String circleMemCount) {
		this.circleMemCount = circleMemCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
