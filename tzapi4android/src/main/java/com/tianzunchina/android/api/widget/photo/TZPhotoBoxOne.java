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
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.util.PhotoTools;

import java.io.File;
import java.util.ArrayList;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * CreateTime 2016-4-6
 *
 * @author SunLiang
 */
public class TZPhotoBoxOne extends TZPhotoBoxView implements PhotoBoxChangeListener, View.OnLongClickListener, View.OnClickListener{
    private int width, height = 100, index = 0;
    private boolean isReadyDel = false;
    private TZPhotoBox box;
    private boolean isCompress = false;//是否压缩，默认不压缩
    private float WIDTH = 1024;//压缩的宽度
    private float HEIGHT = 1024;//压缩的高度
    private int quality = 90;//压缩的质量

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
                width = (int) a.getDimension(attr, 100);
            } else if (attr == R.styleable.TZPhotoBoxOne_boxHeight) {
                height = (int) a.getDimension(attr, 100);
            }else if (attr == R.styleable.TZPhotoBoxOne_boxIndex) {
                index = a.getInt(attr, 0);
            }else if (attr == R.styleable.TZPhotoBoxGroup_isCompress ){
                isCompress = a.getBoolean(attr,false);
            }
        }
        a.recycle();
        box = new TZPhotoBox(context, this, index);
        box.allow();
    }

    /**
     * 初始化点击监听
     */
    private void initClickListener(){
        this.setOnLongClickListener(this);
        this.setOnClickListener(this);
        box.setPhotoBoxChangeListener(this);
    }

    /**
     * 显示图片
     */
    public void showPhoto() {
        Intent intent = new Intent(getContext(), PreviewActivity.class);
        if (box.mode == TZPhotoBox.MODE_ONLY_READ) {
            intent.putExtra(PreviewActivity.KEY_URL, getBoxURL());
        } else {
            intent.putExtra(PreviewActivity.KEY_PATH, getBoxPath());
        }
        getContext().startActivity(intent);
    }

    /**
     * 设置照片的url
     * @param url 地址
     */
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

    /**
     * 得到照片的绝对路径
     * @return null或者绝对路径
     */
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
                    TZLog.e("One",imageFile.toString());
                    if(isCompress){
                        box.addPhoto(PhotoTools.getCompressImageFile(imageFile,WIDTH,HEIGHT,quality));
                    }else {
                        box.addPhoto(imageFile);
                    }
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
     * @return 返回图片的绝对路径
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

    /**
     * 设置压缩的值
     * @param width 宽度
     * @param height 高度
     * @param quality 质量
     */
    public void set(float width,float height,int quality){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.quality = quality;
    }
}
