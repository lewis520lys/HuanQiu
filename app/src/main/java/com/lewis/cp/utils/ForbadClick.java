package com.lewis.cp.utils;

import android.content.Context;
import android.view.View;

public class ForbadClick {

    private static long lastClickTime;
    private static View view;
    public static boolean isFastDoubleClick(View v) {
        long time = System.currentTimeMillis();     
        if ( view == v && time - lastClickTime < 3000) {
            return true;     
        }
        view = v;
        lastClickTime = time;     
        return false;     
    }
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}