package com.guang.wang.openapplication.webview;

import com.guang.wang.openapplication.R;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    WebView  mWebView;
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
        mWebView.loadUrl("http://www.baidu.com");
    }
    public class MyClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;//返回true拦截

        }
    }


}
