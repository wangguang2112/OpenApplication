package com.guang.wang.openapplication.cache;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.Thread.sleep;

public class OOMActivity extends BaseActivity {

    private static final String TAG = "OOMActivity";

    @BindView(R.id.my_image)
    ImageView mImageView;

    int courrent = 0;

    boolean isStop = false;

    ArrayList<Bitmap> mData = new ArrayList<>();


    Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                Vo vo = (Vo) msg.obj;
                mData.add(vo.mBitmap);
                Log.d(TAG, "handleMessage: add");
                return true;
            }
            return false;
        }
    });

    Runnable updataR = new Runnable() {
        @Override
        public void run() {
            try {
                String[] names = getAssets().list("");
                while (true) {
                    for (String name : names) {
                        if (name.endsWith(".jpg") || name.endsWith(".png")) {
                            Bitmap bitmap;
                            InputStream in = getAssets().open(name);
                            bitmap = BitmapFactory.decodeStream(in);
                            Message msg = Message.obtain();
                            msg.obj = new Vo(name, bitmap);
                            msg.what = 1;
                            mHandler.sendMessage(msg);

                            if (isStop) {
                                return;
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);
        Log.d(TAG, "startOrStop: start");
        new Thread(updataR).start();
    }

    @OnClick(R.id.next)
    public void nextImage(Button view) {
        Log.d(TAG, "nextImage: set courrent=" + courrent);
        courrent++;
        int position = courrent < mData.size() ? courrent : courrent %  mData.size();
        Bitmap bitmap = mData.get(position);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        }
        courrent = position;
    }

    @OnClick(R.id.start)
    public void startOrStop(Button view) {
        if (isStop) {
            isStop = false;
            new Thread(updataR).start();
            view.setText("stop");
            Log.d(TAG, "startOrStop: start");
        } else {
            Log.d(TAG, "startOrStop: stop");
            isStop = true;
            view.setText("start");
        }
    }

    @OnClick(R.id.clear)
    public void clear(Button view) {
        if (isStop) {
            mData.clear();
        }
    }

    @Override
    protected void onDestroy() {
        isStop=true;
        super.onDestroy();

    }
}
