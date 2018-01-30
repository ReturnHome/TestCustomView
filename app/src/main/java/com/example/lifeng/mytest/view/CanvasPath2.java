package com.example.lifeng.mytest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lifeng on 2017/12/28.
 */

public class CanvasPath2 extends View {

    private Paint mPaint;
    private int mCentreX;
    private int mCentreY;

    public CanvasPath2(Context context) {
        this(context,null);
    }

    public CanvasPath2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCentreX = w /2;
        mCentreY = h /2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mCentreX,mCentreY);

        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(10f);

        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();

        path1.addCircle(0,0,200, Path.Direction.CW);
        path2.addRect(0,-200,200,200, Path.Direction.CW);
        path3.addCircle(0,-100,100, Path.Direction.CW);
        path4.addCircle(0,100,100, Path.Direction.CCW);

        //DIFFERENCE:path1中减去Path2后减去的部分
        path1.op(path2,Path.Op.DIFFERENCE);
        //UNION:包含全部path1和path3
        path1.op(path3, Path.Op.UNION);
        path1.op(path4, Path.Op.DIFFERENCE);

        canvas.drawPath(path1,mPaint);
    }
}
