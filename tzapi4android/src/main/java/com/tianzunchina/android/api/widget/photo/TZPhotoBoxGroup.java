package com.tianzunchina.android.api.widget.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.view.recycler.RecyclerItemClickListener;
import com.tianzunchina.android.api.view.recycler.TZRecyclerViewAdapter;
import com.tianzunchina.android.api.view.recycler.TZRecyclerViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * CraetTime 2016-4-6
 * @author SunLiang
 */
public class TZPhotoBoxGroup extends RecyclerView implements PhotoBoxChangeListener{
    private int count = 2;
    private int widht,height = 100;
    private TZRecyclerViewAdapter adapter;
    private boolean isReadyDel = false;
    private List<TZPhotoBox> boxes = new ArrayList<>();

    public TZPhotoBoxGroup(Context context) {
        super(context);
    }

    public TZPhotoBoxGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(context, attrs, 0);
        initAdapter();
    }

    public TZPhotoBoxGroup(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        initAttr(context, attrs, defStyle);
        initAdapter();
    }

    public void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(linearLayoutManager);
        initClickListener();
    }

    public void initLayout(String mode){
        switch (mode){
            case "boxLayoutMode":

        }
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyle){
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TZPhotoBoxGroup, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TZPhotoBoxGroup_boxCount) {
                count = a.getInt(attr, 2);
            } else if(attr == R.styleable.TZPhotoBoxGroup_boxWidth){
                widht = (int)a.getDimension(attr, 100);
            } else if(attr == R.styleable.TZPhotoBoxGroup_boxHeight){
                height = (int)a.getDimension(attr, 100);
            }
        }
        a.recycle();
    }

    private void initClickListener(){
        this.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemLongClick(View view, int position) {
                boxes.get(position).ivPhoto.performLongClick();
            }

            @Override
            public void onItemClick(View view, int position) {
                TZPhotoBox photoBox = boxes.get(position);
                switch (photoBox.mode){
                    case TZPhotoBox.MODE_READY_DELETE:
                        photoBox.ivDel.callOnClick();
                        break;
                    case TZPhotoBox.MODE_ADD:
                    case TZPhotoBox.MODE_BROWSE:
                    case TZPhotoBox.MODE_ONLY_READ:
                        photoBox.ivPhoto.callOnClick();
                        break;
                }
            }
        }));
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data){
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if(type >= 10) type -= 10;
                boxes.get(type).addPhoto(imageFile);
                if (type == 0 && !boxes.get(1).isBrowse()) {
                    boxes.get(1).allow();
                }
            }
        });
    }

    /**
     * 获取所有照片路径的集合
     * @return
     */
    public ArrayList<String> getPaths(){
        ArrayList<String> paths = new ArrayList<>();
        for (TZPhotoBox box : boxes){
            if (box.mode == TZPhotoBox.MODE_BROWSE || box.mode == TZPhotoBox.MODE_ONLY_READ){
                paths.add(box.getFileImage().getAbsolutePath());
            }
        }
        return paths;
    }

    public void onlyRead(){
        for(TZPhotoBox box : boxes){
            box.onlyRead();
        }
    }

    /**
     * 获取指定索引下的图片路径
     * @param index
     * @return
     */
    public String getPath(int index){
        if(index < boxes.size()){
            return boxes.get(index).getFileImage().getAbsolutePath();
        }
        return null;
    }

    /**
     * 判断是否为待删除状态
     * @return
     */
    public boolean isReadyDelete(){
        return isReadyDel;
    }

    /**
     * 取消删除状态
     */
    public void cancelDelete(){
        for(TZPhotoBox box : boxes){
            box.cancelDelete();
        }
    }

    private void initAdapter(){
        for (int i = 0; i < count; i++) {
            TZPhotoBoxView view = new TZPhotoBoxView(getContext());
            TZPhotoBox photoBox = new TZPhotoBox(getContext(),view, i);
            if(i == 0){
                photoBox.allow();
            } else {
                photoBox.invalid();
            }
            photoBox.setPhotoBoxChangeListener(this);
            boxes.add(photoBox);
        }
        adapter =  new TZRecyclerViewAdapter<TZPhotoBox>(getContext(), boxes, R.layout.item_photo_box) {
            @Override
            public void convert(TZRecyclerViewHolder holder, TZPhotoBox pb, int position) {
                switch (pb.mode){
                    case TZPhotoBox.MODE_READY_DELETE:
                        holder.setImage(R.id.ivPhoto, pb.fileImage, pb.url, R.mipmap.pic_loading, widht, height);
                        holder.setVisible(R.id.ivDel, true);
                        break;
                    case TZPhotoBox.MODE_ADD:
                        holder.setImage(R.id.ivPhoto, R.mipmap.ico_add_photo, R.mipmap.ico_add_photo);
                        holder.setVisible(R.id.ivDel, false);
                        break;
                    case TZPhotoBox.MODE_NULL:
                    case TZPhotoBox.MODE_ONLY_READ:
                    case TZPhotoBox.MODE_BROWSE:
                    default:
                        holder.setImage(R.id.ivPhoto, pb.fileImage, pb.url, R.mipmap.pic_loading, widht, height);
                        holder.setVisible(R.id.ivDel, false);
                        break;
                }
            }
        };
        this.setAdapter(adapter);
    }

    @Override
    public void change(int index, int mode) {
        adapter.notifyItemChanged(index);
        if(index >= boxes.size() || mode > TZPhotoBox.MODE_BROWSE){
            return;
        }
        switch (mode){
            case TZPhotoBox.MODE_READY_DELETE:
                isReadyDel = true;
                break;
            case TZPhotoBox.MODE_ADD:
                deleted(index);
                isReadyDel = false;
                break;
            case TZPhotoBox.MODE_BROWSE:
                added(index);
                isReadyDel = false;
                break;
        }
        adapter.notifyItemChanged(index + 1);
    }

    /**
     * 该位置添加完图片后 如果下个没有图片就将其设置为待添加
     * @param index
     */
    private void added(int index){
        if(!isBrowse4next(index)) return;
        TZPhotoBox photoBox = boxes.get(index + 1);
        photoBox.allow();
    }

    /**
     * 删除该位置的图片后 如果下一个没有图片就将其设置为不可编辑
     * @param index
     */
    private void deleted(int index){
        if(!isBrowse4next(index)) return;
        TZPhotoBox photoBox = boxes.get(index + 1);
        photoBox.invalid();
    }


    /**
     * 判断下一个box中是否已有图片
     * @param index
     * @return
     */
    private boolean isBrowse4next(int index){
        if(isLast(index)) return false;
        TZPhotoBox photoBox = boxes.get(index + 1);
        if (photoBox.isBrowse()) return false;
        return true;
    }


    /**
     * 检查照片数量是否符合最少数量 minCount
     * @param minCount 最少数量
     * @return
     */
    public boolean check(int minCount){
        int count = 0;
        for (int i = 0; boxes.get(i).mode == TZPhotoBox.MODE_BROWSE && i < boxes.size(); i++){
            count++;
            if(count >= minCount){
                return true;
            }
        }
        return false;
    }

    private boolean isLast(int index){
        return boxes.size()-1 == index;
    }


    /**
     * 取消删除
     * @param event
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent event, boolean def) {
        if(isReadyDelete()&&event.getAction() == MotionEvent.ACTION_UP){
            int[] position = new int[2];
            getLocationInWindow(position);
            TZLog.w("" + position);
            View view = findChildViewUnder(event.getX() - position[0], event.getY() -  position[1]);
            if(view == null){
                cancelDelete();
            }
            return true;
        }
        return def;
    }
}
