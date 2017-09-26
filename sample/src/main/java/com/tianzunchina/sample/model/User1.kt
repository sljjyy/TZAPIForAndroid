package com.tianzunchina.sample.model

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

/**
 * Created by Administrator on 2017/6/1.
 */
open class User1 : Serializable {
    var name: String? = null // 真实姓名
    var nickName: String? = null//昵称
    private var tempHead: Int = 0
    var userID: Int = 0//用户编号
    var account: String? = null//账号
    var password: String? = null//密码
    var region: Region1? = null // 行政区划
    var phone: String? = null//手机号码
    var email: String? = null//邮箱
    var isSocialWorker: Int = 0//是否社工
    var isAuthentication: Int = 0//是否认证
    var address: String? = null//地址
    var picPath: String? = null
    var isCommunityServiceAuthority: Boolean = false//社区服务权限
    var isDjzrThor: Boolean = false// 党建责任权限
    var isAuto_login: Boolean = false
    var totalScore: Int = 0//积分
    var isRemember: Boolean = false


    constructor() : super() {}

    constructor(UserID: Int, Account: String, PicPath: String) : super() {
        this.userID = UserID
        this.account = Account
        this.picPath = PicPath
    }

    constructor(UserID: Int, Account: String, name: String, nickName: String, Phone: String, IsSocialWorker: Int,
                IsAuthentication: Int, imgFile: Int) : super() {
        this.userID = UserID
        this.account = Account
        this.nickName = nickName
        this.name = name
        this.phone = Phone
        this.isSocialWorker = IsSocialWorker
        this.isAuthentication = IsAuthentication
        this.tempHead = imgFile
    }

    constructor(UserID: Int, Account: String, nickName: String, name: String, Phone: String, RegionID: Int,
                IsAuthentication: Int) : super() {
        this.userID = UserID
        this.account = Account
        this.nickName = nickName
        this.name = name
        this.phone = Phone
        this.isAuthentication = IsAuthentication
    }

    constructor(json: JSONObject) : super() {

        try {
            this.userID = json.getInt("UserID")
            this.email = json.getString("Email")
            if (!json.isNull("Phone")) {
                phone = json.getString("Phone")
            }
            if (!json.isNull("Account")) {
                account = json.getString("Account")
            }
            if (!json.isNull("Name")) {
                name = json.getString("Name")
            }
            if (!json.isNull("Address")) {
                address = json.getString("Address")
            }
            this.nickName = json.getString("NickName")
            this.picPath = json.getString("PicPath")
            this.isDjzrThor = json.getBoolean("PartyBuildingAuthority")
            this.totalScore = json.getInt("TotalScore")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    companion object {

        private const val serialVersionUID = 1L
    }

}
