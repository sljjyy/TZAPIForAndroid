package com.tianzunchina.android.api.view.recycler;


import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tianzunchina.android.api.utils.image.CircleTransform;

import java.io.File;


/**
 * 列表item缓存类
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public class TZRecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;
    private int mLayoutId;
    private ViewDataBinding binding;

    public TZRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public TZRecyclerViewHolder(Context context, ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent,
                false));
        mContext = context;
        mLayoutId = layoutId;
        this.mViews = new SparseArray<View>();
        itemView.setTag(this);
    }

    public static TZRecyclerViewHolder get(Context context, View convertView,
                                           ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new TZRecyclerViewHolder(context, parent, layoutId);
        } else {
            return (TZRecyclerViewHolder) convertView.getTag();
        }
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return itemView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public TZRecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public TZRecyclerViewHolder setText(int viewId, SpannableString text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public TZRecyclerViewHolder setImage(int id, File file, String url, @DrawableRes int defaultResID, int widht, int height){
        ImageView imageView = getView(id);
        if (file != null && file.exists()){
            Picasso.with(mContext).load(file).resize(widht, height).error(defaultResID).into(imageView);
        } else {
            Picasso.with(mContext).load(url).resize(widht, height).error(defaultResID).into(imageView);
        }

        return this;
    }

    public TZRecyclerViewHolder setImage(int id, String url, @DrawableRes int defaultResID, int widht, int height){
        ImageView imageView = getView(id);
        Picasso.with(mContext).load(url).resize(widht, height).error(defaultResID).into(imageView);
        return this;
    }

    public TZRecyclerViewHolder setImage(int id, String url, @DrawableRes int defaultResID){
        ImageView imageView = getView(id);
        Picasso.with(mContext).load(url).error(defaultResID).into(imageView);
        return this;
    }

    public TZRecyclerViewHolder setCircleImage(int id, String url, @DrawableRes int defaultResID){
        ImageView imageView = getView(id);
        Picasso.with(mContext).load(url).error(defaultResID).transform(new CircleTransform()).into(imageView);
        return this;
    }


    public TZRecyclerViewHolder setImage(int id, @DrawableRes int resId, @DrawableRes int defaultResID){
        ImageView imageView = getView(id);
        Picasso.with(mContext).load(resId).error(defaultResID).into(imageView);
        return this;
    }

    public TZRecyclerViewHolder setImage(int id, File file, @DrawableRes int defaultResID){
        ImageView imageView = getView(id);
        Picasso.with(mContext).load(file).error(defaultResID).into(imageView);
        return this;
    }

    public TZRecyclerViewHolder setImageResource(int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public TZRecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public TZRecyclerViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public TZRecyclerViewHolder setBackgroundColor(int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public TZRecyclerViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public TZRecyclerViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public TZRecyclerViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public TZRecyclerViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public TZRecyclerViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public TZRecyclerViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public TZRecyclerViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public TZRecyclerViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public TZRecyclerViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public TZRecyclerViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public TZRecyclerViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public TZRecyclerViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public TZRecyclerViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public TZRecyclerViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public TZRecyclerViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }



    public TZRecyclerViewHolder setOnTouchListener(int viewId,
                                                   View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public TZRecyclerViewHolder setOnLongClickListener(int viewId,
                                                       View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public TZRecyclerViewHolder setOnClickListener(int viewId,
                                                   View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public TZRecyclerViewHolder setStarNumber(int viewId, int number){
        RatingBar v = getView(viewId);
        v.setRating(number);
        return this;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}