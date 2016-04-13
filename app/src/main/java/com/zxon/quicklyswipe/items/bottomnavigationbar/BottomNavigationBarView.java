package com.zxon.quicklyswipe.items.bottomnavigationbar;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gigamole.library.NavigationTabBar;
import com.zxon.quicklyswipe.R;
import com.zxon.quicklyswipe.items.BaseItemPresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by leon on 16/4/13.
 */
public class BottomNavigationBarView extends RelativeLayout implements BottomNavigationBarContract.View {

    private BottomNavigationBarPresenter mPresenter;

    private Context mContext;

    private NavigationTabBar mBottomBar;

    private ArrayList<NavigationTabBar.Model> mModels;

    public BottomNavigationBarView(Context context) {
        super(context);
        mContext = context;

        mPresenter = new BottomNavigationBarPresenter(this);

        initView();
    }

    private void initView() {
        mBottomBar = new NavigationTabBar(mContext);
        ArrayList<NavigationTabBar.Model> items = new ArrayList<>();
        items.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.tools), getResources().getColor(R.color.localRed), null));
        items.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.apps), getResources().getColor(R.color.localDarkGreen), null));
        items.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.people), getResources().getColor(R.color.localBlue), null));

        mModels = items;

        mBottomBar.setModels(mModels);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mBottomBar, params);
    }


    @Override
    public void setPresenter(BaseItemPresenter presenter) {
        mPresenter = (BottomNavigationBarPresenter) presenter;
    }
}
