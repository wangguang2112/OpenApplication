package com.guang.wang.openapplication.rxjava;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by wangguang.
 * Date:2017/1/4
 * Description:
 */

public class OneSubScriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.w("OneSubScriber", "onError: "+e.getMessage() );
    }

    @Override
    public void onNext(T t) {

    }


}
