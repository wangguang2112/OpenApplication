package com.guang.wang.openapplication.webview;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        mWebView.canGoBack();mWebView.setWebViewClient(new MyClient());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.loadUrl("http://www.baidu.com");
    }

    public class MyClient extends WebViewClient{


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "onPageFinished: ");

        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            Log.d(TAG, "doUpdateVisitedHistory: ");
            view.clearHistory();
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
