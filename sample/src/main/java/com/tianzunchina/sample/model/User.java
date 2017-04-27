package com.tianzunchina.sample.model;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name; // 真实姓名
    private String nickName;//昵称
    private int tempHead;
    private int UserID;//用户编号
    private String Account;//账号
    private String Password;//密码
    private Region region; // 行政区划
    private String Phone;//手机号码
    private String Email;//邮箱
    private int IsSocialWorker;//是否社工
    private int IsAuthentication;//是否认证
    private String Address;//地址
    private String PicPath;
    private boolean CommunityServiceAuthority;//社区服务权限
    private boolean djzrThor;// 党建责任权限
    private boolean auto_login;
    private int totalScore;//积分
    private boolean isRemember;

    public boolean isRemember() {
        return isRemember;
    }

    public void setIsRemember(boolean isRemember) {
        this.isRemember = isRemember;
    }


    public User() {
        super();
    }

    public User(int UserID, String Account, String PicPath) {
        super();
        this.UserID = UserID;
        this.Account = Account;
        this.PicPath = PicPath;
    }

    public User(int UserID, String Account, String name, String nickName, String Phone, int IsSocialWorker,
                int IsAuthentication, int imgFile) {
        super();
        this.UserID = UserID;
        this.Account = Account;
        this.nickName = nickName;
        this.name = name;
        this.Phone = Phone;
        this.IsSocialWorker = IsSocialWorker;
        this.IsAuthentication = IsAuthentication;
        this.tempHead = imgFile;
    }

    public User(int UserID, String Account, String nickName, String name, String Phone, int RegionID,
                int IsAuthentication) {
        super();
        this.UserID = UserID;
        this.Account = Account;
        this.nickName = nickName;
        this.name = name;
        this.Phone = Phone;
        this.IsAuthentication = IsAuthentication;
    }

    public User(JSONObject json) {
        super();

        try {
            this.UserID = json.getInt("UserID");
            this.Email = json.getString("Email");
            if (!json.isNull("Phone")) {
                Phone = json.getString("Phone");
            }
            if (!json.isNull("Account")) {
                Account = json.getString("Account");
            }
            if (!json.isNull("Name")) {
                name = json.getString("Name");
            }
            if (!json.isNull("Address")) {
                Address = json.getString("Address");
            }
            this.nickName = json.getString("NickName");
            this.PicPath = json.getString("PicPath");
            this.djzrThor = json.getBoolean("PartyBuildingAuthority");
            this.totalScore = json.getInt("TotalScore");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isAuto_login() {
        return auto_login;
    }

    public void setAuto_login(boolean auto_login) {
        this.auto_login = auto_login;
    }

    public boolean isCommunityServiceAuthority() {
        return CommunityServiceAuthority;
    }

    public void setCommunityServiceAuthority(boolean communityServiceAuthority) {
        CommunityServiceAuthority = communityServiceAuthority;
    }

    public boolean isDjzrThor() {
        return djzrThor;
    }

    public void setDjzrThor(boolean djzrThor) {
        this.djzrThor = djzrThor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setIsSocialWorker(int isSocialWorker) {
        IsSocialWorker = isSocialWorker;
    }

    public void setIsAuthentication(int isAuthentication) {
        IsAuthentication = isAuthentication;
    }


    public void setAddress(String address) {
        Address = address;
    }


    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

    public int getUserID() {
        return UserID;
    }

    public String getAccount() {
        return Account;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public int getIsSocialWorker() {
        return IsSocialWorker;
    }

    public int getIsAuthentication() {
        return IsAuthentication;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getAddress() {
        return Address;
    }

    public String getPicPath() {
        return PicPath;
    }

}
