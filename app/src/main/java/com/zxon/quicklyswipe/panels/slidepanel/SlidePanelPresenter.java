package com.zxon.quicklyswipe.panels.slidepanel;

import com.socks.library.KLog;

/**
 * Created by leon on 16/4/7.
 */
public class SlidePanelPresenter implements SlidePanelContract.Presenter {

    private SlidePanelContract.View mView;

    public SlidePanelPresenter(SlidePanelContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void handleClickEvent() {
        KLog.d("click event on slide panel");
        mView.hideForMoment(500l);
    }

    @Override
    public void handleSlideEvent() {
        KLog.d("slide event on slide panel");
        mView.slide();
    }
}
