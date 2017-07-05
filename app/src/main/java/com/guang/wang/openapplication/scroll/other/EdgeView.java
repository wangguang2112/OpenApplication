package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.LinearLayout;


/**
 *
 */
public class EdgeView extends ViewGroup {
    public static final String TAG = "EdgeView";
    private EdgeEffect topedge;
    private EdgeEffect bottomedge;
    int i=0;
    public EdgeView(Context context) {
        super(context);
        init(context,null, 0);
    }

    public EdgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public EdgeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void init(Context context,AttributeSet attrs, int defStyle) {
        topedge=new EdgeEffect(context);
        bottomedge=new EdgeEffect(context);
//        setWillNotDraw(true);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");
//        Paint p=new Paint();
//        p.setColor(Color.WHITE);
//        p.setStrokeWidth(5);
//        p.setTextSize(30);
//        Paint pb=new Paint(Color.BLACK);
//        canvas.drawOval(new RectF(295,295,305,305),pb);
//        pb.setStyle(Paint.Style.STROKE); //设置空心
//        pb.setStrokeWidth(4); //设置圆环的宽度
//        pb.setAntiAlias(true);//锯齿
//        canvas.drawCircle(300,300,280,pb);
//
//
//        for(int i=0;i<12;i++){
//            canvas.rotate(30,300,300);
//            canvas.drawText(i+1+"",285,45,p);
//        }
//
                Paint mPaint=new Paint();
        mPaint.setColor(Color.GRAY);
        final int count = canvas.save();

        final float centerX = 300;
        final float centerY = 300;

        canvas.scale(1.3f, 1.f , centerX, 0);

        final float displacement = Math.max(0, 1.f) - 0.5f;
        float translateX = 600 * displacement / 2;

        canvas.clipRect(new Rect(100,100,500,500));
        canvas.translate(0, translateX);
        mPaint.setAlpha((int) (0xff * 0xaaaaaa));
        canvas.drawCircle(centerX, centerY, 200, mPaint);
        canvas.restoreToCount(count);

    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG,"draw");
        super.draw(canvas);

    }
    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                topedge.onAbsorb(199);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                topedge.onPull(0.8f);
//                postInvalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                if (!topedge.isFinished()) {
//                    topedge.onRelease();
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                if (!topedge.isFinished()) {
//                    topedge.onRelease();
//                }
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int width=getWidth();
//        int height=getHeight();
//        topedge.setSize(width,height);
//        topedge.draw(canvas);
////        bottomedge.setSize(width,height);
//    }

}
