package com.guang.wang.openapplication.webview;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangguang.
 * Date:2017/8/14
 * Description:
 */

public class CacheWebView extends WebView {


    Map<String,String> cacheFile=new HashMap<>();
    public CacheWebView(Context context) {
        super(context);
        this.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Uri uri=Uri.parse(url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                return super.shouldInterceptRequest(view, request);
            }


        });

    }

    @Override
    public void loadUrl(String url) {
        Uri uri=Uri.parse(url);
        String fileName=cacheFile.get(uri.getScheme()+uri.getHost()+uri.getHost());
        if(fileName==null) {
            super.loadUrl(url);
        }else{
            super.loadUrl(fileName);
        }

    }


}
