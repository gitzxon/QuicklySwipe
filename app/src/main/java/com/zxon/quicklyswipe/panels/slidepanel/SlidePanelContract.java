package com.zxon.quicklyswipe.panels.slidepanel;

import com.zxon.quicklyswipe.panels.BasePanelPresenter;
import com.zxon.quicklyswipe.panels.BasePanelView;

/**
 * Created by leon on 16/4/7.
 */
interface SlidePanelContract {

    interface View extends BasePanelView {

        void adjustSlideArea(float zoom);

        void setColor(int color);

        void show();

        void hide();

        void hideForMoment(long time);

        void slide();

        void back();

    }

    interface Presenter extends BasePanelPresenter {

        void handleClickEvent();

        void handleSlideEvent();

    }
}
