package com.tianzunchina.android.api.widget.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tianzunchina.android.api.R;

import java.io.File;
import java.util.ArrayList;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * CraetTime 2016-4-6
 *
 * @author SunLiang
 */
public class TZPhotoBoxOne extends TZPhotoBoxView implements PhotoBoxChangeListener, View.OnLongClickListener, View.OnClickListener{
    private int widht, height = 100, index = 0;
    private boolean isReadyDel = false;
    private TZPhotoBox box;

    public TZPhotoBoxOne(Context context) {
        super(context);
    }

    public TZPhotoBoxOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
        initClickListener();
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TZPhotoBoxOne, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TZPhotoBoxOne_boxWidth) {
                widht = (int) a.getDimension(attr, 100);
            } else if (attr == R.styleable.TZPhotoBoxOne_boxHeight) {
                height = (int) a.getDimension(attr, 100);
            }else if (attr == R.styleable.TZPhotoBoxOne_boxIndex) {
                index = a.getInt(attr, 0);
            }
        }
        a.recycle();
        box = new TZPhotoBox(context, this, index);
        box.allow();
    }

    private void initClickListener(){
        this.setOnLongClickListener(this);
        this.setOnClickListener(this);
        box.setPhotoBoxChangeListener(this);
    }
    public void showPhoto() {
        Intent intent = new Intent(getContext(), PreviewActivity.class);
        if (box.mode == TZPhotoBox.MODE_ONLY_READ) {
            intent.putExtra(PreviewActivity.KEY_URL, getBoxURL());
        } else {
            intent.putExtra(PreviewActivity.KEY_PATH, getBoxPath());
        }
        getContext().startActivity(intent);
    }

    public void setPhotoByURL(String url) {
        box.addPhoto(url);
    }

    public void setPhotoByURL(String root, String url) {
        url = root + url;
        box.addPhoto(url);
    }

    public void setPhotoByPath(String path) {
        File file = new File(path);
        box.addPhoto(file);
    }

    public String getBoxPath() {
        if (box.isBrowse()) {
            return box.getFileImage().getAbsolutePath();
        }
        return null;
    }

    public String getBoxURL() {
        if (box.isBrowse()) {
            return box.getUrl();
        }
        return "";
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                if (type >= 10) type -= 10;
                if(type != index){
                    return;
                }
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (type >= 10) type -= 10;
                if(type != index){
                    return;
                }
                if (!box.isBrowse()) {
                    box.addPhoto(imageFile);
                }
            }
        });
    }

    /**
     * 获取所有照片路径的集合
     *
     * @return
     */
    public ArrayList<String> getPaths() {
        ArrayList<String> paths = new ArrayList<>();
        if (box.mode == TZPhotoBox.MODE_BROWSE || box.mode == TZPhotoBox.MODE_ONLY_READ) {
            paths.add(box.getFileImage().getAbsolutePath());
        }
        return paths;
    }

    public void onlyRead() {
        box.onlyRead();
    }

    /**
     * 获取指定索引下的图片路径
     *
     * @return
     */
    public String getPath() {
        return box.getFileImage().getAbsolutePath();
    }

    /**
     * 判断是否为待删除状态
     *
     * @return
     */
    public boolean isReadyDelete() {
        return isReadyDel;
    }

    /**
     * 取消删除状态
     */
    public void cancelDelete() {
        box.cancelDelete();
    }

    @Override
    public void change(int index, int mode) {
        if (mode > TZPhotoBox.MODE_BROWSE) {
            return;
        }
        switch (mode) {
            case TZPhotoBox.MODE_READY_DELETE:
                isReadyDel = true;
                box.ivDel.setVisibility(View.VISIBLE);
                break;
            case TZPhotoBox.MODE_ADD:
                isReadyDel = false;
                box.ivDel.setVisibility(View.INVISIBLE);
                break;
            case TZPhotoBox.MODE_NULL:
            case TZPhotoBox.MODE_ONLY_READ:
            case TZPhotoBox.MODE_BROWSE:
            default:
                isReadyDel = false;
                box.ivDel.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 检查照片数量是否符合最少数量 minCount
     *
     * @return
     */
    public boolean check() {
        return box.mode == TZPhotoBox.MODE_BROWSE;
    }

    /**
     * 取消删除
     *
     * @param event
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent event, boolean def) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (!isRangeOfView(this, event) && isReadyDelete()) {
                    cancelDelete();
                }
                break;
        }
        return def;
    }

    @Override
    public boolean onLongClick(View v) {
        box.ivPhoto.performLongClick();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (box.mode) {
            case TZPhotoBox.MODE_READY_DELETE:
                box.ivDel.callOnClick();
                break;
            case TZPhotoBox.MODE_ADD:
                box.ivPhoto.callOnClick();
                break;
            case TZPhotoBox.MODE_BROWSE:
            case TZPhotoBox.MODE_ONLY_READ:
                showPhoto();
                break;
        }
    }

    private boolean isRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
}
