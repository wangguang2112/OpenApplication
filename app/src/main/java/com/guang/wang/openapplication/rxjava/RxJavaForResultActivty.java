package com.guang.wang.openapplication.rxjava;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RxJavaForResultActivty extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_for_result_activty);
       setResult(0);

    }
}
