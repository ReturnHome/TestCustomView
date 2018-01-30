package com.example.lifeng.mytest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.lifeng.mytest.R;

/**
 * Created by lifeng on 2017/12/29.
 * @desc 使用矩阵对图片进行处理
 */

public class MatrixBitmapView extends View {
    private int mImageResource = R.drawable.earn_saoyisao_bannermiddle;
    private Bitmap mBitmap;
    private Matrix mPolyMatrix;

    public MatrixBitmapView(Context context) {
        this(context,null);
    }

    public MatrixBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), mImageResource);

        mPolyMatrix = new Matrix();
        //原始数组
        float[] src = {
                0,0,    //图片左上角
                mBitmap.getWidth(),0,   //图片右上角
                mBitmap.getWidth(),mBitmap.getHeight(),  //图片右下角
                0,mBitmap.getHeight()   //图片左下角
        };
        //目标数组 这里其实是图片目标位置的四个点坐标
        float[] dst = {
                0,0,
                mBitmap.getWidth(),400,
                mBitmap.getWidth(),mBitmap.getHeight()-200,
                0,mBitmap.getHeight()
        };

        //参数1:原始数组  参数2:原始数组开始位置 参数3:目标数组  参数4:目标数组开始位置  参数5:测控点的数量 取值范围是:0-4
        mPolyMatrix.setPolyToPoly(src,0,dst,0,src.length >> 1);

        //对图片进行等比缩放和位移(图片有点大)  使用setScale会覆盖之前的操作  会导致之前的操作失效
        mPolyMatrix.postScale(0.5f,0.5f);
//        mPolyMatrix.preScale(0.5f,0.5f);
        mPolyMatrix.postTranslate(0,400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,mPolyMatrix,null);
    }
}
