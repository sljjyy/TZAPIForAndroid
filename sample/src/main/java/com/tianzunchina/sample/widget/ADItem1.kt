package com.tianzunchina.sample.widget

/**
 * Created by Administrator on 2017/5/31.
 */
class ADItem1 {
    var title: String? = null
    var picUrl: String? = null
    var res: Int = 0

    constructor(title: String, picUrl: String) {
        this.picUrl = picUrl
        this.title = title
    }

    constructor(title: String, res: Int) {
        this.res = res
        this.title = title
    }
}
