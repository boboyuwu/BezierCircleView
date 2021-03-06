package com.boboyuwu.beziercirlcle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wubo on 2017/5/31.
 */

public class BezierCirlcieView extends View {

    private int mRadius;
    private final float c = 0.551915024494f;
    private int mDefualtWidth;
    private int mDefualtRadius;
    private VerticalLine mVerticalRightLine;
    private VerticalLine mVerticalLeftLine;
    private HorizontalLine mHorizontalTopLine;
    private HorizontalLine mHorizontalBottomLine;
    private int mResualtWidth;

    public BezierCirlcieView(Context context) {
        this(context, null);
    }

    public BezierCirlcieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierCirlcieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        // init  default value
        mDefualtRadius = 100;
        mDefualtWidth = 300;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measuredWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measuredHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        //半径
        int width;
        int height;
        if (measuredWidthMode == MeasureSpec.EXACTLY) {
            width = measuredWidthSize;
        } else {
            width = Math.min(measuredWidthSize, mDefualtWidth);
        }
        if (measuredHeightMode == MeasureSpec.EXACTLY) {
            height = measuredHeightSize;
        } else {
            height = Math.min(measuredHeightSize, mDefualtWidth);
        }
        //init quartile
        mResualtWidth = Math.min(width, height);
        initLines(mResualtWidth);
        //这里参数是不能设置resualtWidth/2的否则会递归调用导致越来越小 因为onMeasure可能会调用多次
        setMeasuredDimension(mResualtWidth, mResualtWidth);
    }

    private void initLines(int resualtWidth) {
        int i = resualtWidth / 3;
        mRadius = (i <= 0) ? mDefualtRadius : i;
        //top line
        mHorizontalTopLine = new HorizontalLine();
        Point horizontalTopLineLeftPoint = new Point();
        horizontalTopLineLeftPoint.setxCoordinate(-c * mRadius);
        horizontalTopLineLeftPoint.setyCoordinate(-mRadius);
        mHorizontalTopLine.setLeft(horizontalTopLineLeftPoint);

        Point horizontalTopLineMiddlePoint = new Point();
        horizontalTopLineMiddlePoint.setxCoordinate(0);
        horizontalTopLineMiddlePoint.setyCoordinate(-mRadius);
        mHorizontalTopLine.setMiddle(horizontalTopLineMiddlePoint);

        Point horizontalTopLineRightPoint = new Point();
        horizontalTopLineRightPoint.setxCoordinate(c * mRadius);
        horizontalTopLineRightPoint.setyCoordinate(-mRadius);
        mHorizontalTopLine.setRight(horizontalTopLineRightPoint);

        //bottom line

        mHorizontalBottomLine = new HorizontalLine();
        Point horizontalBottomLineLeftPoint = new Point();
        horizontalBottomLineLeftPoint.setxCoordinate(-c * mRadius);
        horizontalBottomLineLeftPoint.setyCoordinate(mRadius);
        mHorizontalBottomLine.setLeft(horizontalBottomLineLeftPoint);

        Point horizontalBottomLineMiddlePoint = new Point();
        horizontalBottomLineMiddlePoint.setxCoordinate(0);
        horizontalBottomLineMiddlePoint.setyCoordinate(mRadius);
        mHorizontalBottomLine.setMiddle(horizontalBottomLineMiddlePoint);

        Point horizontalBottomLineRightPoint = new Point();
        horizontalBottomLineRightPoint.setxCoordinate(c * mRadius);
        horizontalBottomLineRightPoint.setyCoordinate(mRadius);
        mHorizontalBottomLine.setRight(horizontalBottomLineRightPoint);
        // left line
        mVerticalLeftLine = new VerticalLine();
        Point verticalLeftLineTopPoint = new Point();
        verticalLeftLineTopPoint.setxCoordinate(-mRadius);
        verticalLeftLineTopPoint.setyCoordinate(-c * mRadius);
        mVerticalLeftLine.setTop(verticalLeftLineTopPoint);

        Point verticalLeftLineMiddlePoint = new Point();
        verticalLeftLineMiddlePoint.setxCoordinate(-mRadius);
        verticalLeftLineMiddlePoint.setyCoordinate(0);
        mVerticalLeftLine.setMiddle(verticalLeftLineMiddlePoint);

        Point verticalLeftLineBottomPoint = new Point();
        verticalLeftLineBottomPoint.setxCoordinate(-mRadius);
        verticalLeftLineBottomPoint.setyCoordinate(c * mRadius);
        mVerticalLeftLine.setBottom(verticalLeftLineBottomPoint);
        //right line
        mVerticalRightLine = new VerticalLine();

        Point verticalRightLineTopPoint = new Point();
        verticalRightLineTopPoint.setxCoordinate(mRadius);
        verticalRightLineTopPoint.setyCoordinate(-c * mRadius);
        mVerticalRightLine.setTop(verticalRightLineTopPoint);

        Point verticalRightLineMiddlePoint = new Point();
        verticalRightLineMiddlePoint.setxCoordinate(mRadius);
        verticalRightLineMiddlePoint.setyCoordinate(0);
        mVerticalRightLine.setMiddle(verticalRightLineMiddlePoint);

        Point verticalRightLineBottomPoint = new Point();
        verticalRightLineBottomPoint.setxCoordinate(mRadius);
        verticalRightLineBottomPoint.setyCoordinate(c * mRadius);
        mVerticalRightLine.setBottom(verticalRightLineBottomPoint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);

    }

    private void drawCircle(Canvas canvas) {
        //防止越界
        checkRange();
        canvas.save();
        //translate to mRadius point
        canvas.translate(mRadius, mRadius);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        //draw the first quartile
        path.moveTo(mVerticalLeftLine.getMiddle().getxCoordinate(), mVerticalLeftLine.getMiddle().getyCoordinate());
        path.cubicTo(mVerticalLeftLine.getTop().getxCoordinate(), mVerticalLeftLine.getTop().getyCoordinate(),
                mHorizontalTopLine.getLeft().getxCoordinate(), mHorizontalTopLine.getLeft().getyCoordinate(),
                mHorizontalTopLine.getMiddle().getxCoordinate(), mHorizontalTopLine.getMiddle().getyCoordinate());
        //draw the second quartile
        path.cubicTo(mHorizontalTopLine.getRight().getxCoordinate(), mHorizontalTopLine.getRight().getyCoordinate(),
                mVerticalRightLine.getTop().getxCoordinate(), mVerticalRightLine.getTop().getyCoordinate(),
                mVerticalRightLine.getMiddle().getxCoordinate(), mVerticalRightLine.getMiddle().getyCoordinate());
        //draw the third quartile
        //path.moveTo(mRightPoint.getxCoordinate(),mRightPoint.getyCoordinate());
        path.cubicTo(mVerticalRightLine.getBottom().getxCoordinate(), mVerticalRightLine.getBottom().getyCoordinate(),
                mHorizontalBottomLine.getRight().getxCoordinate(), mHorizontalBottomLine.getRight().getyCoordinate(),
                mHorizontalBottomLine.getMiddle().getxCoordinate(), mHorizontalBottomLine.getMiddle().getyCoordinate());
        //draw the fourth quartile
        path.cubicTo(mHorizontalBottomLine.getLeft().getxCoordinate(), mHorizontalBottomLine.getLeft().getyCoordinate(),
                mVerticalLeftLine.getBottom().getxCoordinate(), mVerticalLeftLine.getBottom().getyCoordinate(),
                mVerticalLeftLine.getMiddle().getxCoordinate(), mVerticalLeftLine.getMiddle().getyCoordinate());
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void checkRange() {
        //check top
        checkVerticalLineRange(mVerticalLeftLine);
    }

    //we just need to check vertical range
    public void checkVerticalLineRange(VerticalLine verticalLine) {
        if (Math.abs(verticalLine.getTop().getxCoordinate()) + mRadius >= mResualtWidth) {
            verticalLine.getTop().setxCoordinate(mResualtWidth - mRadius);
        }
        if (Math.abs(verticalLine.getMiddle().getxCoordinate()) + mRadius >= mResualtWidth) {
            verticalLine.getTop().setxCoordinate(mResualtWidth - mRadius);
        }
        if (Math.abs(verticalLine.getBottom().getxCoordinate()) + mRadius >= mResualtWidth) {
            verticalLine.getTop().setxCoordinate(mResualtWidth - mRadius);
        }
    }

    //set the offset value (0~1)
    //定义一些阀值
    private final float thresholdL=0.5f;
    private final float thresholdM=0.8f;
    private final float thresholdR=0.9f;
    public void setHorizontalOffset(float offset){
        offset=offset>=1?1:offset;
        offset=offset<=0?0:offset;
        float offsetValue = mRadius * offset;

        //这里来个阀值 （0~0.6 准备离开框框    0.6～0.8 平衡状态   0.8 ~1 ）
        Point topL = mVerticalRightLine.getTop();
        Point middleL = mVerticalRightLine.getMiddle();
        Point bottomL = mVerticalRightLine.getBottom();

        Point left = mHorizontalTopLine.getLeft();
        Point middle = mHorizontalTopLine.getMiddle();
        Point right = mHorizontalTopLine.getRight();

        if(offsetValue>0&&offsetValue<thresholdL){
            float diffRate = getDiffRate(0, thresholdL, offsetValue);
            float rightDiffX= mRadius+mRadius * diffRate;
            topL.setxCoordinate(rightDiffX);
            middleL.setxCoordinate(rightDiffX);
            bottomL.setxCoordinate(rightDiffX);
            refreshView();
        }else if(offsetValue>=thresholdL&&offsetValue<thresholdM){
            float diffRate = getDiffRate(thresholdL, thresholdM, offsetValue);
            float rightDiffX= mRadius+(mRadius-mRadius * diffRate*0.5f);
            Log.e("wwwwwwww",rightDiffX+"");
            topL.setxCoordinate(rightDiffX);
            middleL.setxCoordinate(rightDiffX);
            bottomL.setxCoordinate(rightDiffX);

            left.setxCoordinate(rightDiffX);
            middle.setxCoordinate(rightDiffX);
            right.setxCoordinate(rightDiffX);
            refreshView();
        }else if(offsetValue>=thresholdM){

        }
    }

    //value2 must greater than value1
    public float getDiffRate(float value1,float value2,float offset){
        return (offset-value1)/(value2-value1);
    }


    private void refreshView() {
        invalidate();
    }

    class VerticalLine {
        private Point top;
        private Point middle;
        private Point bottom;

        public Point getTop() {
            return top;
        }

        public void setTop(Point top) {
            this.top = top;
        }

        public Point getMiddle() {
            return middle;
        }

        public void setMiddle(Point middle) {
            this.middle = middle;
        }

        public Point getBottom() {
            return bottom;
        }

        public void setBottom(Point bottom) {
            this.bottom = bottom;
        }
    }

    class HorizontalLine {
        private Point left;
        private Point middle;
        private Point right;

        public Point getLeft() {
            return left;
        }

        public void setLeft(Point left) {
            this.left = left;
        }

        public Point getMiddle() {
            return middle;
        }

        public void setMiddle(Point middle) {
            this.middle = middle;
        }

        public Point getRight() {
            return right;
        }

        public void setRight(Point right) {
            this.right = right;
        }
    }

    class Point {
        private float xCoordinate;
        private float yCoordinate;

        public float getyCoordinate() {
            return yCoordinate;
        }

        public void setyCoordinate(float yCoordinate) {
            this.yCoordinate = yCoordinate;
        }

        public float getxCoordinate() {
            return xCoordinate;
        }

        public void setxCoordinate(float xCoordinate) {
            this.xCoordinate = xCoordinate;
        }
    }
}
