package com.tianzunchina.android.api.widget.photo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

import com.tianzunchina.android.api.utils.FileCache;
import com.tianzunchina.android.api.view.CameraActivity;

import java.io.File;

import pl.aprilapps.easyphotopicker.EasyImage;

public class CameraListener implements OnClickListener {
	private Activity activity;
	private TZPhotoBox pbox;
	private int num, weight = 0;
	private FileCache fileCache = new FileCache();

	public CameraListener(Context context, int i, TZPhotoBox pbox) {
		activity = (Activity) context;
		num = i;
		this.pbox = pbox;
	}

	public CameraListener(Context context, int i, TZPhotoBox pbox, int weight) {
		this(context, i, pbox);
		this.weight = weight;
	}

	/**
	 * 部分机型不适用
	 * 调用自带相机应用
	 */
	@Deprecated
	public void openCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		File file = new File(fileCache.getCacheDir(), "temp.jpg");
		Uri imageUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		activity.startActivityForResult(intent, num + weight);
	}

	/**
	 * 调用应用内部相机
	 */
	public void openCamera3() {
		Intent intent = new Intent(activity, CameraActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		activity.startActivityForResult(intent, num + weight);
	}

	public void openCamera2(){
		EasyImage.openCamera(activity,  num + weight);
	}

	/**
	 * 调用相册
	 */
	public void openAlbum() {
		EasyImage.openGallery(activity, num + weight + 10);
	}

	@Override
	public void onClick(View v) {
		if (pbox.isBrowse()) {
			Intent intent = new Intent(activity, PreviewActivity.class);
			intent.putExtra("path", pbox.fileImage.getAbsolutePath());
			intent.putExtra("imageUrl", pbox.url);
			activity.startActivity(intent);
			return;
		}
		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(activity).setTitle("选择照片来源")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							openAlbum();
						} else {
							openCamera2();
						}
					}
				}).create();
		dlg.show();
	}
}
