package com.guang.wang.openapplication;

import com.guang.wang.openapplication.dialog.DialogActivity;
import com.guang.wang.openapplication.okhttp.OkhttpMainActivity;
import com.guang.wang.openapplication.rxjava.RxJavaActivity;
import com.guang.wang.openapplication.scroll.DragHelperActivity;
import com.guang.wang.openapplication.scroll.NestedScrollActivity;
import com.guang.wang.openapplication.scroll.ScrollActivity;
import com.guang.wang.openapplication.scroll.ScrollerActivity;
import com.guang.wang.openapplication.scroll.TScrollActivity;
import com.guang.wang.openapplication.syn.AsynActivity;
import com.guang.wang.openapplication.webview.WebViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//自带生命周期测算
public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    private ListView mListView;

    ArrayAdapter<String> adapter;

    private String[] texts = new String[]{"应用程序", "okhttp", "rxjava", "asyn", "dialog", "web", "scroll", "Tscroll", "Scroller", "DragHelper", "Nested"};

    private Class<? extends Activity>[] mActivities = new Class[]{null, OkhttpMainActivity.class, RxJavaActivity.class, AsynActivity.class, DialogActivity.class, WebViewActivity.class,
            ScrollActivity.class,TScrollActivity.class,ScrollerActivity.class,DragHelperActivity.class, NestedScrollActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("wangguang", "Activty::onCreate");
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(new MyFramgment(), "test").commit();
        mListView= (ListView) findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,texts);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(texts.length!=mActivities.length){
            Toast.makeText(this,"数据错误"+texts.length+"!="+mActivities.length,Toast.LENGTH_SHORT).show();
            return;
        }
        if(position==0){
            String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
            Intent intent = new Intent(action);
            intent.setData(Uri.fromParts("package","com.wuba.bangjob",null));
            try {
                getPackageManager().getPackageInfo("com.wuba.bangjob", PackageManager.GET_ACTIVITIES);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(MainActivity.this,"未安装该APP!",Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
            finish();

        }else{
            startActivity(new Intent(this,mActivities[position]));
        }
    }

    public static class MyFramgment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d("wangguang", "Fragment::onCreateView");
            return inflater.inflate(R.layout.activity_fragment, container);

        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d("wangguang", "Fragment::onAttach");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("wangguang", "Fragment::onCreate");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d("wangguang", "Fragment::onStart");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d("wangguang", "Fragment::onStop");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("wangguang", "Fragment::onDestroy");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d("wangguang", "Fragment::onPause");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("wangguang", "Fragment::onResume");
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d("wangguang", "Fragment::onViewCreated");
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d("wangguang", "Fragment::onActivityCreated");
        }

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d("wangguang", "Activty::onResumeFragments");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d("wangguang", "Activty::onAttachFragment");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("wangguang", "Activty::onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("wangguang", "Activty::onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("wangguang", "Activty::onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("wangguang", "Activty::onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("wangguang", "Activty::onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("wangguang", "Activty::onDestroy");
    }
}