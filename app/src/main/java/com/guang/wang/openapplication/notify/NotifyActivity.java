package com.guang.wang.openapplication.notify;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.EmptyActivity;
import com.guang.wang.openapplication.R;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class NotifyActivity extends BaseActivity implements NotifyCallBack{
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        mTextView= (TextView) findViewById(R.id.notifytext);
        mTextView.setText("123");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NotifyActivity.this, EmptyActivity.class);
                startActivity(intent);
            }
        });
        Notify.getInstance().rigisterCallBack(NotifyAction.NOTIFY_ACTION_TEST,this);
    }

    @Override
    public void nodifyCallBack(String action, Intent intent) {
        String text=intent.getStringExtra("text");
        if(!TextUtils.isEmpty(text)){
            this.mTextView.setText(text);
        }
    }
}
