package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by wangguang.
 * Date:2016/10/26
 * Description:
 */

public class UserDragHelpView extends FrameLayout {

    ViewDragHelper mHelper;
    ViewDragHelper.Callback callback;
    public UserDragHelpView(Context context) {
        super(context);
        init();
    }

    public UserDragHelpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserDragHelpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        callback=new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }
        };
        mHelper =ViewDragHelper.create(this,callback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag=mHelper.shouldInterceptTouchEvent(ev);
       return flag;
//        return true;
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
}
