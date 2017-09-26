package com.tianzunchina.sample.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.tianzunchina.android.api.network.HTTPWebAPI
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem
import com.tianzunchina.android.api.widget.form.select.ItemSelectedBottomSheetDialog
import com.tianzunchina.android.api.widget.form.select.ItemSelectedCallBack
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup
import com.tianzunchina.sample.R
import java.util.*

/**
 * Created by Administrator on 2017/5/31.
 */
class EventCaseFragment1 : Fragment() {
    internal lateinit var llBigKind: LinearLayout
    internal lateinit var llSmallKind: LinearLayout
    internal lateinit var llReportDate: LinearLayout
    internal lateinit var llMap: LinearLayout//大类，小类，时间，地图
    internal lateinit var tvBigKinds: TextView
    internal lateinit var tvSmallKinds: TextView
    internal lateinit var tvReportDate: TextView
    internal lateinit var tvEventLocation: TextView//大类，小类，时间，地址坐标
    internal lateinit var etContactName: EditText
    internal lateinit var etContactMobile: EditText
    internal lateinit var etEventTitle: EditText
    internal lateinit var etEventAddress: EditText
    internal lateinit var etReportContent: EditText//联系人名字，联系人电话，事件标题，事件地址，事件内容
    internal lateinit var btnBottomOk: Button
    internal  var photoBoxGroup: TZPhotoBoxGroup? = null
    internal lateinit var ivMap: ImageView
    internal var gregorianCalendar = GregorianCalendar()
    internal var mapLocation = 0
    internal var webAPI = HTTPWebAPI()
    private var items: MutableList<ArrayAdapterItem>? = null

    internal lateinit var view: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.fragment_event_case, container, false)

        initData()
        init()
        initViewNew()

        return view
    }

    private fun initData() {
        items = ArrayList<ArrayAdapterItem>()
        items!!.add(ArrayAdapterItem(1, 0, "选项1", "描述1"))
        items!!.add(ArrayAdapterItem(2, 0, "选项2", "描述2"))
        items!!.add(ArrayAdapterItem(3, 0, "选项3", "描述3"))
        items!!.add(ArrayAdapterItem(4, 0, "选项4", "描述4"))
        items!!.add(ArrayAdapterItem(5, 0, "选项5", "描述5"))
        items!!.add(ArrayAdapterItem(6, 0, "选项6", "描述6"))
        items!!.add(ArrayAdapterItem(7, 0, "选项7", "描述7"))
        items!!.add(ArrayAdapterItem(8, 0, "选项8", "描述8"))
        items!!.add(ArrayAdapterItem(9, 0, "选项9", "描述9"))

    }

    private fun init() {
        llBigKind = view.findViewById(R.id.llBigKinds) as LinearLayout
        llSmallKind = view.findViewById(R.id.llSmallKinds) as LinearLayout
        llReportDate = view.findViewById(R.id.llReportDate) as LinearLayout
        llMap = view.findViewById(R.id.llMap) as LinearLayout

        tvBigKinds = view.findViewById(R.id.tvBigKinds) as TextView
        tvSmallKinds = view.findViewById(R.id.tvSmallKinds) as TextView
        tvReportDate = view.findViewById(R.id.tvReportDate) as TextView
        tvEventLocation = view.findViewById(R.id.tvEventLocation) as TextView

        etContactName = view.findViewById(R.id.etContactName) as EditText
        etContactMobile = view.findViewById(R.id.etContactMobile) as EditText
        etEventTitle = view.findViewById(R.id.etEventTitle) as EditText
        etEventAddress = view.findViewById(R.id.etEventAddress) as EditText
        etReportContent = view.findViewById(R.id.etReportContent) as EditText

        btnBottomOk = view.findViewById(R.id.btnBottomOk) as Button
        ivMap = view.findViewById(R.id.ivMap) as ImageView
        photoBoxGroup = view.findViewById(R.id.pbGroup) as TZPhotoBoxGroup

        val dialog = ItemSelectedBottomSheetDialog(this.activity, items)
        dialog.setCallBack { item -> tvBigKinds.text = item.`val` }
        tvBigKinds.setOnClickListener { dialog.show() }
    }

    /**
     * 初始化
     */
    private fun initViewNew() {
        mapLocation = 0
        llReportDate.setOnClickListener { v -> }
        btnBottomOk.setOnClickListener { v -> }
        gregorianCalendar.timeInMillis = System.currentTimeMillis()
        llMap.setOnClickListener { v ->

        }
    }

    /**
     * 检验填写内容的正确性

     * @return true
     */
    private fun checkCase(): Boolean {
        if (tvBigKinds.text.toString() == "大类") {
            showToast("请选择问题大类")
            return false
        }
        if (tvSmallKinds.text.toString() == "小类") {
            showToast("请选择问题小类")
            return false
        }
        if (TextUtils.isEmpty(etEventTitle.text.toString())) {
            showToast("请输入标题")
            return false
        }
        if (TextUtils.isEmpty(etEventAddress.text.toString())) {
            showToast("请输入地址")
            return false
        }
        if (TextUtils.isEmpty(etReportContent.text.toString())) {
            showToast("请输入内容")
            return false
        }
        if (tvEventLocation.text.toString() == "0.0") {
            showToast("请点击地图标记并选择位置")
            return false
        }
        return true
    }

    /**
     * 提示框

     * @param text 弹出的信息提示
     */
    fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * 提示框

     * @param resID 资源ID
     */
    fun showToast(resID: Int) {
        showToast(activity.getString(resID))
    }


    /*
     照片返回
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (photoBoxGroup == null) {
            return
        }
        photoBoxGroup!!.onActivityResult(this.activity, requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}
