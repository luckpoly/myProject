package com.goodfood86.tiaoshi.order121Project.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * 常用单位转换的辅助类
 *
 * @author SirWangsq
 * @ClassName: WonderCode
 * @date create at 2015/8/21
 */
public class DensityUtil {
    private DensityUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }


    public static final float getHeightInPx(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static final float getWidthInPx(Context context) {
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static final int getHeightInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int heightInDp = (int) px2dp(context, height);
        return heightInDp;
    }

    public static final int getWidthInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int widthInDp = (int) px2dp(context, height);
        return widthInDp;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //距离转换显示
    public static int poi;
    public static void setDistance(TextView view, String name, String distance) {
        if (!TextUtils.isEmpty(distance)) {
            if (distance.equals("0")){
                view.setText("0");
                return;
            }
            String juli = Double.parseDouble(distance) / 1000.0 + "";
            for (int i = 0; i < juli.length(); i++) {
                if (".".equals(juli.charAt(i) + "")) {
                    poi = i;
                }
            }
            if (poi == 1 && "0".equals(juli.charAt(0) + "")) {
                if ("0".equals(juli.charAt(2) + "")) {
                    if ("0".equals(juli.charAt(3) + "")) {
                        view.setText(name + juli.substring(4, poi + 4) + "m");
                    } else {
                        view.setText(name + juli.substring(3, poi + 4) + "m");
                    }
                } else {
                    view.setText(name + juli.substring(2, poi + 4) + "m");
                }
            } else {
                view.setText(name + juli.substring(0, poi + 3) + "km");
            }
        }else {
            view.setText("");
        }
    }


}