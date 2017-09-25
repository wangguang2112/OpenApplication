package com.guang.wang.openapplication.puglin;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.roll.IRollHandler;
import com.guang.wang.openapplication.roll.IRollRootHandler;
import com.guang.wang.openapplication.roll.OnDialogRollListener;
import com.guang.wang.openapplication.roll.RollHelper;
import com.guang.wang.openapplication.roll.RootHandler;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.DexClassLoader;

public class LoadActivity extends BaseActivity {

    public final static String NAME = "com.guang.wang.testdemo.TestClass";

    public final static String METHOD = "getString";

    public final static String TAG = "LoadActivity";

    public final static String FIELD = "adc";

    IRollRootHandler handler = RollHelper.getRootHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);
        handler.beginTransaction()
               .add(new RootDialog(this))
               .add(new RootDialog(this))
               .add(new RootDialog(this))
               .commit();
        handler.beginChildTransaction("tab1")
               .add(new RootDialog(this))
               .add(new RootDialog(this))
               .add(new RootDialog(this))
               .commit();
        handler.beginChildTransaction("tab2")
               .add(new RootDialog(this))
               .add(new RootDialog(this))
               .add(new RootDialog(this))
               .commit();
        handler.preload();
        handler.setOnRollListener(new OnDialogRollListener() {
            @Override
            public void onTouchEnd(String tag) {
                Log.d(TAG, "onTouchEnd() tag = [" + tag + "]");
            }

            @Override
            public void onHandlerChange(String lastTag, String nextTag) {
                Log.d(TAG, "onHandlerChange() lastTag = [" + lastTag + "], nextTag = [" + nextTag + "]");
            }
        });
    }

    @OnClick(R.id.button0)
    public void onClick(View v) {
        String optpath = getFilesDir().getAbsolutePath();
        String dexfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/app-debug.apk";
        Log.e("wangguang", dexfile);
        DexClassLoader classLoader = new DexClassLoader(dexfile, optpath, null, this.getClassLoader());
        try {
            Class cls = classLoader.loadClass(NAME);
            Field f = cls.getDeclaredField(FIELD);

            Log.d("wangguang", (String) f.get(cls.newInstance()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button1)
    void button1(View view) {
        Log.d(TAG, "button1: ");
        handler.reset();
        handler.start();
    }

    boolean click=true;
    @OnClick(R.id.button2)
    void button2(View view) {
        Log.d(TAG, "button2: ");
        handler.trigger("tab1");
        if(click) {
            getMainHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.button3).performClick();
                }
            }, 3000);
            click=false;
        }

    }

    @OnClick(R.id.button3)
    void button3(View view) {
        Log.d(TAG, "button3: ");
        handler.trigger("tab2");
    }

    @OnClick(R.id.button4)
    void button4(View view) {
        Log.d(TAG, "button4: ");
        handler.start();
    }
}
