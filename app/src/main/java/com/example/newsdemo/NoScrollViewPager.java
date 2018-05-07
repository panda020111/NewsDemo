package com.example.newsdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yunchang on 2018/5/7.
 * viewpager滑动通过代码控制，实现首屏的切换
 */

public class NoScrollViewPager extends ViewPager {

    private boolean enabled;

    public NoScrollViewPager(Context context) {
        super(context);
        this.enabled = false;
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.enabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.enabled && super.onInterceptTouchEvent(ev);
    }
}
