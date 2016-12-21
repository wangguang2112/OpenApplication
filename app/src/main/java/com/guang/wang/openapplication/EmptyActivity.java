package com.guang.wang.openapplication;

import com.guang.wang.openapplication.notify.Notify;
import com.guang.wang.openapplication.notify.NotifyAction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("text", "456");
                Notify.getInstance()
                      .notify(NotifyAction.NOTIFY_ACTION_TEST, intent);
            }
        }, 5000);
    }
}
