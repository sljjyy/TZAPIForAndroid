package com.tianzunchina.sample

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * Created by Administrator on 2017/5/25.
 */
class AllTitleFrg1 : Fragment() {
    internal var llBack: LinearLayout = null!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.title_bar, container, false)
        llBack = view.findViewById(R.id.llBack) as LinearLayout
        llBack.setOnClickListener { v -> activity.finish() }
        return view
    }

}