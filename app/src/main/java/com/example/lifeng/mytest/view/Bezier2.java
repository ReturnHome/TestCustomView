package com.example.lifeng.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lifeng on 2017/12/27.
 * @desc 三阶贝塞尔曲线
 */

public class Bezier2 extends View {
    private final Context mContext;
    private Paint mPaint;
    private Paint mPaintText;
    private int mCenterX;
    private int mCenterY;
    private PointF mPointA,mPointB,mControlA,mControlB;
    private boolean mode = true;

    public Bezier2(Context context) {
        this(context,null);
    }

    public Bezier2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initPoint();
        initPaint();
    }

    private void initPoint() {
        mPointA = new PointF();
        mPointB = new PointF();
        mControlA = new PointF();
        mControlB = new PointF();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //绘制文本使用
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
    }

    /**
     *
     * @param mode true:控制点A false:控制点B
     */
    public void setMode(boolean mode){
        this.mode = mode;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;

        //设置数据点坐标
        mPointA.x = mCenterX -200;
        mPointA.y = mCenterY;

        mPointB.x = mCenterX + 200;
        mPointB.y = mCenterY;

        mControlA.x = mCenterX - 200;
        mControlA.y = mCenterY - 100;

        mControlB.x = mCenterX;
        mControlB.y = mCenterY - 200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaintText.setColor(Color.RED);
        mPaintText.setTextSize(30f);

        canvas.drawText("A控制点坐标:" + mControlA.x + "," + mControlA.y,
                100 ,100,mPaintText);
        canvas.drawText("B控制点坐标:" + mControlB.x + "," + mControlB.y,
                100 ,150,mPaintText);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(30f);
        canvas.drawPoint(mPointA.x,mPointA.y,mPaint);
        canvas.drawPoint(mPointB.x,mPointB.y,mPaint);
        canvas.drawPoint(mControlA.x,mControlA.y,mPaint);
        canvas.drawPoint(mControlB.x,mControlB.y,mPaint);

        canvas.drawText("数据点A",mPointA.x - mPaint.getStrokeWidth() - 20,
                mPointA.y + mPaint.getStrokeWidth() + 40,mPaintText);

        canvas.drawText("数据点B",mPointB.x - mPaint.getStrokeWidth() - 20,
                mPointB.y + mPaint.getStrokeWidth() + 40,mPaintText);

        //绘制辅助线
        mPaint.setStrokeWidth(4f);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(mPointA.x,mPointA.y,mControlA.x,mControlA.y,mPaint);
        canvas.drawLine(mControlA.x,mControlA.y,mControlB.x,mControlB.y,mPaint);
        canvas.drawLine(mControlB.x,mControlB.y,mPointB.x,mPointB.y,mPaint);

        //绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(mPointA.x,mPointA.y);
        //绘制三阶贝塞尔曲线关键函数
        //前四个参数是设置两个控制点的坐标 后面两个参数是结束点坐标
        path.cubicTo(mControlA.x,mControlA.y,mControlB.x,mControlB.y,mPointB.x,mPointB.y);
        canvas.drawPath(path,mPaint);

        mPaintText.setColor(Color.BLUE);
        canvas.drawText("控制点A",mControlA.x - mPaint.getStrokeWidth() + 30,
                mControlA.y + mPaint.getStrokeWidth()/2,mPaintText);

        canvas.drawText("控制点B",mControlB.x - mPaint.getStrokeWidth() + 30,
                mControlB.y+ mPaint.getStrokeWidth()/2,mPaintText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 400;
        int mHeight = 400;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(mode) {
            mControlA.x = x;
            mControlA.y = y;
        } else {
           mControlB.x = x;
           mControlB.y = y;
        }
        invalidate();
        return true;
    }
}
