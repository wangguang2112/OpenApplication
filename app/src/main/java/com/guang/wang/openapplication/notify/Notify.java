package com.guang.wang.openapplication.notify;


import com.guang.wang.openapplication.BaseActivity;

import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangguang.
 * Date:2016/12/14
 * Description:
 */

public class Notify implements INotify{

    String TAG="Notify";
    Map<String,Set<NotifyCallBack>> callbacks=new HashMap<>();
    static Notify mNotify=new Notify();
    public static Notify getInstance(){
        return mNotify;
    }
    private Notify(){

    }

    @Override
    public void rigisterCallBack(String action, NotifyCallBack callBack) {
        if(callbacks.containsKey(action)){
            callbacks.get(action).add(callBack);
        }else {
            Set set=new HashSet();
            set.add(callBack);
            callbacks.put(action,set);
        }
    }

    @Override
    public void unrigisterCallBack(String action, NotifyCallBack callBack) {
        if(callbacks.containsKey(action)){
            callbacks.get(action).remove(callBack);
        }
    }

    @Override
    public void clearCallBack(String action) {
        if(callbacks.containsKey(action)) {
            callbacks.get(action)
                     .clear();
        }
    }

    @Override
    public void clearAllCallBack() {
        callbacks.clear();
    }

    @Override
    public void notify(String action, Intent intent) {
        synchronized (callbacks) {
            if (callbacks.containsKey(action)) {
                Set<NotifyCallBack> set=callbacks.get(action);
                for(NotifyCallBack callBack:set){
                    callBack.nodifyCallBack(action,intent);
                }
            }
            Log.d(TAG, "notify() action = [" + action + "], intent = [" + intent + "]");
        }
    }

    @Override
    public void notifyOnMain(final String action, final Intent intent) {
        synchronized (callbacks) {
            if (callbacks.containsKey(action)) {
                Set<NotifyCallBack> set=callbacks.get(action);
                for(final NotifyCallBack callBack:set){
                    if(callBack instanceof BaseActivity) {
                        ((BaseActivity)callBack).getMainHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.nodifyCallBack(action, intent);
                            }
                        });
                    }
                }
            }
            Log.d(TAG, "notify() action = [" + action + "], intent = [" + intent + "]");
        }
    }
}
