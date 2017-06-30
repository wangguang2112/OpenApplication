package com.guang.wang.openapplication.scroll;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.scroll.other.IView;
import com.wuba.bangbang.uicomponents.imageview.gestures.GestureDetector;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class GustureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gusture);
        ViewGroup v= (ViewGroup) getWindow().getDecorView();
        v.addView(new IView(this));

    }
}
