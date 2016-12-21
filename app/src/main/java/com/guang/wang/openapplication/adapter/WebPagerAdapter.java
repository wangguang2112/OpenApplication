package com.guang.wang.openapplication.adapter;

import com.guang.wang.openapplication.webview.NormalWebFragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangguang.
 * Date:2016/12/15
 * Description:
 */

public class WebPagerAdapter extends PagerAdapter {



    List<String> datas;
    Context mContext;
    public WebPagerAdapter(Context context,List<String> datas) {
        this.mContext=context;
        this.datas = datas;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NormalWebFragment fragment=new NormalWebFragment();
        return super.instantiateItem(container, position);
    }


    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }



    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return true;
    }
}
