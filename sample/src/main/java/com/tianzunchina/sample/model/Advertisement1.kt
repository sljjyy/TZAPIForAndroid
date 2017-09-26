package com.tianzunchina.sample.model

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Administrator on 2017/6/1.
 */
class Advertisement1(json: JSONObject) {
    var adID: Int = 0
    var adTypeID: Int = 0
    var adPicURL: String? = null
    var newsID: Int = 0
    var newsTitle: String? = null
    var newsURL: String? = null

    init {
        try {
            this.adID = json.getInt("ADID")
            this.adTypeID = json.getInt("ADTypeID")
            this.adPicURL = json.getString("ADPicUrl")
            this.newsID = json.getInt("NewsID")
            this.newsTitle = json.getString("NewsTitle")
            this.newsURL = json.getString("UrlForPhone")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

}
