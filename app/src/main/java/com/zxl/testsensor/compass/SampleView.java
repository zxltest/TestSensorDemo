package com.zxl.testsensor.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SampleView extends View {
    private Paint mPaint;
    private Path mPath;
    private float[] mValues;
    private boolean isMeasured;
    private int mWidth, mHeight;

    public SampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        initMeasure();
        if (mValues != null) {
            Log.e("TAG", "==" + (180 - mValues[0]));
            canvas.rotate(180 - mValues[0], mWidth / 2, mHeight / 2);//180代表正南方
        }
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mPaint);
        canvas.drawPath(mPath, mPaint);

    }

    public void initMeasure() {
        if (!isMeasured) {
            isMeasured = true;
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            initPath(mWidth, mHeight);
        }
    }

    public void initPath(int width, int height) {
        mPath.reset();
        mPath.moveTo(width / 2, height / 2 - 50);
        mPath.lineTo(width / 2 - 20, height / 2 + 60);
        mPath.lineTo(width / 2, height / 2 + 50);
        mPath.lineTo(width / 2 + 20, height / 2 + 60);
        mPath.close();
    }

    public void setValues(float[] mValues) {
        this.mValues = mValues;
    }
}