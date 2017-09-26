package com.tianzunchina.sample.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.tianzunchina.android.api.login.SignInUser
import com.tianzunchina.android.api.util.Config
import com.tianzunchina.sample.R
import com.tianzunchina.sample.app.SysApplication1
import com.tianzunchina.sample.model.User1

/**
 * Created by Administrator on 2017/6/2.
 */
class LoginUtil1 private constructor() : Config() {

    init {
        sp = SysApplication1.instance!!.getSharedPreferences(Login_Tag, Activity.MODE_PRIVATE)
    }

    fun getSp(): SharedPreferences {
        return sp
    }

    /**
     * 判断是否登陆，根据UserId是否为空

     * @return
     */
    val isLogin: Boolean
        get() = sp.getInt("UserID", 0) != 0

    /**
     * 判断是否保存登录状态
     * 将之前在本地登录的信息进行返回

     * @return 返回本地保存的User对象
     */
    fun loadUser(): SignInUser {
        var user = SignInUser.GUEST
        val userName = loadString(USER_NAME)
        val password = loadString(USER_PASSWORD)
        val isKeep = loadBoolean(IS_SAVE_PASSWORD)!!
        if (userName != null) {
            user = SignInUser(userName, password, isKeep)
        }
        return user
    }

    /**
     * 登陆，保存LoginBean对象

     * @param user
     */
    fun Login(user: User1) {
        val editor = sp.edit()// 获取编辑器
        editor.putInt("UserID", user.userID)
        editor.putString("Account", user.account)
        editor.putInt("RegionID", user.region!!.regionID)
        editor.putString("Phone", user.phone)
        editor.putString("Email", user.email)
        editor.putInt("IsSocialWorker", user.isSocialWorker)
        editor.putInt("IsAuthentication", user.isAuthentication)
        editor.putString("Name", user.name)
        editor.putString("Address", user.address)
        editor.putString("NickName", user.nickName)
        editor.putString("PicPath", user.picPath)
        editor.putBoolean("PartyBuildingAuthority", user.isDjzrThor)
        editor.putInt("TotalScore", user.totalScore)

        editor.commit()
    }

    /**
     * 登出
     */
    fun LoginOut() {
        val editor = sp.edit()
        editor.clear()
        editor.commit()
    }

    /**
     * 获取UserID

     * @return
     */
    val userID: Int
        get() = sp.getInt("UserID", 0)

    /**
     * 获取积分

     * @return
     */
    val totalScore: Int
        get() = sp.getInt("TotalScore", 0)

    /**
     * 获取Account

     * @return
     */
    val account: String
        get() = sp.getString("Account", null)

    /**
     * 获取RegionID

     * @return
     */
    val regionID: Int
        get() = sp.getInt("RegionID", 0)

    /**
     * 获取Phone

     * @return
     */
    val phone: String
        get() = sp.getString("Phone", null)

    /**
     * 获取Email

     * @return
     */
    val email: String
        get() = sp.getString("Email", null)

    /**
     * 获取IsSocialWorker

     * @return
     */
    val isSocialWorker: Int
        get() = sp.getInt("IsSocialWorker", 0)

    /**
     * 获取IsAuthentication

     * @return
     */
    val isAuthentication: Int
        get() = sp.getInt("IsAuthentication", 0)

    /**
     * 获取Name

     * @return
     */
    val name: String
        get() = sp.getString("Name", null)

    /**
     * 获取IDCard

     * @return
     */
    val idCard: String
        get() = sp.getString("IDCard", null)

    /**
     * 获取Address

     * @return
     */
    val address: String
        get() = sp.getString("Address", null)

    /**
     * 获取NickName

     * @return
     */
    val nickName: String
        get() = sp.getString("NickName", null)

    /**
     * 获取PicPath

     * @return
     */
    val picPath: String
        get() = sp.getString("PicPath", null)

    /**
     * 获取PartyBuildingAuthority

     * @return
     */
    val partyBuildingAuthority: Boolean
        get() = sp.getBoolean("PartyBuildingAuthority", false)

    /**
     * 获取CommunityServiceAuthority

     * @return
     */
    val communityServiceAuthority: Boolean
        get() = sp.getBoolean("CommunityServiceAuthority", false)

    fun loadSDXFUser(): SignInUser? {
        val key = "SDXF_" + LoginUtil1.getNowUser().userID
        val userName = loadString(key + "name") ?: return null
        val password = loadString(key + "pass")
        return SignInUser(userName, password)
    }

    companion object {
        var user1: User1? = null
        private var mLoginUtils: LoginUtil1? = null
        private var sp: SharedPreferences = null!!
        private val Login_Tag = "Login_User"
        private val GUEST = User1(2, "游客", "游客", "游客", "", 0, 0, R.drawable.ico_rank_picture)
        private val DB_USER_ACCOUNT = "USER_ACCOUNT"
        private val DB_AUTO_LOGIN = "AUTO_LOGIN"
        private val DB_PASSWORD = "USER_PWD"
        private val IS_REMEMBER = "IS_REMEMBER"
        private val USER_NAME = "account"
        private val USER_PASSWORD = "password"
        private val IS_SAVE_PASSWORD = "isKeep"

        val instance: LoginUtil1
            get() {
                if (mLoginUtils == null) {
                    synchronized(LoginUtil1::class.java) {
                        if (mLoginUtils == null) {
                            mLoginUtils = LoginUtil1()
                        }
                    }
                }
                return mLoginUtils!!
            }

        fun setUser(user: User1?) {
            if (user == null) {
                return
            }
            LoginUtil1.user1 = user
        }

        fun getNowUser(): User1{
            if (user1 == null) {
                user1 = GUEST
            }
            return user1 as User1
        }

        /**
         * 访客登陆
         */
        fun guestLogin() {
            user1 = GUEST
            val user = SignInUser.GUEST
            user.save()
        }

        /**
         * 是否访客

         * @return
         */
        val isGuest: Boolean
            get() {
                if (user1 == null || user1 == GUEST) {
                    user1 = GUEST
                    return true
                }
                return false
            }

        fun clearCacheUserPassWord(context: Context) {
            setStringPref(context, DB_PASSWORD, null)
            setBooleanPref(context, DB_AUTO_LOGIN, false)
        }


        fun getCacheUser(context: Context): User1? {
            val account = getStringPref(context, DB_USER_ACCOUNT, null)
            if (account == null || account.isEmpty()) {
                return null
            } else {
                val user = getNowUser()
                user.account = account
                user.password = getStringPref(context, DB_PASSWORD, null)
                user.isRemember = (getBooleanPref(context, IS_REMEMBER, false))
                return user
            }
        }

        fun setCacheUser(context: Context, user: User1?, pwd: String, isKeep: Boolean) {
            if (user != null) {
                setStringPref(context, DB_USER_ACCOUNT, user.account)
                setStringPref(context, DB_PASSWORD, pwd)
                setBooleanPref(context, IS_REMEMBER, user.isRemember)
            }
        }

        fun getStringPref(context: Context, name: String, def: String?): String? {
            val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            return prefs.getString(name, def)
        }

        fun setStringPref(context: Context, name: String, value: String?) {
            val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val ed = prefs.edit()
            ed.putString(name, value)
            ed.apply()
        }

        fun getBooleanPref(context: Context, name: String, def: Boolean): Boolean {
            val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            return prefs.getBoolean(name, def)
        }

        fun setBooleanPref(context: Context, name: String, value: Boolean) {
            val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val ed = prefs.edit()
            ed.putBoolean(name, value)
            ed.apply()
        }
    }

}
