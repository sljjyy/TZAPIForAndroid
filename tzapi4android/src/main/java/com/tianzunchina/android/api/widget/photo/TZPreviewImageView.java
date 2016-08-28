package com.tianzunchina.android.api.widget.photo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * admin
 * 2016/7/22 0022.
 */

public class TZPreviewImageView extends ImageView{
    private String url;

    public TZPreviewImageView(Context context) {
        super(context);
        init();
    }

    public TZPreviewImageView(Context context, String url) {
        super(context);
        this.url = url;
        setImage(url);
        init();
    }

    public TZPreviewImageView(Context context, File file) {
        super(context);
        setImage(file);
        init();
    }

    public TZPreviewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TZPreviewImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
    }

    public void setImage(File file){
        Picasso.with(getContext()).load(file).into(this);
    }

    public void setImage(String url){
        Picasso.with(getContext()).load(url).into(this);
    }
}
