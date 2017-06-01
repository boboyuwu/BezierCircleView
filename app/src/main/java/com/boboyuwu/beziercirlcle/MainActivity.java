package com.boboyuwu.beziercirlcle;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private BezierCirlcieView mBezier;
    private float mStartX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBezier = (BezierCirlcieView) findViewById(R.id.bezier);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                mBezier.setHorizontalOffset(value);
            }
        });
        valueAnimator.start();
        return true;
    }
}
