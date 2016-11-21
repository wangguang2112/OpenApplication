package com.guang.wang.openapplication.scroll.nestedscroller;

import android.content.Context;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.support.v4.view.NestedScrollingParent;

/**
 * Created by wangguang.
 * Date:2016/11/8
 * Description:
 */

public class NestedParentView extends FrameLayout implements NestedScrollingParent {

    private static final String TAG ="NestedParentView" ;

    NestedScrollingParentHelper mHelper=new NestedScrollingParentHelper(this);
    public NestedParentView(Context context) {
        super(context);
    }

    public NestedParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll() called with: target = [" + "view" + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed[1] + "]");
        final float shouldMoveY=getY();

        final View parent=(View)getParent();
        int consumedY;
        if(shouldMoveY<=0){
            consumedY=- (int) getY();
        }else if(shouldMoveY>=parent.getHeight()-getHeight()){
            consumedY= (int) (parent.getHeight()-getHeight()-getY());
        }else{
            consumedY=dy;
        }
        setY(getY()+consumedY);
        consumed[1]=consumedY;
    }

   /*mHelper帮忙处理的方法*/
    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onNestedScrollAccepted() called with: child = [" + "child" + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
        mHelper.onNestedScrollAccepted(child,target,nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.d(TAG, "onStopNestedScroll() called with: target = [" + "child" + "]");
        mHelper.onStopNestedScroll(target);
    }

    @Override
    /**
     * axes为轴心的意思 这里用来判断是横向的还是纵向的
     */
    public int getNestedScrollAxes() {
        Log.d(TAG, "getNestedScrollAxes() called");
        return mHelper.getNestedScrollAxes();
    }
    /*mHelper帮忙处理的方法结束*/

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll() called with: child = [" + "child" + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
        return true;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG,
                "onNestedScroll() called with: target = [" + "child" + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = ["
                        + dyUnconsumed + "]");
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling() called with: target = [" + "child" + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "], consumed = [" + consumed + "]");
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling() called with: target = [" + "child" + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
        return true;
    }


}
