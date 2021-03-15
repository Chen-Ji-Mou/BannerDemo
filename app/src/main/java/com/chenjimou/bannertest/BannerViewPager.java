package com.chenjimou.bannertest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class BannerViewPager extends ViewPager {

    public BannerViewPager(@NonNull Context context) {
        super(context);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 根据子View的最大高度来确定ViewPager的高度
//        int maxHeight = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            ViewGroup.LayoutParams params = child.getLayoutParams();
//            child.measure(widthMeasureSpec,
//                    getChildMeasureSpec(heightMeasureSpec, 0, params.height));
//            int childHeight = child.getMeasuredHeight();
//            if (childHeight > maxHeight){
//                maxHeight = childHeight;
//            }
//        }
//        if (maxHeight > 0){
//            setMeasuredDimension(getMeasuredWidth(), maxHeight);
//        }
    }
}
