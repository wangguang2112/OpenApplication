package com.guang.wang.openapplication.cache;

import android.graphics.Bitmap;

/**
 * Created by wangguang.
 * Date:2017/1/3
 * Description:
 */

public class Vo {

    public Vo(String name, Bitmap bitmap) {
        this.name = name;
        mBitmap = bitmap;
    }

    public String name;

    public Bitmap mBitmap;
}
