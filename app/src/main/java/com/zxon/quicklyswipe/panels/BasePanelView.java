package com.zxon.quicklyswipe.panels;

import com.zxon.quicklyswipe.BaseMVPView;

/**
 * Created by leon on 16/4/7.
 */
public interface BasePanelView extends BaseMVPView<BasePanelPresenter> {

    void setPresenter(BasePanelPresenter presenter);

    void createPresenterForThisView(BasePanelView view);

    void hide();

    void back();

}
