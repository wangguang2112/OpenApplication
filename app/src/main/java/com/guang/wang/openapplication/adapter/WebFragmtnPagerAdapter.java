package com.guang.wang.openapplication.adapter;

import com.guang.wang.openapplication.webview.NormalWebFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangguang.
 * Date:2016/12/15
 * Description:
 */

public class WebFragmtnPagerAdapter extends FragmentPagerAdapter{
    List<String> datas;
    public WebFragmtnPagerAdapter(FragmentManager fm,  List<String> datas) {
        super(fm);
        this.datas=datas;
    }

    @Override
    public Fragment getItem(int position) {
        NormalWebFragment fragment=new NormalWebFragment();
        fragment.setUrl(datas.get(position));
        fragment.setPosition(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
