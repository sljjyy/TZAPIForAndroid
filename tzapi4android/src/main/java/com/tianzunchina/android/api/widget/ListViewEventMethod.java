package com.tianzunchina.android.api.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewEventMethod extends ListView {

    private void init(){
        this.setFocusable(false);
    }

	public ListViewEventMethod(Context context) {
		super(context);
        init();
	}
	

	public ListViewEventMethod(Context context, AttributeSet attrs) { 
        super(context, attrs);
        init();
    }
    public ListViewEventMethod(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle);
        init();
    }
  
	/** 
	 * 设置不滚动 
	 */  
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
