package com.chenjimou.bannertest;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {

    private Context mContext;
    private Activity mActivity;

    private static volatile DisplayUtil instance;
    private static volatile DisplayMetrics dm = null;

    private DisplayUtil(Context context, Activity activity){
        mContext = context;
        mActivity = activity;
    }

    public static void init(Context context, Activity activity){
        if (instance == null){
            synchronized (DisplayUtil.class){
                if (instance == null){
                    instance = new DisplayUtil(context, activity);
                }
            }
        }
    }

    public static DisplayUtil getInstance(){
        return instance;
    }

    /**
     * 获取屏幕宽度
     */
    public int getScreenWidth() {
        if (mActivity == null) {
            return 0;
        }
        if (dm == null) {
            dm = new DisplayMetrics();
        }
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public int getScreenHeight() {
        if (mActivity == null) {
            return 0;
        }
        if (dm == null) {
            dm = new DisplayMetrics();
        }
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取设备操作系统版本
     *
     * @return
     */
    public String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public String getDevice() {
        return android.os.Build.MODEL;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue 需要转换的值
     * @return 转换后的值
     */
    public int px2dip(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue 需要转换的值
     * @return 转换后的值
     */
    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue 需要转换的值
     * @return 转换后的值
     */
    public int px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue 需要转换的值
     * @return 转换后的值
     */
    public int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public int px2dip(float pxValue, float scale) {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public int dip2px(float dipValue, float scale) {
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    public int getScreenWidthctx() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeightctx() {
        return mContext.getResources().getDisplayMetrics().heightPixels;

    }

    public float getScreenDensity() {
        return mContext.getResources().getDisplayMetrics().density;
    }
}
