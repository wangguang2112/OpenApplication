package com.wuba.client.roll;

import com.wuba.client.roll.inter.Chain;
import com.wuba.client.roll.inter.IRollDialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by wangguang.
 * Date:2017/9/21
 * Description:
 */

public class BaseRollDialog extends Dialog implements IRollDialog {

    Chain mChain;

    boolean mNext = false;

    public BaseRollDialog(Context context) {
        super(context);
    }

    public BaseRollDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseRollDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public boolean showDialog() {
        Log.d("BaseRollDialog", "showDialog: " + priority());
        this.show();
        return true;
    }

    @Override
    public void preload() {

    }

    @Override
    public void reset() {
        mNext = false;
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public void setChain(Chain chain) {
        mChain = chain;
    }

    protected void next() {
        if (mChain != null && !mNext) {
            mChain.next();
            mNext = true;
        }

    }

    protected void stop() {
        if (mChain != null) {
            mChain.stop();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        next();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " priority:" + priority();
    }
}
