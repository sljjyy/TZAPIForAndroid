package com.tianzunchina.sample.form

import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.android.api.widget.form.DynamicFormFragment
import com.tianzunchina.android.api.widget.form.FormItem
import com.tianzunchina.android.api.widget.form.FormTable
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem
import com.tianzunchina.sample.R
import java.util.ArrayList

/**
 * Created by Administrator on 2017/5/25.
 */
class DynamicFormActivity1 : TZAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_form)
        toFragment()
    }

    private fun toFragment() {
        val fragment = DynamicFormFragment()
        val bundle = Bundle()
        val json = json
        bundle.putString("json", json)
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val fTransaction = fragmentManager.beginTransaction()
        fTransaction.add(fragment, DynamicFormFragment.TAG)
        fTransaction.replace(R.id.fragment, fragment, DynamicFormFragment.TAG)
        fTransaction.commit()
    }

    private val json: String
        get() {
            val table = FormTable()
            table.title = "测试表"
            table.colorIDContent = R.color.grey_black_1000
            table.items.add(FormItem("姓名"))
            table.items.add(FormItem("年龄"))
            table.items.add(FormItem("地址"))
            table.items.add(FormItem("电话"))
            val items = ArrayList<ArrayAdapterItem>()
            items.add(ArrayAdapterItem(1, 0, "1111", "一一一一一"))
            items.add(ArrayAdapterItem(2, 0, "2222", "二二二二二"))
            items.add(ArrayAdapterItem(3, 0, "3333", "三三三三三"))
            table.items.add(FormItem("单选框", items))
            table.items.add(FormItem("证件"))
            table.items.add(FormItem("巴拉巴拉"))
            return JSON.toJSONString(table)
        }
}
