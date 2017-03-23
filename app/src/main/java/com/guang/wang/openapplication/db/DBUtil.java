package com.guang.wang.openapplication.db;

import com.github.hf.leveldb.LevelDB;
import com.github.hf.leveldb.Snapshot;
import com.github.hf.leveldb.exception.LevelDBException;

import android.content.Context;
import android.util.Log;

/**
 * Created by wangguang.
 * Date:2017/1/9
 * Description:
 */

public class DBUtil {
    public static void test(Context context) throws LevelDBException {
        String path= context.getExternalFilesDir("db").getAbsolutePath();
        Log.d("wangguang", "test: path="+path);
        LevelDB mLevelDB=LevelDB.open(path+"/a.ldb");
        mLevelDB.put("hello".getBytes(),"world".getBytes());
//        Snapshot h=mLevelDB.obtainSnapshot();

//        mLevelDB.put("hello".getBytes(), "brave-new-world".getBytes());

//        Log.d("wangguang",new String(mLevelDB.get("hello".getBytes(), h)));
        Log.d("wangguang",new String(mLevelDB.get("hello".getBytes())));

//        mLevelDB.releaseSnapshot(h);

        mLevelDB.close();



    }
}
