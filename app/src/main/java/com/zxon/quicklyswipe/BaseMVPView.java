package com.zxon.quicklyswipe;

/**
 * Created by leon on 16/4/13.
 */
public interface BaseMVPView<T extends BaseMVPPresenter>  {

    void setPresenter(T presenter);

}
