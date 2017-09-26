package com.tianzunchina.sample.model

import com.tianzunchina.android.api.util.TimeConverter
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.*

/**
 * Created by Administrator on 2017/6/1.
 */
class News1 : Serializable {
    var id: Int = 0
    var ncID: Int = 0
    var title: String? = null
    var content: String? = null
    var author: String? = null
    var url: String? = null
    var isTop: Boolean = false
    var updateTime: Long = 0
    private var paths: MutableList<String>? = ArrayList()
    var isOnline: Boolean = false

    constructor(title: String, url: String) {
        this.title = title
        this.url = url
    }

    constructor(title: String, url: String, typeID: Int) {
        this.title = title
        this.url = url
        this.ncID = typeID//邻居节特殊处理
    }

    constructor(id: Int, ncID: Int, title: String, content: String, author: String,
                url: String, isTop: Boolean, updateTime: Long, paths: MutableList<String>,
                isOnline: Boolean) {
        this.id = id
        this.ncID = ncID
        this.title = title
        this.content = content
        this.author = author
        this.url = url
        this.isTop = isTop
        this.updateTime = updateTime
        this.paths = paths
        this.isOnline = isOnline
    }

    constructor(json: JSONObject) {
        try {
            id = json.getInt("NewsID")
            ncID = json.getInt("NCID")
            title = json.getString("NewsTitle")
            content = json.getString("NewsContent")
            author = json.getString("Author")
            url = json.getString("UrlForPhone")
            val jsons = json.getJSONArray("NewAttachPaths")
            for (i in 0..jsons.length() - 1) {
                paths!!.add(jsons.getString(i))
            }
            isOnline = json.getBoolean("IsOnline")
            isTop = json.getBoolean("IsTop")
            updateTime = json.getLong("UpdateTime") * 1000
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun getPaths(): MutableList<String>? {
        return paths
    }

    val path: String?
        get() {
            if (paths != null && paths!!.size > 0) {
                return paths!![0]
            }
            return null
        }

    fun setPaths(paths: MutableList<String>) {
        this.paths = paths
    }

    val updateTimeStr: String
        get() = TimeConverter.date2Str(Date(updateTime), "MM月dd日 HH:mm")
    val type: Int
        get() {
            var type = 0
            when (paths!!.size) {
                0 -> {
                }
                else -> type = 1
            }
            return type
        }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + id
        result = prime * result + ncID
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as News1?
        if (id != other!!.id)
            return false
        if (ncID != other.ncID)
            return false
        return true
    }

    override fun toString(): String {
        return "News [title=$title, content=$content, paths="
        (+paths!!.size).toString() + "]"
    }

    companion object {
        /**

         */
        private const val serialVersionUID = 1L
    }


}