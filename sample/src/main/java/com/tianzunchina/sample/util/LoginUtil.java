package com.tianzunchina.sample.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.tianzunchina.android.api.login.SignInUser;
import com.tianzunchina.android.api.util.Config;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.app.SysApplication;
import com.tianzunchina.sample.model.User;

/**
 * 判断登录与否的工具类
 *
 * @author hl
 */
public final class LoginUtil extends Config {
    private static User nowUser;
    private static LoginUtil mLoginUtils = null;
    private static SharedPreferences sp;
    private static final String Login_Tag = "Login_User";
    public static final User GUEST = new User(2, "游客", "游客", "游客", "", 0, 0, R.drawable.ico_rank_picture);
    public static final String DB_USER_ACCOUNT = "USER_ACCOUNT";
    public static final String DB_AUTO_LOGIN = "AUTO_LOGIN";
    public static final String DB_PASSWORD = "USER_PWD";
    public static final String IS_REMEMBER = "IS_REMEMBER";
    private static final String USER_NAME = "account";
    private static final String USER_PASSWORD = "password";
    private static final String IS_SAVE_PASSWORD = "isKeep";

    private LoginUtil() {
        sp = SysApplication.getInstance().getSharedPreferences(Login_Tag, Activity.MODE_PRIVATE);
    }

    public static LoginUtil getInstance() {
        if (mLoginUtils == null) {
            synchronized (LoginUtil.class) {
                if (mLoginUtils == null) {
                    mLoginUtils = new LoginUtil();
                }
            }
        }
        return mLoginUtils;
    }

    public SharedPreferences getSp() {
        return sp;
    }

    /**
     * 判断是否登陆，根据UserId是否为空
     *
     * @return
     */
    public boolean isLogin() {
        return sp.getInt("UserID", 0) == 0 ? false : true;
    }

    public static void setUser(User user) {
        if (user == null) {
            return;
        }
        LoginUtil.nowUser = user;
    }

    synchronized public static User getNowUser() {
        if (nowUser == null) {
            nowUser = GUEST;
        }
        return nowUser;
    }

    /**
     * 访客登陆
     */
    public static void guestLogin() {
        nowUser = GUEST;
        SignInUser user = SignInUser.GUEST;
        user.save();
    }

    /**
     * 是否访客
     *
     * @return
     */
    public static boolean isGuest() {
        if (nowUser == null || nowUser.equals(GUEST)) {
            nowUser = GUEST;
            return true;
        }
        return false;
    }

    /**
     * 判断是否保存登录状态
     * 将之前在本地登录的信息进行返回
     *
     * @return 返回本地保存的User对象
     */
    public SignInUser loadUser() {
        SignInUser user = SignInUser.GUEST;
        String userName = loadString(USER_NAME);
        String password = loadString(USER_PASSWORD);
        boolean isKeep = loadBoolean(IS_SAVE_PASSWORD);
        if (userName != null) {
            user = new SignInUser(userName, password, isKeep);
        }
        return user;
    }

    /**
     * 登陆，保存LoginBean对象
     *
     * @param user
     */
    public void Login(User user) {
        Editor editor = sp.edit();// 获取编辑器
        editor.putInt("UserID", user.getUserID());
        editor.putString("Account", user.getAccount());
        editor.putInt("RegionID", user.getRegion().getRegionID());
        editor.putString("Phone", user.getPhone());
        editor.putString("Email", user.getEmail());
        editor.putInt("IsSocialWorker", user.getIsSocialWorker());
        editor.putInt("IsAuthentication", user.getIsAuthentication());
        editor.putString("Name", user.getName());
        editor.putString("Address", user.getAddress());
        editor.putString("NickName", user.getNickName());
        editor.putString("PicPath", user.getPicPath());
        editor.putBoolean("PartyBuildingAuthority", user.isDjzrThor());
        editor.putInt("TotalScore", user.getTotalScore());

        editor.commit();
    }

    /**
     * 登出
     */
    public void LoginOut() {
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取UserID
     *
     * @return
     */
    public Integer getUserID() {
        return sp.getInt("UserID", 0);
    }

    /**
     * 获取积分
     *
     * @return
     */
    public Integer getTotalScore() {
        return sp.getInt("TotalScore", 0);
    }

    /**
     * 获取Account
     *
     * @return
     */
    public String getAccount() {
        return sp.getString("Account", null);
    }

    /**
     * 获取RegionID
     *
     * @return
     */
    public Integer getRegionID() {
        return sp.getInt("RegionID", 0);
    }

    /**
     * 获取Phone
     *
     * @return
     */
    public String getPhone() {
        return sp.getString("Phone", null);
    }

    /**
     * 获取Email
     *
     * @return
     */
    public String getEmail() {
        return sp.getString("Email", null);
    }

    /**
     * 获取IsSocialWorker
     *
     * @return
     */
    public Integer getIsSocialWorker() {
        return sp.getInt("IsSocialWorker", 0);
    }

    /**
     * 获取IsAuthentication
     *
     * @return
     */
    public Integer getIsAuthentication() {
        return sp.getInt("IsAuthentication", 0);
    }

    /**
     * 获取Name
     *
     * @return
     */
    public String getName() {
        return sp.getString("Name", null);
    }

    /**
     * 获取IDCard
     *
     * @return
     */
    public String getIDCard() {
        return sp.getString("IDCard", null);
    }

    /**
     * 获取Address
     *
     * @return
     */
    public String getAddress() {
        return sp.getString("Address", null);
    }

    /**
     * 获取NickName
     *
     * @return
     */
    public String getNickName() {
        return sp.getString("NickName", null);
    }

    /**
     * 获取PicPath
     *
     * @return
     */
    public String getPicPath() {
        return sp.getString("PicPath", null);
    }

    /**
     * 获取PartyBuildingAuthority
     *
     * @return
     */
    public Boolean getPartyBuildingAuthority() {
        return sp.getBoolean("PartyBuildingAuthority", false);
    }

    /**
     * 获取CommunityServiceAuthority
     *
     * @return
     */
    public Boolean getCommunityServiceAuthority() {
        return sp.getBoolean("CommunityServiceAuthority", false);
    }

    public static void clearCacheUserPassWord(Context context) {
        setStringPref(context, DB_PASSWORD, null);
        setBooleanPref(context, DB_AUTO_LOGIN, false);
    }


    public static User getCacheUser(Context context) {
        String account = getStringPref(context, DB_USER_ACCOUNT, null);
        if (account == null || account.isEmpty()) {
            return null;
        } else {
            User user = getNowUser();
            user.setAccount(account);
            user.setPassword(getStringPref(context, DB_PASSWORD, null));
            user.setIsRemember(getBooleanPref(context, IS_REMEMBER, false));
            return user;
        }
    }

    public static void setCacheUser(Context context, User user, String pwd, boolean isKeep) {
        if (user != null) {
            setStringPref(context, DB_USER_ACCOUNT, user.getAccount());
            setStringPref(context, DB_PASSWORD, pwd);
            setBooleanPref(context, IS_REMEMBER, user.isRemember());
        }
    }

    public static String getStringPref(Context context, String name, String def) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getString(name, def);
    }

    public static void setStringPref(Context context, String name, String value) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        Editor ed = prefs.edit();
        ed.putString(name, value);
        ed.apply();
    }

    public static boolean getBooleanPref(Context context, String name, boolean def) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getBoolean(name, def);
    }

    public static void setBooleanPref(Context context, String name, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        Editor ed = prefs.edit();
        ed.putBoolean(name, value);
        ed.apply();
    }

    public SignInUser loadSDXFUser() {
        String key = "SDXF_" + LoginUtil.getNowUser().getUserID();
        String userName = loadString(key + "name");
        if (userName == null) {
            return null;
        }
        String password = loadString(key + "pass");
        return new SignInUser(userName, password);
    }

}
