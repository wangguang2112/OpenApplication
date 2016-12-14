package com.guang.wang.openapplication.scroll;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.scroll.other.EdgeView;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;

public class ScrollOriginActivity extends BaseActivity {

    private static final String TAG = ScrollOriginActivity.class.getSimpleName();
    Handler mHandler=new Handler();
    EdgeView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_origin);
        view= (EdgeView) findViewById(R.id.view);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                view.setBackgroundResource(R.color.colorPrimaryDark);
//                view.postInvalidate();
////                Toast.makeText(ScrollOriginActivity.this,"123",Toast.LENGTH_SHORT).show();
//            }
//        },2000);
//        view.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Field scrollYF;
//                try {
////                    Log.d(TAG,view.getScrollY()+"");
////                    view.scrollTo(0,101);
//                    scrollYF =View.class.getDeclaredField("mScrollY");
//                    scrollYF.setAccessible(true);
//                    scrollYF.set(view,100);
//                    Log.d(TAG,view.getScrollY()+"");
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                }
//                catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//
//                view.postInvalidate();
//            }
//        },2000);

    }
}
