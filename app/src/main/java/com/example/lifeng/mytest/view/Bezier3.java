package com.example.lifeng.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.midi.MidiDeviceInfo;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lifeng on 2017/12/28.
 *
 * @desc 将一个圆绘制成一个渐变心形
 * 原理:通过四段三阶贝塞尔曲线绘制而成
 */

public class Bezier3 extends View {
    private final Context mContext;
    private int mCenterX, mCenterY;

    /*** 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置 ***/
    private static final float C = 0.551915024494f;
    /**
     * 圆的半径
     **/
    private float mCircleRadius = 200;
    /**
     * 圆形控制点和数据点的差值
     **/
    private float mDifference = mCircleRadius * C;

    /*** 顺时针记录圆形4个数据点 ***/
    private float[] mData = new float[8];
    /*** 顺时针记录圆形8个控制点 ***/
    private float[] mControl = new float[16];
    
    /*** 变化总时长 ***/
    private float mDuration = 1000;
    /*** 当前已进行时长 ***/
    private float mCurrent = 0;
    /*** 将时长一共划分成多少份 ***/
    private float mCount = 100;
    /*** 每一份变化时长 ***/
    private float mPiece = mDuration / mCount;

    private Paint mPaint;

    public Bezier3(Context context) {
        this(context, null);
    }

    public Bezier3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initPaint();
        initData();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void initData() {
        //初始化数据点
        mData[0] = 0;
        mData[1] = mCircleRadius;

        mData[2] = mCircleRadius;
        mData[3] = 0;

        mData[4] = 0;
        mData[5] = -mCircleRadius;

        mData[6] = -mCircleRadius;
        mData[7] = 0;

        //初始化控制点
        mControl[0] = mData[0] + mDifference;
        mControl[1] = mData[1];

        mControl[2] = mData[2];
        mControl[3] = mData[3] + mDifference;

        mControl[4] = mData[2];
        mControl[5] = mData[3] - mDifference;

        mControl[6] = mData[4] + mDifference;
        mControl[7] = mData[5];

        mControl[8] = mData[4] - mDifference;
        mControl[9] = mData[5];

        mControl[10] = mData[6];
        mControl[11] = mData[7] - mDifference;

        mControl[12] = mData[6];
        mControl[13] = mData[7] + mDifference;

        mControl[14] = mData[0] - mDifference;
        mControl[15] = mData[1];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCoordinateSystem(canvas);

        canvas.translate(mCenterX, mCenterY);
        canvas.scale(1,-1);

        //绘制数据点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(30f);
        for (int i = 0; i < 2 * 4; i += 2) {
            Log.i("TAG", "数据点" + i + " 坐标:(" + mData[i] + "," + mData[i + 1] + ")");
            canvas.drawPoint(mData[i], mData[i + 1], mPaint);
        }

        //绘制控制点
        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < 2 * 8; i += 2) {
            canvas.drawPoint(mControl[i], mControl[i + 1], mPaint);
        }

        //绘制辅助线
        mPaint.setStrokeWidth(5F);
        mPaint.setColor(Color.GRAY);
        for (int i = 2, j = 2; i < 8; i += 2, j += 4) {
            canvas.drawLine(mData[i], mData[i + 1], mControl[j], mControl[j + 1], mPaint);
            canvas.drawLine(mData[i], mData[i + 1], mControl[j + 2], mControl[j + 3], mPaint);
        }
        //绘制剩余的两条线
        canvas.drawLine(mData[0], mData[1], mControl[0], mControl[1], mPaint);
        canvas.drawLine(mData[0], mData[1], mControl[14], mControl[15], mPaint);

        //绘制三阶贝塞尔曲线
        Path path = new Path();
        mPaint.setColor(Color.RED);

//        mPath.cubicTo(mControl[0], mControl[1], mControl[2], mControl[3], mData[2], mData[3]);
//        mPath.cubicTo(mControl[4], mControl[5], mControl[6], mControl[7], mData[4], mData[5]);

        path.moveTo(mData[0], mData[1]);
        for (int i = 0, j = 2; j < 8; i+=4, j+=2) {
            Log.i("TAG", "i:" + i + " j:" + j);
            path.cubicTo(mControl[i], mControl[i+1], mControl[i+2], mControl[i+3], mData[j], mData[j+1]);
        }
        path.cubicTo(mControl[12],mControl[13],mControl[14],mControl[15],mData[0],mData[1]);
        canvas.drawPath(path, mPaint);

        mCurrent  += mPiece;

        if(mCurrent < mDuration) {
            mData[1] -= 120/mCount;
            mControl[7] += 80/mCount;
            mControl[9] += 80/mCount;

            mControl[4] -= 20/mCount;
            mControl[10] += 20/mCount;

            //重绘
            postInvalidateDelayed((long) mPiece);
        }
    }

    /**
     * 绘制坐标系
     *
     * @param canvas
     */
    private void drawCoordinateSystem(Canvas canvas) {
        canvas.save();

        canvas.translate(mCenterX, mCenterY);
        //翻转Y坐标
        canvas.scale(1, -1);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        //X坐标轴
        canvas.drawLine(0, -mCenterY * 2, 0, mCenterY * 2, paint);
        //Y坐标轴
        canvas.drawLine(-mCenterX * 2, 0, mCenterX, 0, paint);

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
    }
}
