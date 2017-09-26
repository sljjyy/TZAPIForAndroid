package com.tianzunchina.sample.event

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.android.api.log.TZToastTool
import com.tianzunchina.android.api.network.HTTPWebAPI
import com.tianzunchina.android.api.network.TZRequest
import com.tianzunchina.android.api.network.WebCallBackListener
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem
import com.tianzunchina.android.api.widget.form.select.ItemSelectedBottomSheetDialog
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxOne
import com.tianzunchina.sample.R
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Administrator on 2017/5/25.
 */
class EventActivity1 : TZAppCompatActivity() {
    private var photoBoxGroup: TZPhotoBoxGroup? = null
    private var pbOne: TZPhotoBoxOne? = null
    private var pbOne2: TZPhotoBoxOne? = null
    private var items: MutableList<ArrayAdapterItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        initData()
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        photoBoxGroup = findViewById(R.id.pbg) as TZPhotoBoxGroup
        pbOne = findViewById(R.id.pbOne) as TZPhotoBoxOne
        pbOne2 = findViewById(R.id.pbOne2) as TZPhotoBoxOne
        val etEventContent = findViewById(R.id.etEventContent) as TextView
        val tvSelect = findViewById(R.id.tvSelect) as TextView
        val etEventAddress = findViewById(R.id.etEventAddress) as TextView
        val etEventDes = findViewById(R.id.etEventDes) as TextView
        etEventContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                etEventAddress.text = filter(s.toString())
                etEventDes.text = unFilter(etEventAddress.text.toString())
            }
        })

        val dialog = ItemSelectedBottomSheetDialog(this, items)
        dialog.setCallBack { item -> tvSelect.text = item.`val` }
        tvSelect.setOnClickListener { v -> dialog.show() }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        photoBoxGroup!!.onActivityResult(this, requestCode, resultCode, data)
        pbOne!!.onActivityResult(this, requestCode, resultCode, data)
        pbOne2!!.onActivityResult(this, requestCode, resultCode, data)
    }

    fun submit(view: View) {
        val tzRequest = TZRequest("http://10.80.2.108:8098/api/Login/", "Login")
        tzRequest.addParam("code", 10000)
        tzRequest.addParam("password", "aa1122")
        HTTPWebAPI().call(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: org.json.JSONObject, request: TZRequest) {
                TZToastTool.mark(jsonObject.toString())
            }

            override fun success(response: Any, request: TZRequest) {
                TZToastTool.mark(response.toString())
            }

            override fun err(s: String, tzRequest: TZRequest) {
                TZToastTool.mark(s)
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        photoBoxGroup!!.dispatchTouchEvent(ev, false)
        pbOne!!.dispatchTouchEvent(ev, false)
        pbOne2!!.dispatchTouchEvent(ev, false)
        return super.dispatchTouchEvent(ev)
    }


    fun filter(str: String?): String {
        var str = str
        if (str == null || str.isEmpty()) {
            return ""
        }
        str += " "
        val sb = StringBuffer()
        for (i in 0..str.length - 1 - 1) {
            val ch = str[i].toInt()
            System.err.print(ch)
            if (isNotEmojiCharacter(str[i])) {
                sb.append(ch.toChar())
            } else {
                sb.append(String.format(Locale.CHINA, "%%u%x", ch))
            }
        }
        return sb.toString()
    }

    private fun isNotEmojiCharacter(codePoint: Char): Boolean {
        return codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD
    }


    fun unFilter(str: String?): String {
        if (str == null || str.isEmpty()) {
            return ""
        }
        val sb = StringBuffer()
        sb.append(str)
        val pattern = Pattern.compile("%u[0-9a-fA-F]{4}")
        val matcher = pattern.matcher(str)
        var emojiStr = ""
        while (matcher.find()) {
            emojiStr = matcher.group()
            val index = sb.lastIndexOf(emojiStr)
            val emoji = emojiStr.substring(2)
            val ch = Integer.parseInt(emoji, 16).toChar()
            sb.delete(index, index + 6)
            sb.insert(index, ch)

        }
        return sb.toString()
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
}
