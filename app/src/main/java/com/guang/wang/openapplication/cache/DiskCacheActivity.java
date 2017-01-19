package com.guang.wang.openapplication.cache;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.jakewharton.disklrucache.DiskLruCache;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class DiskCacheActivity extends BaseActivity {
    DiskLruCache diskLruCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_cache);
        finish();
        try {
             diskLruCache=DiskLruCache.open(getExternalCacheDir(),1,1,1024*1024*100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(diskLruCache!=null&&!diskLruCache.isClosed()){
            try {
                DiskLruCache.Editor e=diskLruCache.edit("wang");
                OutputStream out=e.newOutputStream(0);
                out.write("123".getBytes());
                out.flush();
                e.commit();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
