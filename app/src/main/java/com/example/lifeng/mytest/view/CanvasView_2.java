package com.example.lifeng.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lifeng on 2017/12/22.
 * @dec 自定义View之绘制文本
 */

public class CanvasView_2 extends View {

    private Context mContext;
    private Paint mPaint;

    public CanvasView_2(Context context) {
        this(context, null);
    }

    public CanvasView_2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(50F);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String str = "ABCDEFG";
        //参数2 和 参数3表示:基线x 和 基线y
        canvas.drawText(str,200,350,mPaint);
        //参数2 和 参数3表示:截取字符串的开始索引 和 结束索引
        canvas.drawText(str,1,3,200,400,mPaint);

        char[] chars = str.toCharArray();
        //对于数组  参数2 和 参数3 表示: 截取字符串开始索引 和 需要截取的字符串长度
        canvas.drawText(chars,1,3,200,450,mPaint);

        //drawPosText
        str = "ABCD";
        mPaint.setColor(Color.GREEN);
        //参数2:这里的float数组是给字符串中的每个字符指定坐标位置的
        //如果有某个字符未在数组中指定坐标位置 则报索引越界异常
        canvas.drawPosText(str,new float[]{
                100,100,
                200,200,
                300,300,
                400,400,
        },mPaint);
    }
}
