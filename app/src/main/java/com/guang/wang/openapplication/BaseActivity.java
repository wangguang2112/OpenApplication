package com.guang.wang.openapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {
    public Handler mHandler=new Handler();

    public Handler getMainHandler(){
        return mHandler;
    }
}
