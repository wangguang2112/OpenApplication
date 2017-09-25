package com.guang.wang.openapplication.puglin;

import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.roll.BaseRollDialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wangguang.
 * Date:2017/9/22
 * Description:
 */

public class RootDialog2 extends BaseRollDialog implements View.OnClickListener {


    int priority = 5;

    public RootDialog2(Context context) {
        super(context);
    }

    public RootDialog2(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected RootDialog2(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    volatile boolean loadOk = false;

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public void preload() {
        super.preload();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadOk = true;
//            }
//        }, 20000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_dialog_layout);
        TextView view = (TextView) findViewById(R.id.dialog_text);
        view.setOnClickListener(this);
        view.setText(priority() + ":2");

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public boolean showDialog() {
        Log.d("wangguang", "showDialog2: "+priority()+":");
        if (System.currentTimeMillis()%2==0) {
            Log.d("wangguang", "showDialog2: "+priority() +"ok");
            return super.showDialog();
        }
        return true;
    }
}