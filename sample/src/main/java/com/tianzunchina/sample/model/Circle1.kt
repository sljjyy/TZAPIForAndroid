package com.tianzunchina.sample.model

import com.tianzunchina.sample.widget.GVItem1
import org.json.JSONObject

/**
 * Created by Administrator on 2017/5/31.
 */
class Circle1 : GVItem1 {
    private val countDetail = "圈友$1名，活动$2次"
    var createTime: Long? = null
    var createAccess: Int = 0
    var authority: Int = 0
    var isApply: Int = 0
    var smallPath: String? = null
    var actCount: String? = null
    var circleMemCount: String? = null
    var content: String? = null

    constructor(id: Int, title: String, createTime: Long, createAccess: Int, authority: Int, isApply: Int, smallPath: String, actCount: String, circleMemCount: String, content: String) {
        this.id = id
        this.title = title
        this.createTime = createTime
        this.createAccess = createAccess
        this.authority = authority
        this.isApply = isApply
        this.smallPath = smallPath
        this.actCount = actCount
        this.circleMemCount = circleMemCount
        this.content = content
    }

    constructor(json: JSONObject) {
        try {
            this.id = json.getInt("CircleID")
            this.title = json.getString("CircleName")
            this.createTime = json.getLong("CreateTime")
            this.createAccess = json.getInt("CreateAccess")
            authority = json.getInt("CAUID")
            this.isApply = json.getInt("IsApply")
            this.attachPath = json.getString("MainAttach")
            this.smallPath = json.getString("SmallAttach")
            this.actCount = json.getString("ActivityCount")
            this.circleMemCount = json.getString("CircleMemberCount")
            this.description = countDetail.replace("$1", this.circleMemCount!!)
                    .replace("$2", this.actCount!!)
            this.content = json.getString("CircleContent")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    val isApplyStr: String
        get() = APPLY_STATUS[isApply]

    companion object {
        private val serialVersionUID = 1L

        private val APPLY_STATUS = arrayOf("未加入", "已加入", "审核中")
    }


}
