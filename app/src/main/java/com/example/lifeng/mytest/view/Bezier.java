package com.example.lifeng.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.lifeng.mytest.R;

/**
 * Created by lifeng on 2017/12/26.
 * @desc  二阶贝塞尔曲线
 */

public class Bezier extends View {

    private Context mContext;
    private Paint mPaint;
    private PointF mStartPoint,mEndPoint,mControlPoint;

    private int cententX,cententY;

    public Bezier(Context context) {
        this(context,null);
    }

    public Bezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initPaint();
        initPoint();
    }

    private void initPoint() {
        //内塞尔曲线的两个数据点和一个控制点
        mStartPoint = new PointF(0, 0);
        mEndPoint = new PointF(0, 0);
        mControlPoint = new PointF(0, 0);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cententX = w / 2;
        cententY = h / 2;

        //初始化数据点 和 控制点的位置
        mStartPoint.x = cententX - 200;
        mStartPoint.y = cententY;

        mEndPoint.x = cententX +  200;
        mEndPoint.y = cententY;

        mControlPoint.x = cententX;
        mControlPoint.y = cententY - 100;

        Log.i("TAG", "数据点1-->mStartPoint.x:"+  mStartPoint.x  + " mStartPoint.y:"+  mStartPoint.y
                        + "\n数据点2-->mEndPoint.x:" +  mEndPoint.x + "  mEndPoint.y:" +  mEndPoint.y
                        + "\n控制点-->mControlPoint.x:" +  mControlPoint.x + "  mControlPoint.y:" +  mControlPoint.y
        );

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(1f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40f);
        canvas.drawText("控制点坐标:(" + mControlPoint.x + "," + mControlPoint.y + ")",
                100 ,100,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20f);

        canvas.drawPoint(mStartPoint.x,mStartPoint.y,mPaint);
        canvas.drawPoint(mEndPoint.x,mEndPoint.y,mPaint);
        canvas.drawPoint(mControlPoint.x,mControlPoint.y,mPaint);

        //绘制辅助线
        mPaint.setStrokeWidth(4f);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(mStartPoint.x,mStartPoint.y,mControlPoint.x,mControlPoint.y,mPaint);
        canvas.drawLine(mEndPoint.x,mEndPoint.y,mControlPoint.x,mControlPoint.y,mPaint);

        //绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);

        Path path = new Path();
        //重置起点
        path.moveTo(mStartPoint.x,mStartPoint.y);
        //绘制二阶贝塞尔曲线关键函数
        //绘制曲线函数:前两个参数是控制点坐标 后两个参数是结束点坐标
        path.quadTo(mControlPoint.x,mControlPoint.y,mEndPoint.x,mEndPoint.y);
        canvas.drawPath(path,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mControlPoint.x = event.getX();
        mControlPoint.y = event.getY();

        //根据触摸点 时时更新控制点位置
        invalidate();
        return true;
    }
}
