package com.guang.wang.openapplication.scroll;

import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.scroll.overscrollview.OnMyScrollChangeListener;
import com.guang.wang.openapplication.scroll.overscrollview.OverScrollView;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollActivity extends AppCompatActivity implements OnMyScrollChangeListener {

    private static final String TAG = ScrollActivity.class.getSimpleName();

    ImageView icon;

    TextView title;

    OverScrollView scrol;

    boolean canSet = true;

    int count = 0;//添加一次绘制的机会

    int destShowTitle = 100;

    boolean isdown = true;

    int orbackColor = Color.parseColor("#FF4081");

    int ortextColor = Color.parseColor("#ffffff");

    public RelativeLayout title_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        title = (TextView) findViewById(R.id.my_title);
        scrol = (OverScrollView) findViewById(R.id.activity_scroll);
        scrol.setVerticalScrollBarEnabled(false);
        title_layout = (RelativeLayout) findViewById(R.id.my_title_layout);
        icon = (ImageView) findViewById(R.id.icon);
        scrol.setOnMScrollListener(this);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ScrollActivity.this,ScrollOriginActivity.class));
            }
        });
        View head=new View(this);
        head.setBackgroundColor(Color.BLACK);
        scrol.setMaxOverGap(800);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
        scrol.addOverHeadView(this,head,p);
        View foot=new View(this);
        foot.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
        scrol.addOverFootView(this,foot,p2);
    }

    @Override
    public void onMyScrollChanged(int initH,int l, int t, int oldl, int oldt) {
        Log.d(TAG, "onMyScrollChanged() called with: initH = [" + initH + "], l = [" + l + "], t = [" + t + "], oldl = [" + oldl + "], oldt = [" + oldt + "]");
        int col =t - destShowTitle-initH;
        //用户判断是否转变,向下突然变成向上
        if (t - oldt> 0 && !isdown) {
            count = 0;
            isdown = true;

        } else if (isdown) {
            count = 0;
            isdown = false;
        }
        if (t-initH > 128 + destShowTitle) {
            if (count == 0) {
                count++;
            } else {
                return;
            }

        }
        if (t-initH > destShowTitle) {
            if (canSet) {
                title_layout.setVisibility(View.VISIBLE);
                canSet = false;
            }
        } else if (t-initH <= destShowTitle) {//图片随屏幕滚动
            title_layout.setVisibility(View.GONE);
            count = 0;
            canSet = true;
            int mt = 200 - (t-initH);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) icon.getLayoutParams();
            p.topMargin = mt;
            icon.setLayoutParams(p);
            return;
        }
        //介于 destShowTitle 到destShowTitle+128的区间;
        col = Math.min(col, 128);
//        Log.d(TAG, "onMyScrollChanged: col=" + col);
        //改变标题透明色
        int nowBackColor = Color.argb((Math.min(col * 2, 255)), Color.red(orbackColor), Color.green(orbackColor), Color.blue(orbackColor));
        int nowTextColor = Color.argb((Math.min(col * 2, 255)), Color.red(ortextColor), Color.green(ortextColor), Color.blue(ortextColor));
        //改变头像文职和大小
        title_layout.setBackgroundColor(nowBackColor);
        title.setTextColor(nowTextColor);
        int ml = 40 - (col * 10) / 128;//marginleft
        int mt = 100 - (col * 80) / 128;//margintop
        int iw = 160 - (col * 100) / 128;//图片width
        int ih = iw;
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) icon.getLayoutParams();
        p.topMargin = mt;
        p.leftMargin = ml;
        p.width = iw;
        p.height = ih;
        icon.setLayoutParams(p);
    }

    @Override
    public void onScrollOver(int initH, int l, int t) {
        Log.d(TAG, "onScrollOver() called with: initH = [" + initH + "], l = [" + l + "], t = [" + t + "]");
        //在这处理一下 最中结果
//        int col =t - destShowTitle-initH;
//        Log.d(TAG,"onScrollOver() col="+col);
//        if(col>0&&col<80){
//            scrol.scrollTo(0,initH);
//        }else if(col>=80&&col<160){
//            scrol.scrollTo(0,destShowTitle+initH+160);
//        }
    }
}
