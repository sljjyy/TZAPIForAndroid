package com.tianzunchina.sample.home

import android.content.Context
import com.tianzunchina.android.api.view.list.TZCommonAdapter
import com.tianzunchina.android.api.view.list.TZViewHolder
import com.tianzunchina.sample.R
import com.tianzunchina.sample.model.Circle

/**
 * Created by Administrator on 2017/5/26.
 */
class CircleListAdapter1(context: Context, datas: List<Circle>, layoutId: Int, private val isRank: Boolean) : TZCommonAdapter<Circle>(context, datas, layoutId) {
    private var countDetail = "圈友$1名，活动$2次"
    private val rankCountDetail = "圈友$1名，活动$2次,排名第$3"

    init {
        if (isRank) {
            countDetail = rankCountDetail
        }
    }

    override fun convert(holder: TZViewHolder, circle: Circle, position: Int) {
        holder.setCircleImage(R.id.image, "http://218.108.93.154:8090/ImgHandler.ashx?Path=" + circle.smallPath, R.drawable.ico_loading_photo)
        holder.setText(R.id.tvName, circle.title)
        holder.setText(R.id.tvDescription, countDetail
                .replace("$1", circle.circleMemCount)
                .replace("$2", circle.actCount)
                .replace("$3", (position + 1).toString()))
        holder.setText(R.id.state, circle.isApplyStr)
        holder.setVisible(R.id.divider, !isRank)
    }


}
