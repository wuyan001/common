package com.lanshi.utils.base.iml;

import android.view.View;

import com.lanshi.utils.R;
import com.lanshi.utils.base.interfaces.IViewHelper;
import com.lanshi.utils.base.view.GlobalFramelayout;
import com.lanshi.utils.base.view.TitleView;
import com.lanshi.utils.base.view.TotalView;

import java.util.HashMap;
import java.util.Map;


public  abstract   class ViewHelper implements IViewHelper {

    //以viewId作为key缓存View
    protected Map<Integer, View> viewMap = new HashMap<>();
    GlobalFramelayout globalFramelayout;

    @Override
    public void setVisble(int... id) {
        for(int i = 0; i < id.length ; i ++){
            getView(id[i], View.class).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setGone(int... id) {
         for(int i = 0; i < id.length ; i ++){
            getView(id[i], View.class).setVisibility(View.GONE);
         }
    }
    /**
     * 重新加载事件
     */
    @Override
    public void initGlobalFrameLayout(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        findGlobalFrameLayout(onClickReloadButtonListener);
    }
    private void findGlobalFrameLayout(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        globalFramelayout = getView(R.id.globalframelayout, GlobalFramelayout.class);
        globalFramelayout.setOnClickReloadButton(onClickReloadButtonListener);
    }

    @Override
    public void showRealView() {
         globalFramelayout = getView(R.id.globalframelayout,GlobalFramelayout.class);
         globalFramelayout.showRealView();
    }


    @Override
    public void showLoadingView() {
         globalFramelayout = getView(R.id.globalframelayout,GlobalFramelayout.class);
        globalFramelayout.showLoadingView();
    }

    @Override
    public void showFailView() {
         globalFramelayout = getView(R.id.globalframelayout,GlobalFramelayout.class);
        globalFramelayout.showFailView();

    }

    @Override
    public void showEmptyView() {
        globalFramelayout = getView(R.id.globalframelayout,GlobalFramelayout.class);
        globalFramelayout.showEmptyView();
    }
    @Override
    public TitleView getTitleView() {
        TitleView titleView = getView(R.id.titleview,TitleView.class);
        return titleView;
    }



    @Override
    public void setTotal(int total) {
        TotalView view = getView(R.id.totalview, TotalView.class);
        view.setTotal(total);
    }
}
