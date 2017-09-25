package com.guang.wang.openapplication.puglin;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.roll.BaseRollDialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wangguang.
 * Date:2017/9/22
 * Description:
 */

public class RootDialog extends BaseRollDialog implements View.OnClickListener {

    static int i = 10;

    int priority = i--;

    public RootDialog(Context context) {
        super(context);
    }

    public RootDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected RootDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public void preload() {
        super.preload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_dialog_layout);
        TextView view = (TextView) findViewById(R.id.dialog_text);
        view.setOnClickListener(this);
        view.setText(priority() + "");

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

}