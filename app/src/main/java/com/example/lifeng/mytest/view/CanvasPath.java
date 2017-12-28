package com.example.lifeng.mytest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lifeng on 2017/12/26.
 */

public class CanvasPath extends View {

    private Paint mPaint;

    public CanvasPath(Context context) {
        this(context,null);
    }

    public CanvasPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPath();
    }

    private void initPath() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15f);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2);
//        basePath(canvas);
//        directionPath(canvas);
//        pathAddPath(canvas);

        canvas.scale(1,-1);//翻转Y坐标
        Path path = new Path();
        path.lineTo(100,100);

        RectF rectF = new RectF(0, 0, 300, 300);
        //参数2:开始角度 参数3:扫描角度
//        path.addArc(rectF,0,300);
//        path.arcTo(rectF,0,300);

        //forceMoveTo为true表示:将最后一个点移动到圆弧起点 不连接最后一个点和圆弧起点  false时则会连接
        path.arcTo(rectF,0,300,false);
        canvas.drawPath(path,mPaint);
    }

    private void pathAddPath(Canvas canvas) {
        //翻转Y坐标
        canvas.scale(1,-1);
        Path path = new Path();
        Path src = new Path();

        path.addRect(-200,-200,200,200, Path.Direction.CW);
        src.addCircle(0,0,100, Path.Direction.CW);

        //addPath:将两个path合并成为一个
//        path.addPath(src);

        //将path移动到指定位置后再合并
        path.addPath(src,0,200);
        canvas.drawPath(path,mPaint);
    }

    private void directionPath(Canvas canvas) {
        Path path = new Path();
        //Path.Direction.CCW:逆时针 Path.Direction.CW:顺时针
        path.addRect(-200,-200,200,200, Path.Direction.CCW);
        //重置最后一个点的位置
        path.setLastPoint(-300,300);
        canvas.drawPath(path,mPaint);
    }

    private void basePath(Canvas canvas) {
        Path path = new Path();
        //lineTo:表示从某个点到参数坐标点之间连接一条线
        //第一次没有操作  因此起点是默认坐标原点
        path.lineTo(200,200);
        //moveTo:修改下次操作的起点
//        path.moveTo(200,100);

        //setLastPoint:重置上一个操作的最后一个点
        path.setLastPoint(200,300);
        path.lineTo(200,0);

        //close:用于连接当前最后一个点和最初的一个点 最终形成一个封闭的图形
        path.close();

        canvas.drawPath(path,mPaint);
    }
}
