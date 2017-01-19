package com.guang.wang.openapplication.cache;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangguang.
 * Date:2017/1/4
 * Description:
 */

public class StatisticsView extends View {

    private static final String TAG = "StatisticsView";

    int mColor=Color.BLUE;
    int FACTOR=4;
    List<Float> mPreInt=new ArrayList<>();
    int curentX=0;
    public StatisticsView(Context context) {
        super(context);
    }

    public StatisticsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setNum(float f){
        Log.d(TAG, "setNum() called with: f = [" + f + "]");
        mPreInt.add(f);
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height=getHeight();
        int width=getWidth();
        Paint p=new Paint();
        p.setColor(mColor);
        p.setStrokeWidth(FACTOR);
        int size=mPreInt.size();
        if(size*FACTOR<width) {
            for(int i=0;i<size;i++) {
                canvas.drawLine(i*FACTOR, height, i*FACTOR, height-mPreInt.get(i) * height, p);
            }
        }else{
            for(int i=0;i<width/FACTOR;i++) {
                canvas.drawLine(i*FACTOR, height, i*FACTOR, height-mPreInt.get(i+size-width/FACTOR) * height, p);
            }
        }
        Paint borderP=new Paint();
        borderP.setStrokeWidth(FACTOR);
        borderP.setColor(Color.GREEN);
        canvas.drawLine(0, 0, width,0, borderP);
        canvas.drawLine(0, height, width,height, borderP);
        canvas.drawLine(0, height/2, width,height/2, borderP);
    }
    public void clearNum(){
        mPreInt.clear();
    }
}
