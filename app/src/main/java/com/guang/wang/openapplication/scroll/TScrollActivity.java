package com.guang.wang.openapplication.scroll;

import com.guang.wang.openapplication.R;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class TScrollActivity extends AppCompatActivity {
    TextView t1,t2;
    Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tscroll_view);
        t1= (TextView) findViewById(R.id.textview1);
        t2= (TextView) findViewById(R.id.textview2);
        try {
            Field f=TextView.class.getDeclaredField("mText");
            f.setAccessible(true);
            f.set(t1,"wwwwww");
            f.set(t2,"wwwwww");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t2.postInvalidateOnAnimation();
                t1.postInvalidate();

            }
        },20000);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TScrollActivity.this,"t2",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
