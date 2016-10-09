package com.guang.wang.openapplication.dialog;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangguang.
 * Date:2016/9/19
 * Description:引导
 */
public interface GuideDialog {

    public void show();

    public void dismiss();

    public void setContentView(@LayoutRes int layoutId);

    public void setContentView(View view, ViewGroup.LayoutParams params);

    /**
     * 设置是否可以使用外部view，只用当设置点击外部不能关闭是才有效
     */
    public void setCanTouchOut(boolean flag);

    public void setCanceledOnTouchOutside(boolean flag);

    public void setCanReturn(boolean flag,@Nullable OnReturnListener onReturnListener);

}
