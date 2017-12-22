package com.example.lifeng.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lifeng on 2017/12/18.
 */

public class CanvasView extends View {
    private Context mContent;
    private Paint mPaint;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContent = context;
//        initPaint();
        recording();
    }

    private void initPaint() {
        mPaint = new Paint();
        //去抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private Picture mPicture = new Picture();

    private void recording(){
        Canvas canvas = mPicture.beginRecording(500, 500);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.translate(250,250);
        //绘制圆
        canvas.drawCircle(0,0,100,paint);
        mPicture.endRecording();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("TTT", "OnDraw");
//        canvas.translate(getWidth() / 2, getHeight() / 2);

        PictureDrawable pictureDrawable = new PictureDrawable(mPicture);
        pictureDrawable.setBounds(0,0,250,mPicture.getHeight());
        pictureDrawable.draw(canvas);

//        canvasBasic(canvas);
//        canvasPattern(canvas);
//        canvasRotate(canvas);
//        canvasRotatePattern(canvas);
//            canvasSkew(canvas);

//        mPicture.draw(canvas);

//        canvasPath(canvas);
    }

    private void canvasPath(Canvas canvas) {
        Path path = new Path();
        path.lineTo(200,200);
        path.lineTo(200,0);
        //close:用于将path的最后一个点坐标和起始点坐标连接起来
        path.close();
        canvas.drawPath(path,mPaint);
    }

    /**
     * canvas skew(错切)的基本操作
     * @param canvas
     */
    private void canvasSkew(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(0, 0, 200, 200);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF,mPaint);

        //水平错切 45度
        canvas.skew(1,0);
        //错切也是可以叠加的
        //调用次序不同 效果也不同
        canvas.skew(0,1);

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF,mPaint);
    }

    /**
     * 利用canvas旋转可以叠加的特性绘制一个图案
     *
     * @param canvas
     */
    private void canvasRotatePattern(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(0, 0, 400, mPaint);
        canvas.drawCircle(0, 0, 380, mPaint);

        for (int i = 0; i <= 360; i += 10) {
            canvas.drawLine(0, 380, 0, 400, mPaint);
            canvas.rotate(10);
        }
    }

    /**
     * canvas的旋转操作
     *
     * @param canvas
     */
    private void canvasRotate(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        RectF rectF = new RectF(0, -400, 400, 0);
        canvas.drawRect(rectF, mPaint);

        //将画布旋转180度
//        canvas.rotate(180);
        //将画布向右偏移200单位
        canvas.rotate(180, 200, 0);

        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 利用canvas缩放可以叠加的特性绘制图案
     *
     * @param canvas
     */
    private void canvasPattern(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(-400, -400, 400, 400);
        for (int i = 0; i < 20; i++) {
            canvas.scale(0.9f, 0.9f);
            canvas.drawRect(rectF, mPaint);
        }
    }

    /**
     * canvas基本操作
     *
     * @param canvas
     */
    private void canvasBasic(Canvas canvas) {

        RectF rectF = new RectF(0, -400, 400, 0);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF, mPaint);
        //画布缩放  缩放就是坐标原点
//        canvas.scale(0.5f,0.5f);

        //画布向右偏移200个单位
//        canvas.scale(0.5f,0.5f,200,0);

        //当缩放比例是负数时,会根据缩放中心轴进行翻转
//        canvas.scale(-0.5f,-0.5f);

        //画布向右偏移200个单位
        canvas.scale(-0.5f, -0.5f, 200, 0);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }
}
