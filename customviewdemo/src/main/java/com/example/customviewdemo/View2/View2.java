package com.example.customviewdemo.View2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.customviewdemo.R;

/**
 * Created by WEI JUNHAO on 2017/1/9.
 */

public class View2 extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private int mSplitSize;
    private int mCount;
    private Bitmap mImage;

    private Paint mPaint;
    private int mCurrentCount = 3;
    private Rect mRect;

    public View2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public View2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.View2, defStyleAttr, 0);

        mFirstColor = typedArray.getColor(R.styleable.View2_firstColor, Color.GRAY);
        mSecondColor = typedArray.getColor(R.styleable.View2_secondColor, Color.BLUE);
        mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.View2_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
                        getResources().getDisplayMetrics()));
        mSplitSize = typedArray.getInt(R.styleable.View2_splitSize, 20);
        mCount = typedArray.getInt(R.styleable.View2_dotcount, 20);
        mImage = BitmapFactory.decodeResource(getResources(), typedArray.
                getResourceId(R.styleable.View2_bg, 0));

        typedArray.recycle();

        mPaint = new Paint();
        mRect = new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStrokeWidth(mCircleWidth); //圆环宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); //圆头

        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); //空心
        int centre = getWidth() / 2; //获取圆心x坐标
        int radius = centre - centre - mCircleWidth / 2; //半径

        drawOval(canvas, centre, radius);

        int relRadius = radius - mCircleWidth / 2;
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mImage.getWidth());
            mRect.bottom = (int) (mRect.top + mImage.getHeight());
        }
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    /**
     * 画 音量条
     *
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, int radius) {

        float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius,
                centre + radius);
        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }

        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }
    }

    public void up() {
        mCurrentCount++;
        postInvalidate();
    }

    public void down() {
        mCurrentCount--;
        postInvalidate();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown) {
                    down();
                } else {
                    up();
                }
                break;
        }
        return true;
    }
}
