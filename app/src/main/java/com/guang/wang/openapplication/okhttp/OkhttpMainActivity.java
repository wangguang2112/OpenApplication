package com.guang.wang.openapplication.okhttp;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpMainActivity extends BaseActivity {
    EditText mEditText;
    OkHttpClient mOkHttpClient=new OkHttpClient();
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
    CountDownTimer timer=new CountDownTimer(1000*10,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTimer.setText(String.valueOf(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            mTimer.setVisibility(View.GONE);
        }
    };
}
