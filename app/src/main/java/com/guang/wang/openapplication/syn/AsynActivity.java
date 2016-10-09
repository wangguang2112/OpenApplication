package com.guang.wang.openapplication.syn;

import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.rxjava.RxJavaActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class AsynActivity extends AppCompatActivity {

    TextView t;
   static  Handler mHandler= new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           Log.d("wang","handleMessage:msg:what="+msg.what);
           Log.d("wang","Handler Thread id="+Thread.currentThread().getId());
       }
   };
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn);
        t= (TextView) findViewById(R.id.popwindow1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("wang","Thread id="+Thread.currentThread().getId());
                mHandler.sendEmptyMessage(1);
                Log.d("wang","Thread"+"llll");
            }
        }).start();
        Log.d("wang","MainThread id="+Thread.currentThread().getId());
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("123");
        dialog= builder.create();


        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                popupWindow.showAsDropDown(t);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("wang","MainThread onReusume id="+Thread.currentThread().getId());

        Intent intent=new Intent(this, RxJavaActivity.class);
        startActivity(intent);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
                Log.d("wang123","show");
            }
        },1000);
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
//        if(info != null && info.size() > 0){
//            ComponentName component = info.get(0).topActivity;
//            Log.d("wang123",component.getClassName());
//            Log.d("wang123",this.getClass().getName());
//        }

    }
}
