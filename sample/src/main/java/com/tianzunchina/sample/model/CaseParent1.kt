package com.tianzunchina.sample.model

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

/**
 * Created by Administrator on 2017/6/1.
 */
class CaseParent1 : Serializable {
    var content: String? = null
    var title: String? = null
    var imgUrl: String? = null
    var status: Int = 0
    var type: Int = 0
    var id: Int = 0
    var imgId: Int = 0
    var pictures = arrayOf("", "", "", "")
    var network: Int = 0
    var reportTime: String? = null// 上报时间
    var createTime: String? = null// 发生时间
    var actionTime: String? = null// 执法时间
    var updateTime: String? = null//更新时间
    var wiid: String? = null
    var aiid: String? = null
    var eventAction: Int = 0
    var address: String? = null

    var picture1: String
        get() = pictures[0]
        set(picture1) {
            this.pictures[0] = picture1
        }

    var picture2: String
        get() = pictures[1]
        set(picture2) {
            this.pictures[1] = picture2
        }

    var picture3: String
        get() = pictures[2]
        set(picture3) {
            this.pictures[2] = picture3
        }

    var picture4: String
        get() = pictures[3]
        set(picture4) {
            this.pictures[3] = picture4
        }

    fun getPicture(index: Int): String {
        return pictures[index]
    }

    fun setPicture(index: Int, path: String) {
        this.pictures[index] = path
    }

    fun getCaseParent(jsonData: JSONObject) {
        try {
            title = jsonData.getString("title")
            address = jsonData.getString("address")
            content = jsonData.getString("content")
            updateTime = jsonData.getString("updateTime")
            picture1 = jsonData.getString("picture1")
            wiid = jsonData.getString("wiid")
            aiid = jsonData.getString("aiid")
            eventAction = jsonData.getInt("ADID")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    companion object {
        var PICS = arrayOf("picture1", "picture2", "picture3", "picture4")
        val NET_WORK = "network"
    }

}
