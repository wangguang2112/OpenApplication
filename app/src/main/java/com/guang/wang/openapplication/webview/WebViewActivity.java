package com.guang.wang.openapplication.webview;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.MainActivity;
import com.guang.wang.openapplication.R;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebViewActivity extends BaseActivity {
    WebView  mWebView;
    String TAG="WebViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar bar=getSupportActionBar();
        bar.setTitle("Web");
        bar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_web_view);
        mWebView= (WebView) findViewById(R.id.web_view);
        mWebView.canGoBack();
        mWebView.setWebViewClient(new MyClient());
        mWebView.loadUrl("https://www.baidu.com");
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(WebViewActivity.this, "warn warn!", Toast.LENGTH_SHORT).show();
    }

    public class MyClient extends WebViewClient{


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(TAG, "onPageStarted: "+url);
            Toast.makeText(WebViewActivity.this, "warn warn!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            Log.d(TAG, "onPageCommitVisible: "+url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "onPageFinished: "+url);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            Log.d(TAG, "doUpdateVisitedHistory: "+url);
            view.clearHistory();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.d(TAG, "shouldInterceptRequest: "+url);
            try {
                URL url1=new URL(url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                int responseCode =  connection.getResponseCode();
                if(responseCode==HttpURLConnection.HTTP_OK){
                    InputStream stream=connection.getInputStream();
                    BufferedInputStream bf=new BufferedInputStream(stream);
                    WebResourceResponse resourceResponse=new WebResourceResponse("text/html","utf-8",stream);
                    return resourceResponse;
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading: "+url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
