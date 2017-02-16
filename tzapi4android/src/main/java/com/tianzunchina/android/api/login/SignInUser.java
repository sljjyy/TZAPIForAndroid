package com.tianzunchina.android.api.login;


import com.tianzunchina.android.api.util.LoginConfig;

import java.io.Serializable;

/**
 * 登录信息实体类
 * CraetTime 2016-3-3
 *
 * @author SunLiang
 */
public class SignInUser implements Serializable {
    public static SignInUser GUEST = new SignInUser("访客");
    String account;
    String password;
    boolean isKeep = false;
    boolean isAuto = false;

    public SignInUser(String userName, String userPassword) {
        this.account = userName;
        this.password = userPassword;
    }

    public SignInUser(String userName, String userPassword, Boolean isKeep) {
        this.account = userName;
        this.password = userPassword;
        this.isKeep = isKeep;
    }

    public SignInUser(String userName, String userPassword, Boolean isKeep,
                      Boolean isAuto) {
        this.account = userName;
        this.password = userPassword;
        this.isKeep = isKeep;
        this.isAuto = isAuto;
    }

    public SignInUser(String userName) {
        this.account = userName;
    }

    public void save() {
        LoginConfig config = new LoginConfig();
        config.saveUser(this);
    }

    public boolean noGuest() {
        return !this.equals(GUEST);
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public boolean isKeep() {
        return isKeep;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public String toString() {
        return "SignInUser [account=" + account + ", " +
                "password=" + password + ", " +
                "isAuto=" + isAuto + "]";
    }
}
