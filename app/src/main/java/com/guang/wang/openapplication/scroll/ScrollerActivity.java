package com.guang.wang.openapplication.scroll;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.scroll.other.UseScrollerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScrollerActivity extends BaseActivity {
    UseScrollerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        view= (UseScrollerView) findViewById(R.id.scroller_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.stopAnim();
    }
}
