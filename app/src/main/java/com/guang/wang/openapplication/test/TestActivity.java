package com.guang.wang.openapplication.test;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText edt;
     final Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        edt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d(TAG, "onKey:"+keyEvent.getAction()+":"+i);
                return false;
            }
        });
    }

    @OnClick(R.id.textView1)
    void onText1Click(View view){
        Log.d(TAG, "onText1Click: ");
        final AlertDialog dialog=new AlertDialog
          .Builder(TestActivity.this)
                .setMessage("123")
                .setTitle("234")
                .setPositiveButton("1234", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                TestActivity.this.finish();
                dialog.show();
            }
        },4000);
        if(view instanceof TextView){
            ((TextView) view).setText("show");
        }
    }

    @OnClick(R.id.textView2)
    void onText2Click(View view){
        Log.d(TAG, "onText2Click: ");
        this.startActivity(new Intent(this,TestActivity.class));
    }
}
