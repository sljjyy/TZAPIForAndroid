package com.tianzunchina.android.api.widget.form;

import java.util.ArrayList;
import java.util.List;

/**
 * admin
 * 2016/6/13 0013.
 */

public class FormTable {
    private String title;
    private int colorIDContent;
    private int textSize;
    private List<FormItem> items = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColorIDContent() {
        return colorIDContent;
    }

    public void setColorIDContent(int colorIDContent) {
        this.colorIDContent = colorIDContent;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public List<FormItem> getItems() {
        return items;
    }

    public void setItems(List<FormItem> items) {
        this.items = items;
    }
}
