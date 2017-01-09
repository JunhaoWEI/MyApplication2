package com.example.customviewdemo.View2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
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
        mSecondColor = typedArray.getColor(R.styleable.View2_secondColor,Color.BLUE);
        mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.View2_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
                        getResources().getDisplayMetrics()));

    }
}
