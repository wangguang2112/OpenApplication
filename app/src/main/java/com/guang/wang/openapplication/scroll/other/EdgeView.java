package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.LinearLayout;


/**
 * TODO: document your custom view class.
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
        Log.d(TAG,willNotDraw()+"");
        setWillNotDraw(false);
        Log.d(TAG,willNotDraw()+"");
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
