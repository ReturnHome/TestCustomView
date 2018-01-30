package com.example.lifeng.mytest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.lifeng.mytest.R;


/**
 * Created by lifeng on 2018/1/2.
 *
 * @desc 具有删除按钮的输入框
 */

public class ClearEditText extends AppCompatEditText {

    private Context mContext;

    /*** 清除图片资源ID ***/
    private int mClearResourceId;
    /*** 清除按钮宽度 ***/
    private int mClearBtnWidth;
    /*** 清除按钮左右边距 ***/
    private int mBtnPaddingLeft, mBtnPaddingRight;
    /*** 动画时长 ***/
    private int mAnimatorTime;
    /*** 这个要是设置 ***/
    private boolean mIsVisibleClearBtn;
    private int mOtherRight = 0;

    private int mPaddingRight;
    private Bitmap mBitmap_clear;
    private ValueAnimator mAnimator_gone;
    private ValueAnimator mAnimator_visible;
    /*** 初次进入 删除图标是否显示 默认是不显示***/
    private boolean mIsVisibleFirst;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);

        init(attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CET_clear_edittext, defStyleAttr, 0);
        if (typedArray != null) {
            mClearResourceId = typedArray.getResourceId(R.styleable.CET_clear_edittext_CET_clearImgResource, R.drawable.clearfill);
            float clearBtnWidth = typedArray.getDimension(R.styleable.CET_clear_edittext_CET_btnWidth, 23);
            mClearBtnWidth = dp2px(clearBtnWidth);
            float btnPaddingLeft = typedArray.getDimension(R.styleable.CET_clear_edittext_CET_btnPaddingLeft, 5);
            mBtnPaddingLeft = dp2px(btnPaddingLeft);
            float btnPaddingRight = typedArray.getDimension(R.styleable.CET_clear_edittext_CET_btnPaddingRight, 5);
            mBtnPaddingRight = dp2px(btnPaddingRight);

            mAnimatorTime = typedArray.getInteger(R.styleable.CET_clear_edittext_CET_animatorTime, 200);
            mIsVisibleClearBtn = typedArray.getBoolean(R.styleable.CET_clear_edittext_CET_isVisibleClearBtn, true);
            mIsVisibleFirst = typedArray.getBoolean(R.styleable.CET_clear_edittext_CET_isVisibleFirst, false);

            typedArray.recycle();
        }

        if (mClearResourceId != 0) {
            mBitmap_clear = createBitmap(mClearResourceId, mContext);
        }

        if (mIsVisibleClearBtn) {
            mAnimator_gone = ValueAnimator
                    .ofFloat(1f, 0f)
                    .setDuration(mAnimatorTime);

            mAnimator_visible = ValueAnimator
                    .ofInt(mClearBtnWidth + mBtnPaddingRight, 0)
                    .setDuration(mAnimatorTime);
        }
    }

    /*** 给子类继承使用 ***/
    protected int getIntervalLeft() {
        return mBtnPaddingLeft;
    }

    protected int getIntervalRight(){
        return mBtnPaddingRight;
    }

    protected int getmWidth_clear() {
        return mClearBtnWidth;
    }

    public Bitmap getmBitmap_clear() {
        return mBitmap_clear;
    }

    public void addRight(int right) {
        mOtherRight += right;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mIsVisibleClearBtn) {
            //EditText右边距 = 清除按钮左边距 + 清除按钮宽度 + 清除按钮右边距
            mPaddingRight = mBtnPaddingLeft + mClearBtnWidth + mBtnPaddingRight;
            setPadding(getPaddingLeft(), getPaddingTop(), mPaddingRight + mOtherRight, getPaddingBottom());
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (mIsVisibleClearBtn) {
            if (text.length() > 0) {
                if (!mIsVisibleFirst) {
                    mIsVisibleFirst = true;
                    startShowAnimator();
                }
            } else {
                if (mIsVisibleFirst) {
                    mIsVisibleFirst = false;
                    startGoneAnimator();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //按钮左边
            int x1 = getWidth() - mClearBtnWidth-mBtnPaddingRight- mOtherRight;
            //按钮右边
            int x2 = getWidth() - mBtnPaddingRight - mOtherRight;
            Log.w("TTT", "onTouchEvent x1:" + x1 + " x2:" + x2 + " event.getX():" + event.getX());
            Log.w("TTT", "onTouchEvent x2-x1:" + (x2-x1) + " mClearBtnWidth:" + mClearBtnWidth);

            boolean touchable = (x1 < event.getX()) && (event.getX() < x2);

            //当输入框不可点击的时候  不清除输入框中的文本
            if (touchable && this.isClickable()) {
                setError(null);
                this.setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsVisibleClearBtn) return;
        //设置抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));//抗锯齿

        if (mAnimator_visible.isRunning()) {
            int animatedValue = (int) mAnimator_visible.getAnimatedValue();
            drawClearShow(animatedValue, canvas);
            invalidate();
        } else if (mIsVisibleFirst) {
            drawClearShow(0, canvas);
        }

        if (mAnimator_gone.isRunning()) {
            float animatedValue = (float) mAnimator_gone.getAnimatedValue();
            drawClearGone(animatedValue, canvas);
            invalidate();
        }
    }

    /**
     * 绘制清除按钮出现的图案
     *
     * @param translationX 水平移动距离
     * @param canvas
     */
    protected void drawClearShow(int translationX, Canvas canvas) {
        int right = getWidth() + getScrollX() - mBtnPaddingRight - mOtherRight + translationX;
        int left = right - mClearBtnWidth;
        int top = (getHeight() - mClearBtnWidth) / 2;
        int bottom = top + mClearBtnWidth;
        Log.i("TAG", "drawClearShow left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mBitmap_clear, null, rect, null);
    }

    /**
     * 绘制清除按钮消失的图案
     *
     * @param animatedValue
     * @param canvas
     */
    private void drawClearGone(float animatedValue, Canvas canvas) {
        int right = (int) (getWidth() + getScrollX() - mBtnPaddingRight - mOtherRight - mClearBtnWidth * (1f - animatedValue) / 2f);
        int left = (int) (getWidth() + getScrollX() - mBtnPaddingRight - mOtherRight - mClearBtnWidth * (animatedValue + (1f - animatedValue) / 2f));
        int top = (int) ((getHeight() - mClearBtnWidth * animatedValue) / 2);
        int bottom = (int) (top + mClearBtnWidth * animatedValue);

        Log.i("TAG", "drawClearGone left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mBitmap_clear, null, rect, null);
    }

    public int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 给图标染上当前提示文本的颜色并且转出Bitmap
     *
     * @param resources
     * @param context
     * @return
     */
    public Bitmap createBitmap(int resources, Context context) {
        final Drawable drawable = ContextCompat.getDrawable(context, resources);
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);

        /********************getCurrentHintTextColor() 将图标渲染成当前输入框的颜色********************/
        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        return drawableToBitmap(wrappedDrawable);
    }

    /**
     * drawable转换成bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 开始消失动画
     */
    private void startGoneAnimator() {
        endAnimator();
        mAnimator_gone.start();
        invalidate();
    }

    /**
     * 开始显示动画
     */
    private void startShowAnimator() {
        endAnimator();
        mAnimator_visible.start();
        invalidate();
    }

    /**
     * 结束所有动画
     */
    private void endAnimator() {
        mAnimator_gone.end();
        mAnimator_visible.end();
    }
}
