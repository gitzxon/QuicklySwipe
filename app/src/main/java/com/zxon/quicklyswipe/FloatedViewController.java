package com.zxon.quicklyswipe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.zxon.quicklyswipe.panels.BasePanelView;
import com.zxon.quicklyswipe.panels.mainpanel.MainPanelView;
import com.zxon.quicklyswipe.panels.slidepanel.SlidePanelView;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by leon on 16/4/6.
 */
public class FloatedViewController {

    public static OnBackListener sCommonHandleKeyBackPressed = new OnBackListener();

    private static List<BasePanelView> sViewManager = new LinkedList<>();

    private static FloatedViewController ourInstance = new FloatedViewController();

    private Context mContext; // application context
    private WindowManager mWindowManager;

    private SlidePanelView mSlidePanelView;
    private MainPanelView mMainPanelView;

    private FloatedViewController() {

        mContext = QuicklySwipeApplication.getContext();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mContext.registerReceiver(new SystemDialogDismissBroadcastReceiver(), filter);

    }

    /**
     * start view control
     */

    public void showSlidePanel() {

        if (mSlidePanelView == null) {
            mSlidePanelView = new SlidePanelView(mContext);
        }

        if (!sViewManager.contains(mSlidePanelView)) {
            addView(mSlidePanelView, mSlidePanelView.genDefaultLayoutParams());
        }

    }

    public void hideSlidePanel() {

        if (mSlidePanelView != null) {
            removeView(mSlidePanelView);
        }

    }

    public void hideSlidePanelForMoment(long delayTime) {
        if (mSlidePanelView == null) {
            return;
        }

        mSlidePanelView.setVisibility(View.GONE);

        Observable<String> observable = Observable.empty();
        observable.observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(delayTime, TimeUnit.MILLISECONDS) // 500ms
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mSlidePanelView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSlidePanelView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(String s) {

                    }
                });

    }

    public void showMainPanel() {
        if (mMainPanelView == null) {
            mMainPanelView = new MainPanelView(mContext);
            mMainPanelView.setFocusableInTouchMode(true);
            
        }

        if (sViewManager.contains(mMainPanelView)) {
            return;
        }

        addView(mMainPanelView, mMainPanelView.genDefaultLayoutParams());

    }

    public void hideMainPanel() {
        if (mMainPanelView != null) {
            removeView(mMainPanelView);
        }
    }

    private void addView(View v, WindowManager.LayoutParams layoutParams) {
        getWindowManager().addView(v, layoutParams);
        if (v instanceof BasePanelView) {
            sViewManager.add((BasePanelView) v);
        }
    }

    private void removeView(View v) {

        if (v instanceof BasePanelView) {
            if (sViewManager.contains(v)) {
                getWindowManager().removeView(v);
                sViewManager.remove(v);
            }
        }

    }

    private WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;

    }

    /** end view control */


    /**
     * start static utils
     */

    public static int getFloatedWindowType() {
        if (Build.VERSION.SDK_INT >= 19) {
            return WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            return WindowManager.LayoutParams.TYPE_PHONE;
        }
    }


    /** end static utils */


    /**
     * start getters and setters
     */

    public static FloatedViewController getInstance() {
        return ourInstance;
    }


    /**
     * end getters and setters
     */


    public static class OnBackListener implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_UP) {
                if (v instanceof BasePanelView) {
                    ((BasePanelView) v).back();
                }
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * start handling KEY_HOME
     */

    private class SystemDialogDismissBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // hide every view except the slide panel
            hideMainPanel();

            showSlidePanel();
        }

    }

    /** end handling KEY_HOME */

}
