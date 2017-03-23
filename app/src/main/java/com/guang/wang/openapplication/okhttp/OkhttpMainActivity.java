package com.guang.wang.openapplication.okhttp;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpMainActivity extends BaseActivity {

    private static final String TAG ="OkhttpMainActivity" ;

    EditText mEditText;
    OkHttpClient mOkHttpClient;
    Request mRequest;
    TextView mTextView;
    TextView mTimer;
    public static final int SHOW_RESULT=0;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==SHOW_RESULT){
                mTextView.setText((String)msg.obj);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_main);
        mEditText= (EditText) findViewById(R.id.urltext);
        mEditText.setText("http://wangguang2112.github.io/operate.xml");
        mTextView= (TextView) findViewById(R.id.ok_result_text);
        mTimer= (TextView) findViewById(R.id.timer);
        OkHttpClient.Builder b=new OkHttpClient.Builder();
        Cache cache=new Cache(new File(getExternalCacheDir(),"webcache"),1024*1024*50);
        b.cache(cache);
        b.interceptors().add(LoggingInterceptor);
        b.networkInterceptors().add(REWRITE_CACHE_INTERCEPTOR);
        b.interceptors().add(REWRITE_CACHE_INTERCEPTOR);
        mOkHttpClient=b.build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("wwww","onStart"+hasWindowFocus()+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("wwww","onResume"+hasWindowFocus()+"");
    }

    void downclick(View view){
        timer.start();
        mTimer.setVisibility(View.VISIBLE);
        String url=mEditText.getText().toString();
        if(!url.equals("")) {
            if(url.startsWith("bangjob")){
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return;
            }
            try {
                mRequest = new Request.Builder().url(url).build();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"url error",Toast.LENGTH_SHORT).show();
                return;
            }

            Call call = mOkHttpClient.newCall(mRequest);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("wang", "error");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String htmlStr = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(htmlStr);
                            Log.d("main pid",":"+Thread.currentThread().getId());
                        }
                    });
                    //test asyn
//                    mTextView.setText("123");
                    Log.d("pid",":"+Thread.currentThread().getId());

                }
            });
            Toast.makeText(this,"123",Toast.LENGTH_SHORT).show();
        }
    }
    void getclick(View view){

    }
    void postclick(View view){

    }
    CountDownTimer timer=new CountDownTimer(1000*3,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTimer.setText(String.valueOf(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            mTimer.setVisibility(View.GONE);
        }
    };

    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    private final Interceptor REWRITE_CACHE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                             .cacheControl(CacheControl.FORCE_CACHE)
                             .build();
            Response originalResponse = chain.proceed(request);
            String cacheControl = request.cacheControl()
                                         .toString();
            return originalResponse.newBuilder()
                                   .header("Cache-Control", cacheControl)
                                   .removeHeader("Pragma")
                                   .build();
        }

    };

    private final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.i(TAG, String.format("Received response for %s in %.1fms%n%s", response.request()
                                                                                       .url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };
}
