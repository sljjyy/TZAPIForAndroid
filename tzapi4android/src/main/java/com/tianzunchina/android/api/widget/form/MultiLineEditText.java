package com.tianzunchina.android.api.widget.form;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.tianzunchina.android.api.listener.LongClickCopyListener;

public class MultiLineEditText extends EditText {
	
	public MultiLineEditText(Context context) {
		super(context);
		init();
	}
	public MultiLineEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MultiLineEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	public void setInputType(int type) {
		super.setInputType(type);
		setSingleLine(false);
	}

	private void init(){
	}

	public void onlyRead(){
		setInputType(InputType.TYPE_NULL);
		setSingleLine(false);
		setOnLongClickListener(new LongClickCopyListener());
	}

}
