package com.tianzunchina.sample.home;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HomeItem {
    private String title;
    private Class<?> fragment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getFragment() {
        return fragment;
    }

    public void setFragment(Class<?> fragment) {
        this.fragment = fragment;
    }
}