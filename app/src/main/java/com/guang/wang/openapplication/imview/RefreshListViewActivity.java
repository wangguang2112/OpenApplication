package com.guang.wang.openapplication.imview;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.wuba.bangbang.uicomponents.IMListView;
import com.wuba.bangbang.uicomponents.pictureediter.cropwindow.handle.Handle;
import com.wuba.bangbang.uicomponents.pulltorefresh.ILoadingLayout;
import com.wuba.bangbang.uicomponents.pulltorefresh.PullToRefreshBase;
import com.wuba.bangbang.uicomponents.pulltorefresh.PullToRefreshListView;
import com.wuba.bangbang.uicomponents.pulltorefresh.internal.IMLoadingLayout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RefreshListViewActivity extends BaseActivity {
    PullToRefreshListView mListView;
    List<String> list= new ArrayList<String>();
    ArrayAdapter<String> mAdapter;
    Handler mHandler=new Handler();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list_view);
        mListView= (PullToRefreshListView) findViewById(R.id.activity_refresh_list_view);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout layout=mListView.getLoadingLayoutProxy(false,true);
        layout.setRefreshingLabel("lalalalal");
        layout.setPullLabel("啦啦啦啦。。.");
        layout.setReleaseLabel("请稍后再拉。。。");
        for(int i=0;i<20;i++) {
            list.add("1111111");
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(mAdapter);


        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<IMListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<IMListView> refreshView) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0,String.valueOf(count+1)+"       "+"1");
                        mListView.onRefreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                },1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<IMListView> refreshView) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count++<4){
                            list.add(String.valueOf(count+1)+"       "+"1");
                            list.add(String.valueOf(count+1)+"       "+"1");
                            list.add(String.valueOf(count+1)+"       "+"1");
                            list.add(String.valueOf(count+1)+"       "+"1");
                            list.add(String.valueOf(count+1)+"       "+"1");
                            list.add(String.valueOf(count+1)+"       "+"1");
                        }
                        mListView.onRefreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                },1000);
            }
        });

    }
}
