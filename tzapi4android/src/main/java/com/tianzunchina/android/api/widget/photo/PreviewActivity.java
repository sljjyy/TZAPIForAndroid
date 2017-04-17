package com.tianzunchina.android.api.widget.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.widget.gesture.GestureImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/5/25.
 */
public class PreviewActivity extends AppCompatActivity {
    public static final String KEY_PATH = "path";
    public static final String KEY_URL = "imageUrl";
    public static final String KEY_PATHS = "paths";
    public static final String KEY_URLS = "imageUrls";
    public static final String KEY_INDEX = "index";

    private List<View> listViews = new ArrayList<>();
    private LinearLayout llPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        llPhoto = (LinearLayout) findViewById(R.id.llPhoto);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init(){
        Intent intent = getIntent();
        if(intent.hasExtra(KEY_PATH) || intent.hasExtra(KEY_URL)) {
            singlePhoto(intent);
            return;
        }
        int index = intent.getIntExtra(KEY_INDEX, 0);
        multiplePhoto(intent, index);
    }

    private void singlePhoto(Intent intent){
        GestureImageView imageView = getImageView();
        String path = intent.getStringExtra(KEY_PATH);
        if(path == null){
            String url = intent.getStringExtra(KEY_URL);
            Glide.with(this).load(url).error(R.mipmap.pic_loading).override(800, 600).into(imageView);
        } else {
            File file = new File(path);
            Glide.with(this).load(file).error(R.mipmap.pic_loading).override(800, 600).into(imageView);
        }
        llPhoto.addView(imageView);

    }

    private void multiplePhoto(Intent intent, int index){
        ViewPager viewPager = new ViewPager(this);
        listViews = new ArrayList<>();
        String[] paths = intent.getStringArrayExtra(KEY_PATHS);
        if(paths == null){
            String[] urls = intent.getStringArrayExtra(KEY_URLS);
            listViews = loadURLs(urls);
        } else {
            listViews = loadPaths(paths);
        }
        if(listViews.size() >= index){
            index = 0;
        }
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);//定义布局管理器的参数
        viewPager.setAdapter(new ViewPageViewAdapter(listViews));
        viewPager.setCurrentItem(index);
        llPhoto.addView(viewPager, param);
    }

    private List<View> loadPaths(String[] paths){
        List<View> views = new ArrayList<>();
        for(int i = 0; i < paths.length; i++){
            final GestureImageView imageView = getImageView();
            final LinearLayout linearLayout = getLinearLayout();
            File file = new File(paths[i]);
            Glide.with(this).load(file).asBitmap().error(R.mipmap.pic_loading).override(800, 600).into(new BitmapImageViewTarget(imageView){
                @Override
                protected void setResource(final Bitmap resource) {
                    super.setResource(resource);
                    Palette.generateAsync(resource, new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch swatch = palette.getSwatches().get(0);
                            if (swatch != null) {
                                linearLayout.setBackgroundColor(swatch.getRgb());
                                swatch.getPopulation();
                            }
                        }
                    });
                }
            });
            imageView.setLayoutParams(getLayoutParams());//配置文本显示组件的参数
            linearLayout.addView(imageView);
            views.add(linearLayout);
        }
        return views;
    }

    private List<View> loadURLs(String[] url){
        List<View> views = new ArrayList<>();
        for(int i = 0; i < url.length; i++){
            final GestureImageView imageView = getImageView();
            final LinearLayout linearLayout = getLinearLayout();
            Glide.with(this).load(url[i]).asBitmap().error(R.mipmap.pic_loading).override(800, 600).into(new BitmapImageViewTarget(imageView){
                @Override
                protected void setResource(final Bitmap resource) {
                    super.setResource(resource);
                    Palette.generateAsync(resource, new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch swatch = palette.getLightMutedSwatch();
                            if (swatch != null) {
                                imageView.setBackgroundColor(swatch.getRgb());
                                swatch.getPopulation();
                            }
                        }
                    });
                }
            });
            imageView.setLayoutParams(getLayoutParams());//配置文本显示组件的参数
            linearLayout.addView(imageView);

            views.add(linearLayout);
        }
        return views;
    }

    private GestureImageView getImageView(){
        GestureImageView imageView = new GestureImageView(this);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }

    private LinearLayout getLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);//所有组件垂直摆放
        linearLayout.setGravity(Gravity.CENTER);
        return linearLayout;
    }

    private LinearLayout.LayoutParams getLayoutParams(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//定义文本显示组件

        return params;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

    private LinearLayout getTitleLinearLayout() {
        // 创建单元格（LinearLayout）
        LinearLayout.LayoutParams layoutWrapper = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        getLinearLayout().addView(linearLayout, layoutWrapper);

        // 返回（TextView）
        LinearLayout.LayoutParams layoutBack = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutBack.setMargins(20, 0, 20, 0);
        layoutBack.gravity= Gravity.LEFT;
        TextView tvBack = new TextView(this);
        tvBack.setText("返回");
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvBack.setTextSize(20);
        linearLayout.addView(tvBack, layoutBack);

        // 标题（TextView）
        LinearLayout.LayoutParams layoutTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutTitle.setMargins(20, 0, 20, 0);
        layoutTitle.gravity = Gravity.CENTER;
        TextView tvTitle = new TextView(this);
        tvTitle.setText("预览照片");
        linearLayout.addView(tvTitle, layoutTitle);

        return  linearLayout;
    }

}