package com.guang.wang.openapplication.animate;

import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class AnimateActivity extends BaseActivity {

    TextView mTextView;

    TextView mTextView2;

    ViewPropertyAnimator anim;

    String TAG = "AnimateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Fade());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);
        mTextView2 = (TextView) findViewById(R.id.text12);
        mTextView = (TextView) findViewById(R.id.text1);
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mTextView2.animate()
                              .zBy(100)
                              .setDuration(500)
                              .withEndAction(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                                          startActivity(new Intent(AnimateActivity.this,AnimateShareActivity.class), ActivityOptions.makeSceneTransitionAnimation(AnimateActivity.this,mTextView2,"transition").toBundle());
                                      }
                                  }
                              });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
