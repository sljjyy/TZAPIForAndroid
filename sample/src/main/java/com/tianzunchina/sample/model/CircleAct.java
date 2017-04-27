package com.tianzunchina.sample.model;

import com.tianzunchina.android.api.util.TimeConverter;

import org.json.JSONObject;
import java.io.Serializable;

public class CircleAct implements Serializable {
	private static final long serialVersionUID = 1L;
	private String circleName;
	private String circleSmallPath;
	private int id;
	private int typeID;
	private String createUserHeader;
	private String title;
	private String content;
	private int createrID;
	private String createrName;
	private Long createTime;
	private double score;
	private int memberNum;
	private int isCommentActivity;
	private int isCommentMember;
	private int isComMemOfAuthority;
	private int isApply;
	private String[] pathArray = {};

	public CircleAct(JSONObject json) {
		try {
			this.circleName = json.getString("CircleName");
			this.circleSmallPath = json.getString("CircleSmallAttach");
			this.id = json.getInt("CAID");
			this.typeID = json.getInt("CATID");
			this.createUserHeader = json.getString("UserHeader");
			this.title = json.getString("CATitle");
			this.content = json.getString("CAContent");
			this.createrID = json.getInt("CreateUserID");
			this.createrName = json.getString("CreateUserName");
			this.createTime = json.getLong("CreateTime") * 1000;
			this.score = json.getDouble("CAScore");
			this.memberNum = json.getInt("UserCount");
			this.isCommentActivity = json.getInt("IsCommentActivity");
			this.isCommentMember = json.getInt("IsCommentMember");
			this.isComMemOfAuthority = json.getInt("CommentMemberOfAuthority");
			this.isApply = json.getInt("IsApply");
			String pathString = json.getString("Path");
			if (!"null".equals(pathString)) {
				this.pathArray = pathString.split(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleSmallPath() {
		return circleSmallPath;
	}

	public void setCircleSmallPath(String circleSmallPath) {
		this.circleSmallPath = circleSmallPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getCreateUserHeader() {
		return createUserHeader;
	}

	public void setCreateUserHeader(String createUserHeader) {
		this.createUserHeader = createUserHeader;
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

	public int getCreaterID() {
		return createrID;
	}

	public void setCreaterID(int createrID) {
		this.createrID = createrID;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public String getcreateTimeTimeStr() {
		return TimeConverter.date2Str(createTime);
	}

	public String getcreateTimeDateStr() {
		return TimeConverter.date2Str(createTime, "yyyy-MM-dd");
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getIsCommentActivity() {
		return isCommentActivity;
	}

	public void setIsCommentActivity(int isCommentActivity) {
		this.isCommentActivity = isCommentActivity;
	}

	public int getIsCommentMember() {
		return isCommentMember;
	}

	public void setIsCommentMember(int isCommentMember) {
		this.isCommentMember = isCommentMember;
	}

	public int getIsComMemOfAuthority() {
		return isComMemOfAuthority;
	}

	public void setIsComMemOfAuthority(int isComMemOfAuthority) {
		this.isComMemOfAuthority = isComMemOfAuthority;
	}

	public int getIsApply() {
		return isApply;
	}

	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}

	public String[] getPathArray() {
		return pathArray;
	}

	public void setPathArray(String[] pathArray) {
		this.pathArray = pathArray;
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
		CircleAct other = (CircleAct) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
