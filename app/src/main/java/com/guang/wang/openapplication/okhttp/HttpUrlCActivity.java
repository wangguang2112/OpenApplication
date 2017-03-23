package com.guang.wang.openapplication.okhttp;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HttpUrlCActivity extends BaseActivity {

    private static final String TAG = "HttpUrlCActivity";

    @BindView(R.id.h_content)
    TextView content;

    ProgressDialog mDialog;

//    String url = "http://wangguang2112.github.io/operate.xml";
//    String url = "http://web.bangbang.58.com/misc/operatenew/getconfnewzp";

    String url ="https://webbangbang.58.com/misc/operatenew/getconfnewzp?uid=0&lon=0.0&adtypes=launch&imei=869736023490286&token=52c353052e6ef7b2&lat=0.0&os=android";
    String url3="https://webbangbang.58.com/misc/operatenew/getconfnewzp?adtypes=banner,top_left,popwin&imei=869736023490286&token=52c353052e6ef7b2&os=android";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_c);
        ButterKnife.bind(this);
        Handler handler=new Handler(getApplicationContext().getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                content.setText("click,click");
            }
        },3000);

    }

    @OnClick(R.id.h_click)
    void click(View v) {
        AsyncTask<String, Integer, String> mTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (mDialog == null) {
                    mDialog = ProgressDialog.show(HttpUrlCActivity.this, "load", "", true, false);
                } else {
                    mDialog.show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String url = params[0];
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String str;
                        StringBuilder sb = new StringBuilder();
                        while ((str = bf.readLine()) != null) {
                            sb.append(str + "\n");
                        }
                        connection.disconnect();
                        bf.close();
                        return sb.toString();
                    } else {
                        Toast.makeText(HttpUrlCActivity.this, "网络异常。。", Toast.LENGTH_SHORT)
                             .show();
                        return null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                Log.d(TAG, "onPostExecute: " + s);
                content.setText(s);
            }
        };
        mTask.execute(url3);
    }




}

