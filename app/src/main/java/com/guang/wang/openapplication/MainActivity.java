package com.guang.wang.openapplication;

import com.guang.wang.openapplication.dialog.DialogActivity;
import com.guang.wang.openapplication.okhttp.OkhttpMainActivity;
import com.guang.wang.openapplication.rxjava.RxJavaActivity;
import com.guang.wang.openapplication.scroll.DragHelperActivity;
import com.guang.wang.openapplication.scroll.ScrollActivity;
import com.guang.wang.openapplication.scroll.ScrollerActivity;
import com.guang.wang.openapplication.scroll.TScrollActivity;
import com.guang.wang.openapplication.syn.AsynActivity;
import com.guang.wang.openapplication.webview.WebViewActivity;

import org.w3c.dom.Text;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//自带生命周期测算
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button dialogTV;

    private Button okhttpTV;

    private Button rxjavaTV;

    private Button scrollTV;

    private Button synTV;

    private Button webTV;

    private Button appTV;

    private Button tscrollTV;

    private Button scroller;

    private Button drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("wangguang", "Activty::onCreate");
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(new MyFramgment(), "test").commit();
        dialogTV= (Button) findViewById(R.id.dialog);
        okhttpTV= (Button) findViewById(R.id.okhttp);
        rxjavaTV= (Button) findViewById(R.id.rxjava);
        scrollTV=(Button)findViewById(R.id.scroll);
        synTV= (Button) findViewById(R.id.asyn);
        webTV= (Button) findViewById(R.id.web);
        appTV= (Button) findViewById(R.id.application);
        tscrollTV= (Button) findViewById(R.id.tscroll);
        scroller= (Button) findViewById(R.id.scroller);
        drag= (Button) findViewById(R.id.dragerhelp);
        tscrollTV.setOnClickListener(this);
        dialogTV.setOnClickListener(this);
        okhttpTV.setOnClickListener(this);
        rxjavaTV.setOnClickListener(this);
        scrollTV.setOnClickListener(this);
        synTV.setOnClickListener(this);
        webTV.setOnClickListener(this);
        appTV.setOnClickListener(this);
        scroller.setOnClickListener(this);
        drag.setOnClickListener(this);
    }

    public  void call(String number, Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("拨打电话");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        call("0311-88343586",this);
        return true ;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.application:
                String action = Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS;
                Intent intent = new Intent(action);
                startActivity(intent);
                finish();
                break;
            case R.id.dialog:
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                break;
            case R.id.okhttp:
                startActivity(new Intent(MainActivity.this, OkhttpMainActivity.class));
                break;
            case R.id.rxjava:
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                break;
            case R.id.scroll:
                startActivity(new Intent(MainActivity.this, ScrollActivity.class));
                break;
            case R.id.web:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.asyn:
                startActivity(new Intent(MainActivity.this, AsynActivity.class));
                break;
            case R.id.tscroll:
                startActivity(new Intent(MainActivity.this, TScrollActivity.class));
                break;
            case R.id.scroller:
                startActivity(new Intent(MainActivity.this, ScrollerActivity.class));
                break;
            case R.id.dragerhelp:
                startActivity(new Intent(MainActivity.this, DragHelperActivity.class));
                break;
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
