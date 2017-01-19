package com.guang.wang.openapplication.cache;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.rxjava.OneSubScriber;
import com.jakewharton.rxbinding.view.RxView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LruCacheActivity extends BaseActivity {

    private static final String TAG ="LruCacheActivity" ;

    LruCache<String, Bitmap> mCache = new LruCache(6);

    @BindView(R.id.next)
    Button next;

    @BindView(R.id.my_image)
    ImageView image;

    @BindView(R.id.statistics)
    StatisticsView sview;

    @BindView(R.id.start)
    Button start;
    String[] names;

    int cor = 0;

    int hit=0;

    int count=0;

    Random r=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        ButterKnife.bind(this);
        try {
            names = getAssets().list("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initNext();
    }

    private void initNext() {
        Subscription s = RxView.clicks(next)
                               .subscribeOn(AndroidSchedulers.mainThread())
                               .doOnNext(new Action1<Void>() {
                                   @Override
                                   public void call(Void aVoid) {
//                                       setOnBusy(true);
                                       count++;
                                   }
                               })
                               .observeOn(Schedulers.io())
                               .map(new Func1<Void, Bitmap>() {
                                   @Override
                                   public Bitmap call(Void aVoid) {
                                       cor=Math.abs(r.nextInt())% names.length;
                                       String name = names[cor];
                                       String md5 = Md5Util.encode2hex(name);
                                       Bitmap bm = mCache.get(md5);
                                       if (bm != null) {
                                           Log.d(TAG, "has cache md5="+md5);
                                           hit++;

                                           return bm;
                                       } else {
                                           Log.d(TAG, "no cache md5="+md5);
                                           try {
                                               bm = compllieAssetFile(name);
                                               mCache.put(md5, bm);
                                               return bm;
                                           } catch (IOException e) {
                                               Log.w(TAG, "error name="+name);
                                               return null;
                                            }
                                       }
                                   }
                               })
                               .observeOn(AndroidSchedulers.mainThread())
                               .doAfterTerminate(new Action0() {
                                   @Override
                                   public void call() {
                                       setOnBusy(false);
                                       Log.d(TAG, "call: doAfterTerminate");
                                   }
                               })
                               .subscribe(new OneSubScriber<Bitmap>() {
                                   @Override
                                   public void onNext(Bitmap bitmap) {
                                       super.onNext(bitmap);
//                                       setOnBusy(false);
                                       if(bitmap==null){
                                           Log.d(TAG, "onNext: bitmap=null");
                                            return;
                                       }
                                       image.setImageBitmap(bitmap);
                                       if(count!=0){
                                           Log.d(TAG, "onNext: cor="+hit+"count"+count);
                                           sview.setNum((float)hit/count);
                                       }

                                   }
                               });
        addSubscription(s);
    }

    private Bitmap compllieAssetFile(String name) throws IOException {
        InputStream in = getAssets().open("image/"+name);
        return BitmapFactory.decodeStream(in);
    }

    @OnClick(R.id.clear)
    void clearClick(View v) {
        mCache.evictAll();
        cor=0;
        sview.clearNum();
        sview.postInvalidate();
    }
    boolean isStop=false;

    @OnClick(R.id.start)
    void startClick(View v){
        if(start.getText().toString().equals("start")){
            start.setText("stop");
            isStop=false;
            getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    if(!isStop) {
                        next.performClick();
                        getMainHandler().postDelayed(this,500);
                    }
                }
            });
        }else{
            isStop=true;
            start.setText("start");
        }
    }
}
