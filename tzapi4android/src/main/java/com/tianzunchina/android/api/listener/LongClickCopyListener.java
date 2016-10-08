package com.tianzunchina.android.api.listener;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.view.View;

import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZToastTool;

/**
 * 长按复制文本框内容
 * CraetTime 2014-10-11
 * @author SunLiang
 *
 */
public class LongClickCopyListener implements View.OnLongClickListener {
    private String info;

    /**
     * 构造方法
     * @param info 复制成功后的提示
     */
    public LongClickCopyListener(String info){
        this.info = info;
    }

    public LongClickCopyListener(){
        this.info = "复制成功";
    }

    /**
     * 重写长按事件
     * 将tv中的内容复制到剪贴板
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
         /*
		 * API11之前 用android.text.ClipboardManager
		 * API11之后用android.content.ClipboardManager
		 * 所以此处会标记过时
		 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager cmb = (ClipboardManager) TZApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(ClipData.newPlainText(null, v.toString()));
        } else {
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) TZApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(v.toString());
        }

        TZToastTool.essential(info);
        return false;
    }
}
