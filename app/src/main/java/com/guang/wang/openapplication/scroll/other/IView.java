package com.guang.wang.openapplication.scroll.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wangguang.
 * Date:2017/4/13
 * Description:
 */

public class IView extends View {

    public IView(Context context) {
        super(context);
    }

    public IView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("wangguang","IView onFinishInflate");
    }
}
