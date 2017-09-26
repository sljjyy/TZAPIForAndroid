package com.tianzunchina.sample.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.flyco.banner.widget.Banner.BaseIndicatorBanner
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */
class ADImageBanner1 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : BaseIndicatorBanner<ADItem1, ADImageBanner1>(context, attrs, defStyle) {
    private val colorDrawable: ColorDrawable

    init {
        colorDrawable = ColorDrawable(Color.parseColor("#555555"))
    }

    override fun onTitleSlect(tv: TextView?, position: Int) {
        val item = mDatas[position]
        tv!!.text = item.title
    }

    override fun onCreateItemView(position: Int): View {
        val inflate = View.inflate(context, R.layout.item_ad, null)
        val iv = inflate.findViewById(R.id.imageView) as ImageView

        val item = mDatas[position]
        iv.scaleType = ImageView.ScaleType.CENTER_CROP

        val imgUrl = item.picUrl
        val res = item.res

        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context)
                    .load(imgUrl)
                    .placeholder(colorDrawable)
                    .into(iv)
        } else {
            if (res != -1) {
                iv.setImageResource(res)
                /* Glide.with(getContext())
                        .load(res)
                        .placeholder(colorDrawable)
                        .into(iv);*/
            } else {
                iv.setImageDrawable(colorDrawable)
            }
        }

        return inflate
    }
}
