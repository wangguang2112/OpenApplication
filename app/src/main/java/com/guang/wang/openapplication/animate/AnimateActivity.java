package com.guang.wang.openapplication.animate;

import com.guang.wang.openapplication.R;

import android.animation.TimeInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;
import android.widget.Toast;

public class AnimateActivity extends AppCompatActivity {

    TextView mTextView;

    TextView mTextView2;

    ViewPropertyAnimator anim;

    String TAG = "AnimateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);
        mTextView2 = (TextView) findViewById(R.id.text11);
        mTextView = (TextView) findViewById(R.id.text12);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim = mTextView.animate()
                                .alpha(0.3f)
                                .yBy(500)
                                .setInterpolator(new TimeInterpolator() {
                                    @Override
                                    public float getInterpolation(float input) {
//                         float output= (float) Math.sin(input*Math.PI/2);
                                        float output = (float) Math.log(input * (Math.E - 1) + 1);
                                        Log.d(TAG, "getInterpolation: output" + output);
                                        return output;
                                    }
                                })
                                .setDuration(2000)
                                .withStartAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AnimateActivity.this, "start", Toast.LENGTH_SHORT)
                                             .show();
                                    }
                                })
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AnimateActivity.this, "over", Toast.LENGTH_SHORT)
                                             .show();
                                    }
                                });
//                anim.start();
            }
        });

        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView2.animate()
                          .zBy(50)
                          .setDuration(5000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
