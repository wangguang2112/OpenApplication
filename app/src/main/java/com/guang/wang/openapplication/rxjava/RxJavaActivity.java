package com.guang.wang.openapplication.rxjava;

import com.guang.wang.openapplication.R;
import com.jakewharton.rxbinding.view.RxView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.MainThreadSubscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {
    ActivityCallBakck callback;
    Button mButton;
    Button mJumpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        mButton = (Button) findViewById(R.id.button);
        mJumpButton= (Button) findViewById(R.id.jumpbutton);
        setClick();
    }

    void setClick() {
        //RxJava 一体化实现
        RxView.clicks(mButton).doOnNext(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.d("wangguang", "1");
            }
        }).observeOn(Schedulers.immediate())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Log.d("wangguang", "2");
                        rxjavaTest1();
                    }
                });
        RxView.clicks(mJumpButton)
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Void, Observable<Intent>>() {
                    @Override
                    public Observable<Intent> call(Void aVoid) {
                        final Intent intent=new Intent(RxJavaActivity.this,RxJavaForResultActivty.class);
                        startActivityForResult(intent,0);
                        return Observable.create(new Observable.OnSubscribe<Intent>() {
                            @Override
                            public void call(Subscriber<? super Intent> subscriber) {
                                callback=new ActivityCallBakck(subscriber);
                                subscriber.add(new MainThreadSubscription() {
                                    @Override
                                    protected void onUnsubscribe() {
                                        callback=null;
                                    }
                                });
                            }
                        });
                    }
                })
                .subscribe(new Action1<Intent>() {
            @Override
            public void call(Intent intent) {
               Log.d("wang","back");
            }
        });
    }

    /**
     * 调度器的使用
     */
    void rxjavaTest1() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("123");
                Log.d("wang", "Observable:id::" + Thread.currentThread().getId());
                try {
                    Thread.sleep(3000);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                /**
                 * Subscriber运行时的线程
                 * onNext,onError,onCompleted运行时的线程
                 * 同样会作用于变换（map） 变换会返回一个Observable,
                 * 为这个线程从新分配线程分布
                 */
                .observeOn(AndroidSchedulers.mainThread())
                /**
                 * onSubscirbe运行时的线程
                 * 注意 subscribeOn只在第一次时有用
                 * 因为原始的Obsevable只调用了一次
                 */
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        Log.d("wang", "map:id::" + Thread.currentThread().getId());
                        return s;
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("wang", "over");
                        Log.d("wang", "onCompleted:id::" + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("wang", e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("wang", s);
                        Log.d("wang", "onNext:id::" + Thread.currentThread().getId());
                    }
                });
    }
    class ActivityCallBakck{
        Subscriber<? super Intent> subscriber;
        ActivityCallBakck(Subscriber<? super Intent> subscriber){
            this.subscriber=subscriber;
        }
        public void doBack(Intent intent){
            subscriber.onNext(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0&&callback!=null){
            callback.doBack(data);
        }
    }
}
