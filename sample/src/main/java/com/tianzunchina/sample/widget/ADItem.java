package com.tianzunchina.sample.widget;

/**
 * admin
 * 2016/7/7 0007.
 */

public class ADItem {
    private String title;
    private String picUrl;
    private int res;

    public ADItem(String title, String picUrl) {
        this.picUrl = picUrl;
        this.title = title;
    }

    public ADItem(String title, int res) {
        this.res = res;
        this.title = title;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
