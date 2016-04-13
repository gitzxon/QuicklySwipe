package com.zxon.quicklyswipe.panels.slidepanel;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zxon.quicklyswipe.panels.BasePanelPresenter;
import com.zxon.quicklyswipe.panels.BasePanelView;
import com.zxon.quicklyswipe.FloatedViewController;
import com.zxon.quicklyswipe.util.ViewUtil;

/**
 * Created by leon on 16/4/6.
 */
public class SlidePanelView extends RelativeLayout implements SlidePanelContract.View {

    public Context pContext;
    public WindowManager.LayoutParams pLayoutParams;


    private static final int SLIDE_THRESHOLD_2_POW = (int) Math
            .pow(10 * ViewUtil.SCREEN_DENSITY, 2);


    private RelativeLayout mRoot;
    private RelativeLayout.LayoutParams mLocalLayoutParams;
    private SlidePanelContract.Presenter mPresenter;
    private boolean mIsSlideEventDetected;
    private float mDownX;
    private float mDownY;
    private boolean mIsSlided;

    public SlidePanelView(Context context) {
        super(context);

        pContext = context;

        createPresenterForThisView(this);

        initView();

        initLayoutParams();

    }

    /**
     * start init
     */

    public void initView() {

        mRoot = (RelativeLayout) getRootView();

        View view = new View(pContext);
        view.setBackgroundColor(Color.RED);

        mLocalLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mRoot.addView(view, mLocalLayoutParams);

    }

    public void initLayoutParams() {
        pLayoutParams = genDefaultLayoutParams();
    }


    /** end init */


    /**
     * start touch
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {

                mIsSlideEventDetected = false;
                mDownX = x;
                mDownY = y;

            }
            break;
            case MotionEvent.ACTION_MOVE: {

                if (!mIsSlideEventDetected) {
                    if (Math.pow(x - mDownX, 2) + Math.pow(y - mDownY, 2) > SLIDE_THRESHOLD_2_POW) {

                        mIsSlideEventDetected = true;

                        mPresenter.handleSlideEvent();

                    }
                }

            }
            break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {

                if (!mIsSlideEventDetected) {
                    // on click
                    mPresenter.handleClickEvent();
                }

            }
            break;
        }

        return super.onTouchEvent(event);

    }

    /** end touch */


    /** start util */

    /**
     * by default:
     * width = MATCH_PARENT,
     * height = StatusBarHeight
     *
     * @return
     */
    public WindowManager.LayoutParams genDefaultLayoutParams() {

        int slidePanelHeight = ViewUtil.getStatusBarHeight();
        return ViewUtil.genLayoutParams(WindowManager.LayoutParams.MATCH_PARENT, slidePanelHeight, 0, ViewUtil.getScreenHeight() - slidePanelHeight, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }


    /** end util */



    /**
     * start implement {@link com.zxon.quicklyswipe.panels.slidepanel.SlidePanelContract.View}
     */


    @Override
    public void createPresenterForThisView(BasePanelView view) {
        new SlidePanelPresenter(this);
    }

    @Override
    public void setPresenter(BasePanelPresenter presenter) {
        mPresenter = (SlidePanelContract.Presenter) presenter;
    }

    @Override
    public void adjustSlideArea(float zoom) {

    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void show() {
        FloatedViewController.getInstance().showSlidePanel();
    }

    @Override
    public void hide() {
        FloatedViewController.getInstance().hideSlidePanel();
    }

    @Override
    public void back() {
        // do nothing
    }

    @Override
    public void slide() {
        hide();
        FloatedViewController.getInstance().showMainPanel();
    }

    @Override
    public void hideForMoment(long time) {
        FloatedViewController.getInstance().hideSlidePanelForMoment(time);
    }

    /** end implement {@link com.zxon.quicklyswipe.panels.slidepanel.SlidePanelContract.View} */

}
