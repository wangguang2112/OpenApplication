package com.guang.wang.openapplication.dialog;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DialogActivity extends BaseActivity {

    TextView tv;

    GuideDialog dialog;

    Handler mHandler = new Handler();

    Button b;
    PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setWindowAnimations(R.style.popanim_down);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        tv = (TextView) findViewById(R.id.bottom_text);
        b= (Button) findViewById(R.id.popwindow);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        if (dialog == null) {
            dialog = new GuideDialogImp(this);
//            dialog=new MyDialog(this );
            final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT | Gravity.BOTTOM;
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_guide, null);
            ImageView close = (ImageView) view.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            dialog.setCanceledOnTouchOutside(false);
//            dialog.setContentView(view, params);
            dialog.setContentView(R.layout.dialog_guide);
            dialog.setCanTouchOut(true);
             popupWindow = new PopupWindow();
            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_layout_anim, null);
            popupWindow.setContentView(view2);
            //必须设置
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.popanim_down2);

//            popupWindow.setFocusable(true);
//            popupWindow.showAtLocation(DialogActivity.this.getWindow().getDecorView(),Gravity.CENTER,-1,-1);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                   dialog.show();
//                    popupWindow.showAtLocation(DialogActivity.this.getWindow().getDecorView(), Gravity.CENTER, -1, -1);
                }
            }, 1000);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.showAsDropDown(b);
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:15810826527"));
                    if (ActivityCompat.checkSelfPermission(DialogActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(DialogActivity.this,"未分配打电话权限",Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                }
            });
        }
//        tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.pop_down));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
