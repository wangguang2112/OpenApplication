package com.guang.wang.openapplication;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

public class BaseActivity extends AppCompatActivity {
    List<Subscription> list=new ArrayList<>();
    ProgressDialog mDialog;

    protected final String TAG=this.getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    protected Handler.Callback mCallback;
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mCallback!=null){
                if(mCallback.handleMessage(msg)){
                    return;
                }else{
                    super.handleMessage(msg);
                }
            }
        }
    };
    public Handler getMainHandler(){
        return mHandler;
    }

    protected void addHandlerCallBack(Handler.Callback callback){
        this.mCallback=callback;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(Subscription s:list){
            s.unsubscribe();
        }
        list.clear();
    }
    protected void addSubscription(Subscription s){
        list.add(s);
    }

    protected void setOnBusy(boolean isbusy){
        if(mDialog==null&&isbusy) {
            mDialog = ProgressDialog.show(this, "Loadding", "", true, false);
        }else if(isbusy){
            mDialog.show();
        }else {
            mDialog.dismiss();
        }

    }
    protected void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT);
    }
}
