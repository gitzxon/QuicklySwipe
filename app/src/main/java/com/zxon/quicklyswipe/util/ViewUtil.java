package com.zxon.quicklyswipe.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.zxon.quicklyswipe.QuicklySwipeApplication;

import java.lang.reflect.Field;

/**
 * Created by leon on 16/4/7.
 */
public class ViewUtil {

    public static final float SCREEN_DENSITY;

    public static final int SHORT_EDGE_LENGTH;

    static {
        Resources res = QuicklySwipeApplication.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        SCREEN_DENSITY = dm.density;

        SHORT_EDGE_LENGTH = Math.min(dm.widthPixels, dm.heightPixels);
    }



    public static int getStatusBarHeight(){
        Context context = QuicklySwipeApplication.getContext();
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getScreenWidth() {
        Resources res = QuicklySwipeApplication.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        Resources res = QuicklySwipeApplication.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.heightPixels;
    }

    public static RectF getLocationInScreen(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    public static int getFloatedWindowType() {
        if (Build.VERSION.SDK_INT >= 19) {
            return WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            return WindowManager.LayoutParams.TYPE_PHONE;
        }
    }


    public static WindowManager.LayoutParams genLayoutParams(int width, int height, int x, int y) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //noinspection ResourceType
        params.type = ViewUtil.getFloatedWindowType();
        params.format = PixelFormat.TRANSLUCENT;
        params.width = width;
        params.height = height;

        params.x = x;
        params.y = y;

        return params;
    }

    public static WindowManager.LayoutParams genLayoutParams(int width, int height, int x, int y, int flags) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //noinspection ResourceType
        params.type = ViewUtil.getFloatedWindowType();
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = flags;

        params.width = width;
        params.height = height;

        params.x = x;
        params.y = y;

        return params;
    }

}
