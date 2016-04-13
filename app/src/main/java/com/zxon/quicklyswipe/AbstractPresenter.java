package com.zxon.quicklyswipe;

/**
 * Created by leon on 16/4/13.
 */
public abstract class AbstractPresenter<T extends BaseMVPView>implements BaseMVPPresenter{

    protected T mView;

    public AbstractPresenter(T view) {
        mView = view;
        mView.setPresenter(this);
    }

}
