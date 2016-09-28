package com.tianzunchina.android.api.widget.photo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.view.recycler.RecyclerItemClickListener;
import com.tianzunchina.android.api.view.recycler.TZRecyclerViewAdapter;
import com.tianzunchina.android.api.view.recycler.TZRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 照片展示盒组
 * 类似微博消息图片展示
 * 2016/7/22 0022.
 */

public class TZPhotoDisplayBoxGroup extends RecyclerView {
    private int widht,height = 100;
    private TZRecyclerViewAdapter adapter;
    private List<String> urls = new ArrayList<>();

    public TZPhotoDisplayBoxGroup(Context context) {
        super(context);
        init();
    }

    public TZPhotoDisplayBoxGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TZPhotoDisplayBoxGroup(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        setLayoutManager(gridLayoutManager);
        initAdapter();
        initClickListener();
    }

    public void addURLs(String url){
        urls.add(url);
    }

    public void addAllURLs(ArrayList<String> urls){
        this.urls.addAll(urls);
    }

    public void setURLs(ArrayList<String> urls){
        if(urls == null){
            return;
        }
        this.urls.clear();
        this.urls.addAll(urls);
    }

    private void initClickListener(){
        this.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), PreviewActivity.class);
                intent.putExtra("imageUrl", urls.get(position));
                getContext().startActivity(intent);
            }
        }));
    }

    private void initAdapter(){
        adapter =  new TZRecyclerViewAdapter<String>(getContext(), urls, R.layout.item_photo_box) {
            @Override
            public void convert(TZRecyclerViewHolder holder, String url, int position) {
                holder.setImage(R.id.ivPhoto, url, R.mipmap.pic_loading, widht, height);
            }
        };
        this.setAdapter(adapter);
    }
}