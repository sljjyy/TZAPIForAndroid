package com.tianzunchina.android.api.widget.form;

import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * admin
 * 2016/6/12 0012.
 */

public class FormItem {
    private String title; //标题
    private FormItemType type = FormItemType.EDIT;
    private Map<String, Object> params = new HashMap<>();
    private List<ArrayAdapterItem> items = new ArrayList<>();

    public FormItem(){}

    public FormItem(String title) {
        this.title = title;
    }

    /**
     * 单选
     * @param title
     * @param items
     */
    public FormItem(String title, List<ArrayAdapterItem> items) {
        this.title = title;
        this.items = items;
        type = FormItemType.SINGLE_SELECT;
    }

    public FormItem(String title, FormItemType type) {
        this.title = title;
        this.type = type;
    }

    public FormItem(String title, FormItemType type, Map<String, Object> params) {
        this.title = title;
        this.type = type;
        this.params = params;
    }

    public List<ArrayAdapterItem> getItems() {
        return items;
    }

    public void setItems(List<ArrayAdapterItem> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FormItemType getType() {
        return type;
    }

    public void setType(FormItemType type) {
        this.type = type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "FormItem{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", params=" + params +
                ", items=" + items +
                '}';
    }
}
