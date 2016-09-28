package com.tianzunchina.android.api.widget.form;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tianzunchina.android.api.R;

import java.util.regex.Pattern;

/**
 * admin
 * 2016/6/8 0008.
 */

public class FormItemEditView extends FormItemView{
    public String errStr; //不符合正则的错误提示
    public Pattern regex ; //正则
    public int line = 1;

    public TextInputLayout tilContent;
    public EditText etContent;

    public static final String KEY_REGEX = "regex";
    public static final String KEY_ERR = "err";
    public static final String KEY_LINE = "line";

    public FormItemEditView(Context context, FormTable table, FormItem item) {
        super(context, item);
        View view = LayoutInflater.from(context).inflate(R.layout.view_form_edit_text, this, true);
        tilContent = (TextInputLayout) view.findViewById(R.id.tilContent);
        etContent = (EditText) view.findViewById(R.id.etContent);
        tilContent.setHint(formItem.getTitle());
        tilContent.setError(errStr);
        etContent.setLines(line);
    }

    public void init(){
        String regexStr = (String) formItem.getParams().get(KEY_REGEX);
        regex = Pattern.compile(regexStr);
        errStr = (String) formItem.getParams().get(KEY_ERR);
        line = (Integer) formItem.getParams().get(KEY_LINE);
    }

    @Override
    String getValue() {
        return etContent.getText().toString();
    }

    @Override
    int getValueInt() {
        return 0;
    }

    @Override
    boolean isEnable() {
        String str = etContent.getText().toString();
        if(regex.matcher(str).matches()){
            return true;
        }
        tilContent.setErrorEnabled(true);
        return false;
    }
}
