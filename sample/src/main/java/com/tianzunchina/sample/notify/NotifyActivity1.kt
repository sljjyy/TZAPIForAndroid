package com.tianzunchina.sample.notify

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.android.api.log.TZToastTool
import com.tianzunchina.android.api.widget.notification.TZNotification
import com.tianzunchina.sample.MainActivity1
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */
class NotifyActivity1 : TZAppCompatActivity() {

    private var tzNotification: TZNotification? = null
    private var resultIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)

        resultIntent = Intent(this, MainActivity1::class.java)
    }

    fun OnClick(v: View) {
        when (v.id) {
            R.id.btnNormalNotify -> {
                tzNotification = TZNotification(this, "普通标题", "这是一条普通通知栏", "您有一条新消息！", R.mipmap.ic_launcher, resultIntent)
                tzNotification!!.showNotify(100)
            }
            R.id.btnResidentNotify -> {
                tzNotification = TZNotification(this, "常驻标题", "这是一条常驻通知栏", "您有一条新消息！", R.mipmap.ic_launcher, resultIntent)
                tzNotification!!.showNotify(200, Notification.FLAG_ONGOING_EVENT)
            }
            R.id.btnProgressNotify -> {
                tzNotification = TZNotification(this, "正在下载中...", "", "开始下载", R.mipmap.ic_launcher, resultIntent)
                for (i in 0..100) {
                    tzNotification!!.showNotify(300, i, 100, String.format("已下载%d%s", i, "%"), object : TZNotification.DownLoadListener {
                        override fun success() {
                            TZToastTool.essential("已经下载完成咯！")
                        }

                        override fun fail() {}
                    })
                }
            }
            R.id.btnClearAllNotify -> {
                tzNotification = TZNotification(this)
                tzNotification!!.cancleAll()
            }
        }
    }
}
