package com.boboyuwu.beziercirlcle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float diffX = moveX - mStartX;
                BezierCirlcieView.VerticalLine verticalRightLine = mBezier.getVerticalRightLine();
                BezierCirlcieView.Point top = verticalRightLine.getTop();
                BezierCirlcieView.Point middle = verticalRightLine.getMiddle();
                BezierCirlcieView.Point bottom = verticalRightLine.getBottom();
                top.setxCoordinate(top.getxCoordinate()+diffX);
                middle.setxCoordinate(middle.getxCoordinate()+diffX);
                bottom.setxCoordinate(bottom.getxCoordinate()+diffX);
                mBezier.setVerticalRightLine(verticalRightLine);
                mStartX=moveX;
                return true;
        }
        return false;
    }
}
