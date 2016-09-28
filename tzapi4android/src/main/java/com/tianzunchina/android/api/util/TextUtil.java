package com.tianzunchina.android.api.util;

import android.widget.TextView;

/**
 * Created by admin on 2016/3/16 0016.
 */
public class TextUtil {
    public static boolean isNull(TextView et) {
        String text = et.getText().toString().trim();
        return text.equals("");
    }
}