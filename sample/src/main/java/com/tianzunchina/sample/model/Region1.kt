package com.tianzunchina.sample.model

import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem
import com.tianzunchina.sample.widget.LinearLayoutItem
import java.io.Serializable

/**
 * Created by Administrator on 2017/6/1.
 */
class Region1(id: Int, parentID: Int, `val`: String, des: String) : ArrayAdapterItem(id, parentID, `val`, des), Serializable, LinearLayoutItem {

    var regionID: Int
        get() = intID!!
        set(regionID) = setID(regionID)

    val regionName: String
        get() = `val`

    val description: String
        get() = des

    override fun getTag(): String {
        return regionName.replace("支部", "")
    }

    override fun getTagID(): Int {
        return regionID
    }
}