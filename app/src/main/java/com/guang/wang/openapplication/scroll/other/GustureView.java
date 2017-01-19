package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wangguang.
 * Date:2016/12/28
 * Description:
 */

public class GustureView extends View {
    
    GestureDetector mDetector;

    private String TAG="GustureView";

    public GustureView(Context context) {
        super(context);
        init();
    }

    public GustureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GustureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG, "onTouchEvent: init");
        mDetector=new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG, "onDown() called with: e = [" + e + "]");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG, "onShowPress() called with: e = [" + e + "]");
                Toast.makeText(getContext(), "press", Toast.LENGTH_SHORT)
                     .show();
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp() called with: e = [" + e + "]");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll() called with: e1 = [" + e1 + "], e2 = [" + e2 + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress() called with: e = [" + e + "]");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling() called with: e1 = [" + e1 + "], e2 = [" + e2 + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume=mDetector.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent: consume"+consume);
        return consume;
    }

    /**
     * 这里进行动画的停止
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}
