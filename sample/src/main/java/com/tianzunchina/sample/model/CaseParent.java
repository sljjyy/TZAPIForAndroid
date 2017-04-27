package com.tianzunchina.sample.model;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * 事件实体类——父类
 * Created by sg on 2015/5/29.
 */
public class CaseParent implements Serializable {
    private String content;
    private String title;
    private String imgUrl;
    private int status;
    private int type;
    private int id;
    private int imgId;
    private String[] pictures = {"", "", "", ""};
    public static String[] PICS = {"picture1", "picture2", "picture3", "picture4"};
    public final static String NET_WORK = "network";
    private int network;
    private String reportTime;// 上报时间
    private String createTime;// 发生时间
    private String actionTime;// 执法时间
    private String updateTime;//更新时间
    private String wiid;
    private String aiid;
    private int eventAction;
    private String address;

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEventAction() {
        return eventAction;
    }

    public void setEventAction(int eventAction) {
        this.eventAction = eventAction;
    }

    public String getWiid() {
        return wiid;
    }

    public void setWiid(String wiid) {
        this.wiid = wiid;
    }

    public String getAiid() {
        return aiid;
    }

    public void setAiid(String aiid) {
        this.aiid = aiid;
    }


    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    public String getPicture1() {
        return pictures[0];
    }

    public void setPicture1(String picture1) {
        this.pictures[0] = picture1;
    }

    public String getPicture2() {
        return pictures[1];
    }

    public void setPicture2(String picture2) {
        this.pictures[1] = picture2;
    }

    public String getPicture3() {
        return  pictures[2];
    }

    public void setPicture3(String picture3) {
        this.pictures[2] = picture3;
    }

    public String getPicture4() {
        return pictures[3];
    }

    public void setPicture4(String picture4) {
        this.pictures[3] = picture4;
    }

    public String getPicture(int index){
        return pictures[index];
    }

    public void setPicture(int index,String path){
         this.pictures[index] = path;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void getCaseParent(JSONObject jsonData) {
        try {
            setTitle(jsonData.getString("title"));
            setAddress(jsonData.getString("address"));
            setContent(jsonData.getString("content"));
            setUpdateTime(jsonData.getString("updateTime"));
            setPicture1(jsonData.getString("picture1"));
            setWiid(jsonData.getString("wiid"));
            setAiid(jsonData.getString("aiid"));
            setEventAction(jsonData.getInt("ADID"));
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

}
