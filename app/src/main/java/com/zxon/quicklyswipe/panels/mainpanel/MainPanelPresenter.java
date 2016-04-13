package com.zxon.quicklyswipe.panels.mainpanel;

/**
 * Created by leon on 16/4/8.
 */
public class MainPanelPresenter implements MainPanelContract.Presenter {

    private MainPanelContract.View mView;

    public MainPanelPresenter(MainPanelContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void handleBackEvent() {
        mView.back();
    }
}
