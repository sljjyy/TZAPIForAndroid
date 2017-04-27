package com.tianzunchina.sample.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.tianzunchina.sample.R;

/**
 * admin
 * 2016/7/7 0007.
 */

public class ADImageBanner extends BaseIndicatorBanner<ADItem, ADImageBanner> {
    private ColorDrawable colorDrawable;

    public ADImageBanner(Context context) {
        this(context, null, 0);
    }

    public ADImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ADImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final ADItem item = mDatas.get(position);
        tv.setText(item.getTitle());
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(getContext(), R.layout.item_ad, null);
        ImageView iv = (ImageView) inflate.findViewById(R.id.imageView);

        final ADItem item = mDatas.get(position);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String imgUrl = item.getPicUrl();
        int res = item.getRes();

        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(getContext())
                    .load(imgUrl)
                    .placeholder(colorDrawable)
                    .into(iv);
        } else {
            if(res != -1){
                iv.setImageResource(res);
               /* Glide.with(getContext())
                        .load(res)
                        .placeholder(colorDrawable)
                        .into(iv);*/
            }else {
                iv.setImageDrawable(colorDrawable);
            }
        }

        return inflate;
    }
}
