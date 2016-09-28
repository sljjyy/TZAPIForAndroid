package com.tianzunchina.android.api.widget.form;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;
import com.tianzunchina.android.api.widget.form.select.ItemSelectedBottomSheetDialog;
import com.tianzunchina.android.api.widget.form.select.ItemSelectedCallBack;

import java.util.regex.Pattern;

/**
 * admin
 * 2016/6/8 0008.
 */

public class SelectFormItemTextView extends FormItemView implements View.OnClickListener, ItemSelectedCallBack {
    public Pattern regex ; //正则
    public int line = 1;
    public TextInputLayout tilContent;
    public TextInputEditText etContent;
    private ArrayAdapterItem item;
    private ItemSelectedBottomSheetDialog dialog;


    public SelectFormItemTextView(Context context, FormTable table, FormItem item) {
        super(context, item);
        View view = LayoutInflater.from(context).inflate(R.layout.view_form_text, this, true);
        tilContent = (TextInputLayout) view.findViewById(R.id.tilContent);
        etContent = (TextInputEditText) view.findViewById(R.id.etContent);
        init();
    }

    public void init(){
        tilContent.setOnClickListener(this);
        etContent.setOnClickListener(this);
        tilContent.setHint(formItem.getTitle());
        etContent.setLines(line);
        etContent.setKeyListener(null);

    }

    @Override
    String getValue() {
        return item.getVal();
    }

    @Override
    int getValueInt() {
        if(item == null){
            return 0;
        }
        return item.getIntID();
    }

    @Override
    boolean isEnable() {
        if(item == null){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        //TODO  当前支持库如果每次重新new 在将dialog拖到底部后 下次show只能看到半透明的黑屏
        dialog = new ItemSelectedBottomSheetDialog(getContext(), formItem.getItems());
        dialog.setCallBack(this);
        dialog.show();
    }

    @Override
    public void select(ArrayAdapterItem item) {
        this.item = item;
        etContent.setText(getValue());
    }
}
