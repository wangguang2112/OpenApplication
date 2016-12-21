package com.guang.wang.openapplication.webview;

import com.guang.wang.openapplication.BaseFragment;
import com.guang.wang.openapplication.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by wangguang.
 * Date:2016/12/15
 * Description:
 */
public class NormalWebFragment extends BaseFragment {

    WebView mWebView;
    String url="http://www.baidu.com";
    String TAG="NormalWebFragment";
    int position=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUrl(String url){
        this.url=url;
    }
    public void setPosition(int position){
        this.position=position;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_web_view, container, false);
        mWebView= (WebView) view.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted: position="+position+" STARTE");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageStarted: position="+position+" Finish");
            }
        });
        Log.d(TAG, "onCreateView: loadUrl position="+position);
        mWebView.loadUrl(url);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}