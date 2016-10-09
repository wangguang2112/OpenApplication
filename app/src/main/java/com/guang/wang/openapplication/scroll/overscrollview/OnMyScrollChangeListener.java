package com.guang.wang.openapplication.scroll.overscrollview;

/**
 * Created by wangguang.
 * Date:2016/9/30
 * Description:
 */

public interface OnMyScrollChangeListener {
    public void onMyScrollChanged(int initH,int l, int t, int oldl, int oldt);
    public void onScrollOver(int initH,int l, int t);
}
