package com.guang.wang.openapplication.service;


import com.guang.wang.openapplication.BuildConfig;

import android.util.Log;

/**
 * Created by wangguang.
 * Date:2017/5/27
 * Description:可自定义在某个类打开
 * （因为涉及多个类一起调用，不好管理，这里直接使用实例）
 */

public class WGLog {
    boolean packDebug= BuildConfig.DEBUG;
    boolean isdebug;

    public WGLog(boolean isdebug){
        this.isdebug=isdebug;
    }
    public static WGLog init(boolean isDebug){
        return new WGLog(isDebug);
    }
    public void d(String tag, String msg){
        if(isdebug&&packDebug){
            Log.d(tag, msg);
        }
    }
    public void w(String tag, String msg){
        if(isdebug&&packDebug){
            Log.w(tag, msg);
        }

    }
    public void e(String tag, String msg){
        if(isdebug&&packDebug){
            Log.e(tag, msg);
        }

    }
    public void i(String tag, String msg){
        if(isdebug&&packDebug){
            Log.i(tag, msg);
        }

    }
}
