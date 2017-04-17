package com.tianzunchina.android.api.widget.photo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.util.PhotoTools;

import java.io.File;

/**
 * 拍照盒子控件
 * 用于帮助用户通过拍照/相册等方式获取照片，并展示
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public class TZPhotoBox {
	private int index;
	int mode = MODE_ADD;
	private Context context;
	ImageView ivPhoto;
	ImageView ivDel;
	File fileImage;
	String url;
	private PhotoBoxChangeListener listener;
	private PhotoTools pt = PhotoTools.getInstence();

	public TZPhotoBox(){}

	public TZPhotoBox(Context context, TZPhotoBoxView piv, int index) {
		this.context = context;
		this.ivPhoto = piv.ivPhoto;
		this.ivDel = piv.ivDel;
		this.index = index;
		init();
	}
	
	public TZPhotoBox(Context context, ImageView thumbIv, ImageView delIv, int index) {
		this.context = context;
		this.ivPhoto = thumbIv;
		this.ivDel = delIv;
		this.index = index;
		init();
	}

	public TZPhotoBox(Context context, ImageView thumbIv, ImageView delIv, int index, int mode) {
		this.context = context;
		this.ivPhoto = thumbIv;
		this.ivDel = delIv;
		this.index = index;
		this.mode = mode;
		init();
	}

	public TZPhotoBox(Context context, ImageView thumbIv, ImageView delIv, int i, DelPhotoListener.ImpDelCallBack back) {
		this.ivPhoto = thumbIv;
		this.ivDel = delIv;
		this.context = context;
		this.index = i;
		ivPhoto.setOnLongClickListener(new DelPhotoLongListener(this));
		ivDel.setOnClickListener(new DelPhotoListener(this, i, back));
	}

	private void init(){
		ivPhoto.setOnClickListener(new CameraListener(context, index, this));
		ivPhoto.setOnLongClickListener(new DelPhotoLongListener(this));
		ivDel.setOnClickListener(new DelPhotoListener(this));
	}

	public void setPhotoBoxChangeListener(PhotoBoxChangeListener listener){
		this.listener = listener;
	}

	/**
	 * 无效状态
	 * 不能点击 不显示图片
	 */
	public void invalid(){
		setMode(MODE_NULL);
		ivPhoto.setImageBitmap(null);
		ivDel.setVisibility(View.INVISIBLE);
		ivPhoto.setEnabled(false);
	}

	/**
	 * 允许操作状态
	 * 允许点击，显示“+”图片
	 */
	public void allow(){
		TZLog.e("this is allow");
		setMode(MODE_ADD);
		fileImage = null;
		ivDel.setVisibility(View.INVISIBLE);
		ivPhoto.setEnabled(true);
		ivPhoto.setImageResource(R.mipmap.ico_add_photo);
	}

	/**
	 * 只读模式
	 * 只能单击浏览图片、不能删除或拍照
	 */
	public void onlyRead(){
		setMode(MODE_ONLY_READ);
		ivDel.setVisibility(View.INVISIBLE);
		if(url == null) {
			invalid();
		}
		ivPhoto.setOnLongClickListener(null);
	}


	/**
	 * 待删除状态
	 * 显示删除图标，保留缩略图
	 */
	public void readyDelete(){
		if(mode == MODE_BROWSE){
			setMode(MODE_READY_DELETE);
			ivDel.setVisibility(View.VISIBLE);
		}
	}

	public void cancelDelete(){
		if(mode == MODE_READY_DELETE){
			setMode(MODE_BROWSE);
			ivDel.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 删除照片
	 * 清空缩略图及本地缓存图片
	 */
	public void deletePhoto(){
		TZLog.e("this is deletePhoto");
		allow();
	}

	public void addPhoto(File file) {
		if(file == null){
			return;
		}
		TZLog.e("file",file.toString());
		fileImage =  file;
		ivPhoto.setEnabled(true);
		if(!fileImage.exists()){
			return;
		}
		Glide.with(context).load(file).placeholder(R.mipmap.pic_loading).error(R.mipmap.pic_loading).override(200, 200).centerCrop().into(ivPhoto);
		setMode(MODE_BROWSE);
	}

	public void addPhoto(String url) {
		if(url == null){
			return;
		}
		this.url =  url;
		ivPhoto.setEnabled(true);
		Glide.with(context).load(url).placeholder(R.mipmap.pic_loading).error(R.mipmap.pic_loading).override(200, 200).centerCrop().into(ivPhoto);
		setMode(MODE_BROWSE);
	}

	public void setMode(int mode){
		this.mode = mode;
		if (listener == null)return;
		listener.change(index, mode);
	}

	public boolean isBrowse() {
		return mode == MODE_BROWSE || mode == MODE_ONLY_READ;
	}

	public File getFileImage() {
		return fileImage;
	}

	public static final int MODE_NULL = 0, MODE_ONLY_READ = 1, MODE_ADD = 2, MODE_READY_DELETE = 3,MODE_BROWSE = 4;

	@Override
	 public String toString() {
		return "TZPhotoBox [activity=" + context + ", ivPhoto=" + ivPhoto
				+ ", fileImage=" + fileImage + ", ivDel=" + ivDel + ", pt="
				+ pt +  "]";
	}

	Callback picassoCallback =  new Callback() {
		@Override
		public void onSuccess() {}

		@Override
		public void onError() {
			switch (mode){
				case MODE_BROWSE:
					TZLog.e("this is onError" );
					allow();
					break;
				case MODE_ONLY_READ:
					invalid();
					break;
			}
		}
	};


	public String getUrl() {
		return url;
	}

	public void setFileImage(File fileImage) {
		this.fileImage = fileImage;
	}

	public int getMode() {
		return mode;
	}
}
