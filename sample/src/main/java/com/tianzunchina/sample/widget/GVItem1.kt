package com.tianzunchina.sample.widget

import java.io.Serializable

/**
 * Created by Administrator on 2017/5/31.
 */
open class GVItem1 : Serializable {
    var id = 1
    var title: String? = ""
    var description = ""
    var attachPath: String? = null
    var resID = -1

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + id
        result = prime * result + if (title == null) 0 else title!!.hashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as GVItem?
        if (id != other!!.id)
            return false
        if (title == null) {
            if (other.title != null)
                return false
        } else if (title != other.title)
            return false
        return true
    }

    override fun toString(): String {
        return "GVItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", attachPath='" + attachPath + '\'' +
                ", resID=" + resID +
                '}'
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
