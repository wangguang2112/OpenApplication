package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by wangguang.
 * Date:2016/10/21
 * Description:
 */

public class UseScrollerView extends FrameLayout{
    public Scroller mScroller;
    int i=-1;
    boolean canRun=true;
    boolean isRunning=false;
    Runnable r=new Runnable() {
        @Override
        public void run() {
            if(!mScroller.isFinished()){
                mScroller.forceFinished(true);
            }
            mScroller.startScroll(getScrollX(),getScrollY(),getScrollX(),i*200);
            invalidate();
            i=-i;
            if(canRun) {
                postDelayed(this, 300);
            }else{
                isRunning=false;
            }
        }
    };
    public UseScrollerView(Context context) {
        super(context);
        init(context);
    }

    public UseScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UseScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller=new Scroller(getContext());
        setWillNotDraw(false);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
    public void startAnim(){
        if(!isRunning) {
            isRunning=true;
            canRun=true;
            this.postDelayed(r, 250);
        }
    }

    public void stopAnim(){
        if(isRunning) {
            this.canRun=false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopAnim();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                startAnim();
                break;
        }

        return super.onTouchEvent(event)||true;
    }
}
