package com.zxon.quicklyswipe.panels.mainpanel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zxon.quicklyswipe.items.bottomnavigationbar.BottomNavigationBarView;
import com.zxon.quicklyswipe.panels.BasePanelPresenter;
import com.zxon.quicklyswipe.panels.BasePanelView;
import com.zxon.quicklyswipe.FloatedViewController;
import com.zxon.quicklyswipe.R;
import com.zxon.quicklyswipe.util.Util;
import com.zxon.quicklyswipe.util.ViewUtil;

/**
 * Created by leon on 16/4/8.
 */
public class MainPanelView extends RelativeLayout implements MainPanelContract.View {


    private MainPanelContract.Presenter mPresenter;

    private Context mContext;

    private RelativeLayout mRoot;

    private RelativeLayout mMainPanel;

    private LayoutInflater mInflater;

    public MainPanelView(Context context) {
        super(context);
        mContext = context;

        createPresenterForThisView(this);

        initView();

        initEventListener();
    }

    private void initView() {

//        setBackgroundColor(Color.parseColor("#ffffffff"));

        mRoot = this;
        mInflater = LayoutInflater.from(mContext);
        mInflater.inflate(R.layout.view_main_panel, mRoot);

        BottomNavigationBarView mBottomBar = new BottomNavigationBarView(mContext);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2px(mContext, 50)); // 50 dp
        params.addRule(ALIGN_PARENT_BOTTOM);

        mBottomBar.setAlpha(1);

        mRoot.addView(mBottomBar, params);

    }

    private void initEventListener() {
        setOnKeyListener(FloatedViewController.sCommonHandleKeyBackPressed);
    }


    /**
     * start getters and setters
     */

    public WindowManager.LayoutParams genDefaultLayoutParams() {
//        int y = ViewUtil.getStatusBarHeight() + (ViewUtil.getScreenHeight() - ViewUtil.getStatusBarHeight()) / 3;
        WindowManager.LayoutParams params =  ViewUtil.genLayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0,
                0);
//        params.alpha = 0.7f;
        return params;
    }

    /** end getters and setters */




    /**
     * start implementing mvp
     */

    @Override
    public void createPresenterForThisView(BasePanelView view) {
        new MainPanelPresenter(this);
    }


    @Override
    public void setPresenter(BasePanelPresenter presenter) {
        mPresenter = (MainPanelContract.Presenter) presenter;
    }

    @Override
    public void back() {
        hide();
        FloatedViewController.getInstance().showSlidePanel();
    }

    @Override
    public void hide() {
        FloatedViewController.getInstance().hideMainPanel();
    }

    /** end implementing mvp */

}
