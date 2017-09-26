package com.tianzunchina.sample.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.view.View
import android.widget.AdapterView
import com.tianzunchina.android.api.view.list.TZCommonListActivity
import com.tianzunchina.sample.R
import com.tianzunchina.sample.model.Circle
import org.json.JSONObject

/**
 * Created by Administrator on 2017/5/26.
 */
class CircleJoinedActivity1 : TZCommonListActivity<Circle>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setup()
    }

    private fun setup() {
        setTitle("我的圈子", "返回", null)
        val propertyMap = ArrayMap<String, Any>()
        propertyMap.put("UserID", 30)
        webServicePropertys = propertyMap
        adapter = CircleListAdapter(this, listData, R.layout.item_list2, false)
        setAdapter(adapter)
    }

    override fun getWebServicePropertys(): ArrayMap<String, Any> {
        return super.getWebServicePropertys()
    }

    private fun init() {
        val url = "http://218.108.93.154:8090/" + "CirclesService.asmx"
        init(url, "http://tempuri.org/", "GetJoinedCircles", "SkipNumber", "TakeNumber",
                "Circles", TIME_OUT)
    }

    override fun json2Obj(json: JSONObject): Circle {
        return Circle(json)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        super.onItemClick(parent, view, position, id)
        val circle = parent.adapter.getItem(position) as Circle ?: return

        val intent = Intent(this, CircleActivity::class.java)

        val bundle = Bundle()
        bundle.putSerializable("circle", circle)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    companion object {

        private val TIME_OUT = 10000
    }
}
