package com.zxon.quicklyswipe.panels.mainpanel;

import com.zxon.quicklyswipe.panels.BasePanelPresenter;
import com.zxon.quicklyswipe.panels.BasePanelView;

/**
 * Created by leon on 16/4/8.
 */
interface MainPanelContract {

    interface View extends BasePanelView {

        void back();
        void hide();

    }

    interface Presenter extends BasePanelPresenter {

        void handleBackEvent();

    }

}
