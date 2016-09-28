package com.tianzunchina.android.api.widget.form;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianzunchina.android.api.R;

import java.util.Map;

/**
 * admin
 * 2016/6/8 0008.
 */

abstract class FormItemView extends LinearLayout{
    FormItem formItem;

    public FormItemView(Context context) {
        super(context);
    }

    public FormItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FormItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FormItemView(Context context, FormItem formItem) {
        super(context);
        this.formItem = formItem;
    }

    abstract String getValue();
    abstract int getValueInt();
    abstract boolean isEnable();
}
