package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wangguang.
 * Date:2016/11/3
 * Description:
 */

public class DrawerView extends FrameLayout {

    private static final String TAG = "DrawerView";

    ViewDragHelper mHelper;

    ViewDragHelper.Callback callback;

    Context mContext;

    int vw;

    View menu;
    View main;

    public DrawerView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        callback = new ViewDragHelper.Callback() {
            int left=0;
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child==main;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int x=(xvel>300||xvel>0&&(vw-left)<200)
                        ||
//                        ((xvel< 0&&xvel>-300)||xvel<0&&left>100)
                       !(xvel<-300||xvel<=0&&left<200)
                        ?vw:0;
//                if(xvel>300){
//                    x=vw;
//                }else if(xvel>0&&vw-left<200){
//                    x=vw;
//                }else if(xvel>0){
//                    x=0;
//                } else if(xvel<-300){
//                    x=0;
//                }else if(xvel<0&&left<200){
//                    x=0;
//                }else if(xvel<0){
//                    x=vw;
//                }
                mHelper.smoothSlideViewTo(releasedChild, x, 0);
//                不用这个有很强的顿挫感
                DrawerView.this.postInvalidateOnAnimation();
                Log.d(TAG,"left="+left+"  xvel="+xvel+"  x="+x);

            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                Log.d(TAG, "onViewPositionChanged() called with: changedView = [" + changedView + "], left = [" + left + "], top = [" + top + "], dx = [" + dx + "], dy = [" + dy + "]");
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                menu.offsetLeftAndRight(dx);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left <= vw && left >= 0) {
                    this.left=left;
                } else if(left>vw){
                    this.left= vw;
                }else {
                    this.left = 0;
                }
                return this.left;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return super.clampViewPositionVertical(child, top, dy);
            }
        };
        mHelper = ViewDragHelper.create(this, callback);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(mHelper.continueSettling(true)){
            this.postInvalidateOnAnimation();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        至于为什么要流出一个像素 我也不知道 maybe abug
        vw=menu.getMeasuredWidth()-1;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menu=getChildAt(0);
        main=getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //默认隐藏
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(this.getChildCount()!=2){
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        int vn = menu.getMeasuredHeight();
        menu.layout(left-vw, top, left+1, top + vn);
    }
}
