package com.tianzunchina.sample.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */

class IpCaseFragment1 : Fragment() {

    internal var view: View = null!!

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.fragemnt_ip_case, container, false)

        return view
    }
}