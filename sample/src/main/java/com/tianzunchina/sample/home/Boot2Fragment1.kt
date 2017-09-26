package com.tianzunchina.sample.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tianzunchina.android.api.base.TZFragment
import com.tianzunchina.sample.MainActivity1
import com.tianzunchina.sample.R
import com.tianzunchina.sample.util.BootConfig1

/**
 * Created by Administrator on 2017/5/26.
 */
class Boot2Fragment1(private val bgID: Int, private val phonePicID: Int, private val titleID: Int,
                    private val isLast: Boolean) : TZFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = View.inflate(activity, R.layout.fragment_boot2, null)
        val ivTitle = view.findViewById(R.id.ivTitle) as ImageView
        val ivPic = view.findViewById(R.id.ivPic) as ImageView
        val tvClose = view.findViewById(R.id.tvClose) as TextView
        view.setBackgroundResource(bgID)
        ivPic.setImageResource(phonePicID)
        ivTitle.setImageResource(titleID)
        if (!isLast) {
            return view
        }
        tvClose.visibility = View.VISIBLE
        tvClose.setOnClickListener { v ->
            val bc = BootConfig1()
            bc.boot()
            val intent = Intent(activity, MainActivity1::class.java)
            startActivity(intent)
        }
        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_ERR) {
            return
        }

    }

    companion object {

        private val REQUEST_OK = 100
        private val RESULT_ERR = -1
    }
}