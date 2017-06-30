package com.guang.wang.openapplication.puglin;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

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

    public final static String NAME="com.guang.wang.testdemo.TestClass";
    public final static String METHOD="getString";
    public final static String FIELD="adc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button2)
    public void onClick(View v) {
        String optpath=getFilesDir().getAbsolutePath();
        String dexfile=Environment.getExternalStorageDirectory().getAbsolutePath()+"/app-debug.apk";
        Log.e("wangguang",dexfile);
        DexClassLoader classLoader=new DexClassLoader(dexfile,optpath,null,this.getClassLoader());
        try {
            Class cls=classLoader.loadClass(NAME);
            Field f=cls.getDeclaredField(FIELD);

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
}
