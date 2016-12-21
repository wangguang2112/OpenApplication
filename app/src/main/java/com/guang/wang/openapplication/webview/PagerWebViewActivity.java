package com.guang.wang.openapplication.webview;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.adapter.WebFragmtnPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PagerWebViewActivity extends BaseActivity {
    ViewPager mViewPager;
    List<String> data=new ArrayList<>();
    WebFragmtnPagerAdapter mWebFragmtnPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_web_view);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
        for(int i=0;i<=60;i++){
            data.add("http://www.baidu.com");
        }
        mWebFragmtnPagerAdapter=new WebFragmtnPagerAdapter(getSupportFragmentManager(),data);
        mViewPager.setAdapter(mWebFragmtnPagerAdapter);
    }
}
