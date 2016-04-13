package com.zxon.quicklyswipe;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;


/**
 * Created by leon on 16/4/6.
 */
public class QuicklySwipeApplication extends Application {

    public static final boolean DEBUG = true;

    private static QuicklySwipeApplication INSTANCE = new QuicklySwipeApplication();

    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        initLogUtil();

        initData();

        initView();

    }


    /** start init */

    public void initLogUtil() {
        KLog.init(QuicklySwipeApplication.DEBUG);
    }

    public void initData() {

    }

    public void initView() {
        FloatedViewController.getInstance().showSlidePanel();
    }

    /** end init */



    /** start android lifecycle */

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /** end android lifecycle */


    /** start getters and setters */

    public static QuicklySwipeApplication getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化的时候需要在 application onCreate() 手动指定 mContext, 然后使用 getContext 来获取这个 mContext。
     * 其他时候使用 {@link QuicklySwipeApplication#getInstance()} 或者此函数都可以。
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /** end getters and setters */

}
