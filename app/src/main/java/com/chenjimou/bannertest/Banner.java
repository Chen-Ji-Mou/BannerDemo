package com.chenjimou.bannertest;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    private final Context mContext;
    // banner轮播的图片url集合
    private List<String> mUrls;
    // 记录所有指示器中的"点"
    private List<View> indicators;
    // 记录指示器当前"点"的位置
    private int indicatorPosition = 0;

    private BannerViewPager viewPager;
    private LinearLayout indicator;
    private Handler mHandler;
    private AutoRollTimer mAutoRollTimer;
    private BannerOnClickListener mOnClickListener;

    public Banner(@NonNull Context context) {
        this(context ,null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        // 初始化
        init();
    }

    /**
     * 进行初始化
     */
    private void init(){
        // 初始化布局
        View.inflate(mContext, R.layout.banner_layout, this);
        viewPager = findViewById(R.id.banner_viewpager);
        indicator = findViewById(R.id.banner_indicator);

        // 定死ViewPager的高度是屏幕的1/4
        ViewGroup.LayoutParams vParams = viewPager.getLayoutParams();
        vParams.height = (int) (DisplayUtil.getInstance().getScreenHeight() * 0.25);
        viewPager.setLayoutParams(vParams);

        // 初始化数据
        mUrls = new ArrayList<>();
        indicators = new ArrayList<>();
        mAutoRollTimer = new AutoRollTimer();
        mHandler = new Handler();
        viewPager.setAdapter(new BannerAdapter());

        // 设置监听
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 设置banner轮播的图片url集合，根据集合来初始化指示器布局
     * @param urls banner轮播的图片url集合
     */
    public void setBannerImgUrlData(List<String> urls){
        if (null != urls && !urls.isEmpty()){
            this.mUrls.addAll(urls);
        }
        if (!this.mUrls.isEmpty()){
            indicators.clear();
            indicator.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < mUrls.size(); i++) {
                ImageView imageView = new ImageView(mContext);
                if (i == 0){
                    imageView.setBackgroundResource(R.drawable.banner_indicator_select);
                } else {
                    imageView.setBackgroundResource(R.drawable.banner_indicator_normal);
                }
                params.setMargins(0,
                        0,
                        DisplayUtil.getInstance().dip2px(5),
                        0);
                imageView.setLayoutParams(params);
                // 添加"点"到指示器中
                indicator.addView(imageView);
                // 记录"点"
                indicators.add(imageView);
            }
        }
    }

    /**
     * 设置开始轮播
     */
    public void startRoll() {
        mAutoRollTimer.start(mAutoRollTimer.interval);
    }

    /**
     * 设置停止轮播
     */
    public void stopRoll() {
        mAutoRollTimer.stop();
    }

    /**
     * 设置轮播间隔
     * @param interval 间隔时长
     */
    public void setRollInterval(long interval){
        mAutoRollTimer.interval = interval;
        startRoll();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 更新"点"的图案
        indicators.get(indicatorPosition).setBackgroundResource(R.drawable.banner_indicator_normal);
        indicators.get(position % indicators.size()).setBackgroundResource(R.drawable.banner_indicator_select);
        // 重新刷新 indicatorPosition 索引
        indicatorPosition = position % indicators.size();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface BannerOnClickListener {
        void onClick(int position);
    }

    /**
     * 设置点击监听
     */
    public void setBannerOnClickListener(BannerOnClickListener bannerOnClickListener){
        mOnClickListener = bannerOnClickListener;
    }

    private class BannerAdapter extends PagerAdapter {
        // 缓存ImageView实例
        private final List<ImageView> imgCache = new ArrayList<>();

        @Override
        public int getCount() {
            // 无限滑动
            return mUrls == null ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (null != object && object instanceof ImageView){
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
                // 添加缓存
                imgCache.add(imageView);
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView;

            // 获取ImageView对象
            if (imgCache.size() > 0) {
                imageView = imgCache.remove(0);
            } else {
                imageView = new ImageView(mContext);
            }
            // 设置图片缩放模式
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setOnTouchListener(new OnTouchListener() {
                // 手指触摸点的X坐标
                private int downX = 0;
                // 手指按下的时间
                private long downTime = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            mAutoRollTimer.stop();
                            downX = (int) v.getX();
                            downTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            mAutoRollTimer.start(mAutoRollTimer.interval);
                            int moveX = (int) v.getX();
                            long moveTime = System.currentTimeMillis();
                            //判断为点击的条件
                            if (downX == moveX && (moveTime - downTime < 500)) {
                                //轮播图回调点击事件
                                if (null != mOnClickListener){
                                    mOnClickListener.onClick(position % mUrls.size());
                                }
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mAutoRollTimer.start(mAutoRollTimer.interval);
                            break;
                    }
                    return true;
                }
            });
            // 网络加载图片url
            Glide.with(mContext).load(mUrls.get(position % mUrls.size())).into(imageView);
            // 添加imageView到布局中
            container.addView(imageView);
            return imageView;
        }
    }

    private class AutoRollTimer implements Runnable {
        // 是否在轮播的标志
        boolean isRunning = false;
        // 默认3秒轮播
        long interval = 3000;

        public void start(long interval){
            if (!isRunning) {
                isRunning = true;
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, interval);
            }
        }

        public void stop() {
            if (isRunning) {
                mHandler.removeCallbacks(this);
                isRunning = false;
            }
        }

        @Override
        public void run() {
            if (isRunning) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                mHandler.postDelayed(this, interval);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRoll();
    }
}
