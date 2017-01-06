package com.example.customviewdemo.Circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.customviewdemo.R;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO: document your custom view class.
 */
public class CircleView extends View {
    private int inColor;//圆环颜色
    private int arcColor;//圆弧颜色
    private int speed;//圆环速度
    private float circleWidth;//圆环宽度
    private Paint mPaint;
    private int angle = 0;//旋转的角度

    public CircleView(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleView, defStyle, 0);

        inColor = a.getColor(R.styleable.CircleView_inColor,Color.RED);
        arcColor = a.getColor(R.styleable.CircleView_outColor,Color.BLUE);
        circleWidth = a.getDimensionPixelSize(R.styleable.CircleView_circleWidth,100);
        speed = a.getInt(R.styleable.CircleView_circleSpeed,20);
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
                if (angle >= 360) {
                    angle = 0;
                } else {
                    angle = angle +2;
                }
            }
        };

        timer.schedule(task,0,speed);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);

        mPaint.setColor(inColor);
        canvas.drawCircle(getWidth()/2,getHeight()/2,circleWidth/2,mPaint);

        RectF oval = new RectF(getWidth()/2 - circleWidth/2,getHeight()/2-circleWidth/2,
                getWidth()/2+circleWidth/2,
                getHeight()/2 + circleWidth/2);

        mPaint.setColor(arcColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval,45,angle,false,mPaint);
    }


}
