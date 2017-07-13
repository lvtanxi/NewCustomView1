package com.lv.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Date: 2017-07-12
 * Time: 16:57
 * Description:
 */

public class HorizontalView extends ViewGroup {

    private int lastInterceptX;
    private int lastInterceptY;
    private int lastX;
    private int currentIndex = 0;
    private int childWidth = 0;
    private Scroller scroller;
    private VelocityTracker velocityTracker;


    public HorizontalView(Context context) {
        this(context, null);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                childWidth = child.getMeasuredWidth();
                child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
                left += childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(getChildCount() * childWidth, childHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            int childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(getChildCount() * childWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, getChildAt(0).getMeasuredHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            int deltaX = x - lastInterceptX;
            int deltaY = y - lastInterceptY;
            intercept = (Math.abs(deltaX) - Math.abs(deltaY)) > 0;
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            intercept = false;
            //如果动画还没有执行完成,则打断
            if (!scroller.isFinished())
                scroller.abortAnimation();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            intercept = false;
        }
        lastX = x;
        lastInterceptX = x;
        lastInterceptY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //跟随手指滑动
            int deltaX = x - lastX;
            scrollBy(-deltaX, 0);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //相对于当前View滑动的距离,正为向左,负为向右
            int distance = getScrollX() - currentIndex * childWidth;
            //滑动的距离要大于1/3个宽度,否则不会切换到其他页面
            if (Math.abs(distance) > childWidth / 3) {
                if (distance > 0) {
                    currentIndex++;
                } else {
                    currentIndex--;
                }
            } else {
                //调用该方法计算1000ms内滑动的平均速度
                velocityTracker.computeCurrentVelocity(1000);
                //获取到水平方向上的速度
                float xV = velocityTracker.getXVelocity();
                //如果速度的绝对值大于50的话，就认为是快速滑动，就执行切换页面
                if (Math.abs(xV) > 50) {
                    //大于0切换上一个页面
                    if (xV > 0) {
                        currentIndex--;
                        //小于0切换到下一个页面
                    } else {
                        currentIndex++;
                    }
                }
            }
            currentIndex = currentIndex < 0 ? 0 : currentIndex > getChildCount() - 1 ? getChildCount() - 1 : currentIndex;
            smoothScrollTo(currentIndex * childWidth, 0);
            //重置速度计算器
            velocityTracker.clear();
        }
        lastX = x;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    //弹性滑动到指定位置
    public void smoothScrollTo(int destX, int destY) {
        scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        invalidate();
    }
}
