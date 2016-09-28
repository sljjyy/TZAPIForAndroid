package com.tianzunchina.android.api.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 图片处理工具类
 * CraetTime 2016-3-4
 * @author SunLiang
 */
public class PhotoTools {
    private UnitConverter converter;
    private static PhotoTools photoTools;
    private FileCache fileCache = new FileCache();

    public static PhotoTools getInstence(){
        if(photoTools == null){
            photoTools = new PhotoTools();

        }
        return photoTools;
    }

    private PhotoTools(){
        converter = UnitConverter.getInstance();
    }

    public File getCache(String path) {
        String fileName = fileCache.url2fileName(path);
        File file = new File(fileCache.getCacheDir(), fileName);
        return file;
    }

    public Bitmap circleBitmap(Bitmap bitmap, int ratio) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public Bitmap convertToBitmap(String path) {
        return convertToBitmap(path, 80, 80, true);
    }

    /**
     * 压缩图片文件大小
     */
    public Bitmap convertToBitmap(String path, int w, int h, boolean isDip) {
        if (isDip) {
            w = converter.dip2px(w);
            h = converter.dip2px(h);
        }
        if (!new File(path).exists()) {
            return null;
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
                BitmapFactory.decodeFile(path, opts));
        if (weak.get() == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    public File saveBitmap(Bitmap bitmap, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /*
     * 保存图片到缓存
     */
    public File saveBitmap(Bitmap bitmap) {
        File file = fileCache.getCacheDir();
        saveBitmap(bitmap, file);
        return file;
    }

    public File saveBitmap(Bitmap bitmap, String fileName) {
        File file = new File(fileCache.getCacheDir(), fileName);
        saveBitmap(bitmap, file);
        return file;
    }

    public File saveBitmap(Bitmap bitmap, URL url) {
        File file = new File(fileCache.getCacheDir(), fileCache.url2fileName(url));
        saveBitmap(bitmap, file);
        return file;
    }

    public File saveBitmap(String path) {
        return saveBitmap(getBitmapFromPath(path));
    }

    public File saveBitmap(String path, int angle) {
        Bitmap bitmap = rotateBitmap(getBitmapFromPath(path), angle);
        return saveBitmap(bitmap);
    }

    public Bitmap zoomBitmap(Bitmap source) {
        return zoomBitmap(source, 1024, 768);
    }

    /**
     * 按比例缩放尺寸
     */
    public Bitmap zoomBitmap(Bitmap source, int width, int height) {
        if (source == null) {
            return null;
        }
        int w = source.getWidth();
        int h = source.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        float scale = scaleWidth;
        if(scaleWidth > scaleHeight){
            scale = scaleHeight;
        }
        matrix.postScale(scale, scale);
        Bitmap newbmp = Bitmap.createBitmap(source, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public Bitmap createThumbnail(Bitmap source) {
        return createThumbnail(source, 80, 80, true, false, true);
    }

    /**
     * 缩放或切割图片尺寸
     *
     * @param source: 源图片 width: 目标宽度 height: 目标高度 isDip: width、heigth的单位. true
     * 为dp，false为px isRoate: 是否旋转, true旋转90°,false不旋转 isCut: 是否进行切割, true
     * 对source按照width*heigth的尺寸切割， false 按照比例缩放直到宽度和高度都不超过width和heigth
     */
    public Bitmap createThumbnail(Bitmap source, int width, int height,
                                  boolean isDip, boolean isRotate, boolean isCut) {
        if (source == null) {
            return null;
        }
        if (isDip) {
            width = converter.dip2px(width);
            height = converter.dip2px(height);
        }
        int oldW = source.getWidth();
        int oldH = source.getHeight();
        Bitmap imgThumb = null;
        if (isCut) {
            imgThumb = ThumbnailUtils.extractThumbnail(source, width, height);
        } else {
            imgThumb = zoomBitmap(source, width, height);
        }
        if (isRotate && oldW < oldH) {
            imgThumb = rotateBitmap(imgThumb, 90);
        }
        return imgThumb;
    }

    /**
     * 旋转图片
     */
    public Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(angle);
        Bitmap mRotateBitmap = null;
        try {
            mRotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                    matrix, true);
        } catch (OutOfMemoryError e) {
            System.gc();
            System.runFinalization();
            mRotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                    matrix, true);
        }
        return mRotateBitmap;
    }

    /**
     * 获取指定uri中的图片
     */
    public Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
	 * 获取指定url中的图片
	 */
    public Bitmap getBitMap(URL url) {
        Bitmap bitmap = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Bitmap getBitmapFromPath(String filePath){
        return getBitmapFromPath(filePath, true);
    }

    /**
     *
     * @param filePath
     * @param isSample
     * @return
     */
    public Bitmap getBitmapFromPath(String filePath, boolean isSample) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        if(isSample){
            opts.inSampleSize = computeSampleSize(opts, -1, 1024*768);
            opts.inJustDecodeBounds = false;
        }
        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        }catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 256 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 获得照片旋转角度
     * @param filepath
     * @return
     */
    public int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }
}
