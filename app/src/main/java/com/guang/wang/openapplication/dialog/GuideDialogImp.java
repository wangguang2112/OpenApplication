package com.guang.wang.openapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by wangguang.
 * Date:2016/9/19
 * Description:引导dialog，背景无色
 */
public class GuideDialogImp extends Dialog implements GuideDialog{
    private boolean isPassTouchEventToActivity=false;
    private Context activityContext;
    //默认关闭自己
    private OnReturnListener mListener;
    private  boolean isReturnKeyCanUse=true;
    public GuideDialogImp(Context context) {
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        activityContext=context;
        mListener=new OnReturnListener() {
            @Override
            public void onReturnListener() {
                GuideDialogImp.this.dismiss();
            }
        };
        setCancelable(false);
    }
    @Override
    public void setCanTouchOut(boolean flag) {
        isPassTouchEventToActivity=flag;
    }

    @Override
    public void setCanReturn(boolean flag, @Nullable OnReturnListener onReturnListener) {
        isReturnKeyCanUse=flag;
        if(flag&&onReturnListener!=null){
            mListener=onReturnListener;
        }
    }


    /**
     * 重写事件传递能保证事件能传给Activity
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag=super.onTouchEvent(event);
        if(!flag&&isPassTouchEventToActivity&&activityContext instanceof Activity){
            flag|=((Activity)activityContext).dispatchTouchEvent(event);
        }
        return flag;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isReturnKeyCanUse&&keyCode==KeyEvent.KEYCODE_BACK){
            if(mListener!=null) {
                mListener.onReturnListener();
            }
        }
        return super.onKeyDown(keyCode, event);

    }


}
