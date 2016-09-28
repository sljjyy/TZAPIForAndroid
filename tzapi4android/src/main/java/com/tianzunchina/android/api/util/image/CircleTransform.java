package com.tianzunchina.android.api.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * 毕加索圆形图片辅助类
 * Created by sl on 2015/10/26.
 */
public class CircleTransform implements Transformation {
    //重载函数
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int x = 0, y = 0;
        if (width != height) {
            if (width < height) {
                y = (height - width) / 2;
            } else {
                x = (width - height) / 2;
            }
            width = height = Math.min(width, height);
            Bitmap tempSource = Bitmap.createBitmap(source, x, y, width, height);
            source.recycle();
            source = tempSource;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        Canvas canvas = new Canvas(output);
        final RectF bounds = new RectF(0, 0, width, height);
        canvas.drawOval(bounds, paint);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "circle";
    }
}
