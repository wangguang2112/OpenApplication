package com.guang.wang.openapplication.scroll.nestedscroller;

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.support.v4.view.NestedScrollingChild;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangguang.
 * Date:2016/11/7
 * Description:
 */

public class NestedChildView extends View implements NestedScrollingChild {
    public static final String TAG="NestedChildView";
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];
    private final NestedScrollingChildHelper childHelper = new NestedScrollingChildHelper(this);

    public NestedChildView(Context context) {
        super(context);
        init();
    }

    public NestedChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public float downY;
    private void init() {
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionMasked=event.getActionMasked();
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
                downY=event.getY();
                if(downY==-1){
                    return false;
                }
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = event.getY() - downY;
                Log.d(TAG,"deltaY:"+deltaY);
                //offsetInWindow这个表示父布局在窗口上的相对位移
                if(dispatchNestedPreScroll(0,(int)deltaY,consumed,offsetInWindow)){
                    deltaY-=consumed[1];
                    Log.d(TAG,"child deltaY:"+deltaY);
                }
                //floor去地板 abs去绝对值=
                if(Math.floor(Math.abs(deltaY))==0){
                    deltaY=0;
                }
                this.setY(Math.min(Math.max(getY() + deltaY, 0), ((View) getParent()).getHeight() - getHeight()));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.d(TAG, "setNestedScrollingEnabled() called with: enabled = [" + enabled + "]");
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.d(TAG, "isNestedScrollingEnabled() called");
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.d(TAG, "startNestedScroll() called with: axes = [" + axes + "]");
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.d(TAG, "stopNestedScroll() called");
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        Log.d(TAG, "hasNestedScrollingParent() called");
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        Log.d(TAG, "dispatchNestedScroll() called with: dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed
                + "], offsetInWindow = [" + offsetInWindow + "]");
        return childHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        Log.d(TAG, "dispatchNestedPreScroll() called with: dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed[1] + "], offsetInWindow = [" + offsetInWindow[1] + "]");
        return childHelper.dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "dispatchNestedFling() called with: velocityX = [" + velocityX + "], velocityY = [" + velocityY + "], consumed = [" + consumed + "]");
        return childHelper.dispatchNestedFling(velocityX,velocityY,consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.d(TAG, "dispatchNestedPreFling() called with: velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
        return childHelper.dispatchNestedPreFling(velocityX,velocityY);
    }
}
