package com.guang.wang.openapplication.service;

//import com.wuba.bangjob.common.model.config.Config;
//import com.wuba.bangjob.common.utils.AndroidUtil;
//import com.wuba.client.download.UploadExecutors;
//import com.wuba.client.download.UploadWorker;

import com.guang.wang.openapplication.R;
import com.wuba.bangbang.uicomponents.pictureediter.cropwindow.handle.Handle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.UUID;

/**
 * Created by wangguang.
 * Date:2017/9/5
 * Description:
 */

public class TraceLogService extends Service {

    public static final String TAG="TraceLogService";
    WGLog log;
    Handler handler;
    HandlerThread thread;
    public TraceLogService() {
        thread=new HandlerThread("load");
        thread.start();
        handler=new Handler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        handler.postDelayed(mRunnable,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    Runnable mRunnable=new MRunnable(this);
    public class MRunnable implements Runnable{
        Context mContext;
        int progress=0;
        public MRunnable(Context context){
            this.mContext=context;
        }
        @Override
        public void run() {
            if(progress<=100) {
                showMessage(mContext, progress, false);
                progress++;
                handler.postDelayed(this, 1000);
            }else{
                thread.quit();
            }

        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

  /*  @Override
    protected void onHandleIntent(Intent intent) {
        UploadWorker.HttpURLConnectionConfig connectionConfig=getDefaultConfig();
        UploadExecutors.getInstance().setOnUploadListener(UPLOAD_URL, new UploadExecutors.OnUploadListener() {
            @Override
            public void onStart(String path) {
                log.d(TAG, "onStart() called with: path = [" + path + "]");
                showMessage(getApplicationContext(),0,false);
            }

            @Override
            public void onFinished(String path, String response) {
                log.d(TAG, "onFinished() called with: path = [" + path + "], response = [" + response + "]");
            }

            @Override
            public void onCanceled(String path) {
                log.d(TAG, "onCanceled() called with: path = [" + path + "]");
            }

            @Override
            public void onProgressChanged(String path, int progress) {
                if(progress>=100){
                    showMessage(getApplicationContext(), progress, true);
                }else {
                    showMessage(getApplicationContext(), progress, false);
                }

            }
        });
        UploadExecutors.getInstance().upload(UPLOAD_URL,UPLOAD_FILE_PATH,connectionConfig,null,null);
    }*/

  /*  private UploadWorker.HttpURLConnectionConfig getDefaultConfig() {
        UploadWorker.HttpURLConnectionConfig config = new UploadWorker.HttpURLConnectionConfig();
        config.setReadTimeout(10000);
        config.setConnectTimeout(10000);
        config.setDoInput(true); // 允许输入流
        config.setDoOutput(true); // 允许输出流
        config.setUseCaches(false); // 不允许使用缓存
        config.setRequestMethod("POST"); // 请求方式
        config.setRequestProperty("Pic-Flash", "1");
        config.setRequestProperty("Charset", "utf-8"); // 设置编码
        config.setRequestProperty("connection", "keep-alive");
        config.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + UUID.randomUUID().toString());
        //以下参数同FLASH版代码
        config.setRequestProperty("Pic-Path", DIR_NO_WATERMARK);
        config.setRequestProperty("Pic-Size", "0*0");
        config.setRequestProperty("Pic-Bulk", "0");
        config.setRequestProperty("Pic-dpi", "0");
        config.setRequestProperty("Pic-Cut", "0*0*0*0");
        config.setRequestProperty("Pic-IsAddWaterPic", "true");   //如果是上传到服务端的/bidding/big/这个目录，参数是不起作用的。要去掉水印，需要上传到/userauth/pp/这个目录
        config.setRequestProperty("File-Extensions", "jpg");
        return config;
    }*/

    NotificationManager manager;
    /*
 * 通知提示
 * */
    private void showMessage(Context mContext,int progress,boolean isCompelte) {
        Log.d(TAG, "showMessage() called with: progress = [" + progress + "], isCompelte = [" + isCompelte + "]");
   /*     Intent i = new Intent();
        i.setClass(App.getApp().getApplicationContext(), JobMainInterfaceActivity.class);
        i.putExtra(JobMainInterfaceActivity.ACTION_CHANGE_TAB, JobMainInterfaceActivity.TALENT);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                i, PendingIntent.FLAG_UPDATE_CURRENT);*/
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.back))
                .setContentText("123")
                .setContentTitle("test")
                .setTicker("123")

                .setAutoCancel(false);
//                .setContentIntent(pendingIntent);
        if(isCompelte){
            nBuilder.setProgress(0,0,false)
                    .setContentText("upload complete");

        }else{
            nBuilder.setProgress(100,progress,false)
                    .setContentText("upload..");
        }
        manager.notify(103,nBuilder.build());

    }
}
