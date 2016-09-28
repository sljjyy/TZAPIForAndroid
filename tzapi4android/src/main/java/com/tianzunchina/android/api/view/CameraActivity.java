package com.tianzunchina.android.api.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.util.FileCache;
import com.tianzunchina.android.api.util.PhotoTools;

import java.io.File;
import java.lang.reflect.Method;

/**
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public class CameraActivity extends Activity {
	private static AlertDialog alertDialog;
	private TextView btnTake;
	private TextView btnTake2;
	private Camera tzCamera;
	private int angle = 0;
	private PhotoTools pt = PhotoTools.getInstence();
	private FileCache fileCache = new FileCache();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 窗口特效为无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置窗口全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_camera);

		btnTake = (TextView) findViewById(R.id.btnTakePicture);
		btnTake2 = (TextView) findViewById(R.id.btnTakePicture2);
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.svCamera);
		// 保持屏幕高亮
		surfaceView.getHolder()
				.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setKeepScreenOn(true);
		btnTake.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takepicture();
			}
		});
		btnTake2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takepicture();
			}
		});
		// 监听 surfaceView何时创建
		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try{
					// 打开摄像头
					if (tzCamera == null) {
						tzCamera = Camera.open();
						try {
							tzCamera.setDisplayOrientation(90);
							angle = 90;
							tzCamera.setPreviewDisplay(holder);
						} catch (Exception e) {
							e.printStackTrace();
							if (tzCamera != null) {
								tzCamera.setPreviewCallback(null);
								tzCamera.release();
								tzCamera = null;
							}
						}
					}
				} catch (Exception e){
					finish();
					TZToastTool.essential("摄像头授权有误, 是否已禁止调用摄像头");
				}

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
									   int width, int height) {
				Camera.Parameters parameters = tzCamera.getParameters();
				// 设置照片格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				parameters.setJpegQuality(100);  // 设置照片的质量
				// 设置保存的图像大小
				tzCamera.setParameters(parameters);
				// 开始拍照
				tzCamera.startPreview();
				tzCamera.autoFocus(new AutoFocusCallback() {
					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						if (success) {
							camera.cancelAutoFocus();
						}
					}
				});
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (tzCamera != null) {
					// 关闭预览并释放资源
					tzCamera.stopPreview();
					tzCamera.setPreviewCallback(null);
					tzCamera.release();
					tzCamera = null;
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setCameraDisplayOrientation(tzCamera, 0);
			angle = 0;
			btnTake.setVisibility(View.GONE);
			btnTake2.setVisibility(View.VISIBLE);
		} else {
			setCameraDisplayOrientation(tzCamera, 90);
			angle = 90;
			btnTake.setVisibility(View.VISIBLE);
			btnTake2.setVisibility(View.GONE);
		}
	}

	// 按下拍照按钮后，开始拍照
	public void takepicture() {
		showDialog();
		try {
			tzCamera.autoFocus(mAutoFocusCallback);
			if (tzCamera != null) {
				tzCamera.takePicture(mShutterCallback, null, mPictureCallback);
			}
		} catch (Exception e) {
			closeDialog();
			TZToastTool.essential( "相机自动对焦失败!");
//			Toast.makeText(this, "相机自动对焦失败!", Toast.LENGTH_SHORT).show();
		}

	}

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CameraActivity.this);
		builder.setMessage("照片处理中...");
		builder.setCancelable(false);
		alertDialog = builder.create();
		alertDialog.show();
	}

	public static void closeDialog() {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
	}

	final AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				camera.cancelAutoFocus();
			}
		}
	};

	final ShutterCallback mShutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {
		}
	};

	final PictureCallback mPictureCallback = new PictureCallback() {
		// 当用户拍完一张照片的时候触发onPictureTaken,这时候对拍下的照片进行相应的处理操作
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			tzCamera.release();
			tzCamera = null;
			// 判断是否存在SD卡
			if (new FileCache().haveSDCard()) {
				// 将拍的照片保存到sd卡中
				File jpgFile = fileCache.getCacheJPG();
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				bitmap = pt.zoomBitmap(bitmap);
				bitmap = pt.rotateBitmap(bitmap, angle);
				pt.saveBitmap(bitmap, jpgFile);
				// 返回照片路径
				Intent intent = new Intent();
				intent.putExtra("path", jpgFile.getAbsolutePath());
				setResult(RESULT_OK, intent);
				closeDialog();
				finish();
			} else {
				TZToastTool.essential("请检查SD卡");
			}
		}
	};

	public void setCameraDisplayOrientation(Camera camera, int orientation) {
		if (android.os.Build.VERSION.SDK_INT > 10) {
			camera.setDisplayOrientation(orientation);
		} else {
			Method downPolymorphic;
			try {
				downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", int.class);
				if (downPolymorphic != null)
					downPolymorphic.invoke(camera, orientation);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}