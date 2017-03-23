package com.guang.wang.openapplication.annotation;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.os.Bundle;

public class AnnotaActivity extends BaseActivity {

//    @AutoVo(name = "MyVo", count = 1)
//    @IntField(name = {"value", "value2", "value3"})
//    @ShortField(name = "value4")
//    @StringField(name = "value5")
//    MyVo vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annota);
//        vo=new MyVo();
//        int a = vo.value3;
    }
}
