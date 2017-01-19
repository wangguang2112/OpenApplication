package com.guang.wang.openapplication.db;

import com.github.hf.leveldb.exception.LevelDBException;
import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.os.Bundle;
import android.util.Log;

public class LevelDbActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_db);
        try {
            Log.d("wangguang", "onCreate: ");
            DBUtil.test(this);
        } catch (LevelDBException e) {
            e.printStackTrace();
        }
    }
}
