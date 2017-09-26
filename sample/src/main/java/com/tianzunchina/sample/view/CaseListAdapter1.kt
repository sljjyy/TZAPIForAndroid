package com.tianzunchina.sample.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tianzunchina.sample.R
import com.tianzunchina.sample.model.CaseParent1
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Administrator on 2017/5/31.
 */
class CaseListAdapter1(internal var context: Context, internal var lists: ArrayList<CaseParent1>?, internal var status_show: Int) : BaseAdapter() {
    internal var dish = 0f
    internal var disW = 0f

    init {
        dish = Dp2Px(context, 50f).toFloat()
        disW = Dp2Px(context, 60f).toFloat()

    }

    private fun Dp2Px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun getCount(): Int {
        return lists!!.size
    }

    override fun getItem(i: Int): Any {
        return lists!![i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: Holder
        if (view == null) {
            view = View.inflate(context, R.layout.item_case_list, null)
            holder = Holder()
            holder.tvTitle = view!!.findViewById(R.id.id_tv_case_list_item_title) as TextView
            holder.tvDate = view.findViewById(R.id.id_tv_case_list_item_date) as TextView
            holder.iv = view.findViewById(R.id.id_iv_case_list_item_pic) as ImageView
            holder.ivHot = view.findViewById(R.id.id_iv_case_list_item_new) as ImageView
            holder.tvStatus = view.findViewById(R.id.id_tv_case_list_item_address) as TextView
            view.tag = holder
        } else {
            holder = view.tag as Holder
        }
        if (lists != null && !lists!!.isEmpty()) {
            val caseParent = lists!![position]
            holder.tvTitle!!.text = caseParent.title
            holder.tvDate!!.text = caseParent.updateTime
            holder.tvStatus!!.text = caseParent.address
            val updateTime = getDateOfSrc(caseParent.updateTime!!)
            val currentTime = Date()
            if (position <= 3 && getNumIncludeTwoHours(currentTime, updateTime) < 5) {
                holder.ivHot!!.visibility = View.VISIBLE
            } else {
                holder.ivHot!!.visibility = View.GONE
            }
            if (caseParent.picture1 != null) {
                val url = caseParent.picture1
                Glide.with(context).load(url).override(100, 100).into(holder.iv!!)
            } else {
                holder.iv!!.setImageResource(R.drawable.img_holder)
            }


        }
        return view
    }

    internal inner class Holder {
        var tvTitle: TextView? = null
        var tvDate: TextView? = null
        var iv: ImageView? = null
        var ivHot: ImageView? = null
        var tvStatus: TextView? = null
    }

    fun getDateOfSrc(src: String): Date {
        val formatter = SimpleDateFormat(DEF_DATE_FORMAT)
        var date: Date? = null
        try {
            date = formatter.parse(src)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return date!!
    }

    fun getNumIncludeTwoHours(date1: Date, date2: Date): Int {
        try {
            val g1 = GregorianCalendar()
            val g2 = GregorianCalendar()
            g1.time = date1
            g2.time = date2
            return getHourInclude(g1, g2)
        } catch (e: Exception) {
            return 0
        }

    }

    private fun getHourInclude(g1: GregorianCalendar, g2: GregorianCalendar): Int {
        return getHours(g1, g2) + 1
    }

    private fun getHours(g1: GregorianCalendar, g2: GregorianCalendar): Int {
        var elapsed = 0
        val gc1: GregorianCalendar
        val gc2: GregorianCalendar

        if (g2.after(g1)) {
            gc2 = g2.clone() as GregorianCalendar
            gc1 = g1.clone() as GregorianCalendar
        } else {
            gc2 = g1.clone() as GregorianCalendar
            gc1 = g2.clone() as GregorianCalendar
        }
        //gc1.clear(Calendar.DAY_OF_MONTH);
        gc1.clear(Calendar.MILLISECOND)
        gc1.clear(Calendar.SECOND)
        gc1.clear(Calendar.MINUTE)
        //gc1.clear(Calendar.HOUR_OF_DAY);

        // gc2.clear(Calendar.DAY_OF_MONTH);
        gc2.clear(Calendar.MILLISECOND)
        gc2.clear(Calendar.SECOND)
        gc2.clear(Calendar.MINUTE)
        // gc2.clear(Calendar.HOUR_OF_DAY);

        while (gc1.before(gc2)) {
            gc1.add(Calendar.HOUR_OF_DAY, 1)
            elapsed++
        }
        return elapsed
    }

    companion object {
        val DEF_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }
}
