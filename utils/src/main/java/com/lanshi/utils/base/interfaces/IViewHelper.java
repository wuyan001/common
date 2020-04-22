package com.lanshi.utils.base.interfaces;


import com.lanshi.utils.base.iml.IFailLayout;
import com.lanshi.utils.base.iml.INodata;
import com.lanshi.utils.base.view.TitleView;

/**
 * Created by admin on 2018/7/2.
 */

public interface IViewHelper extends INodata {
    void setVisble(int... id);
    void setGone(int... id);
    //clazz---用来表明T是view
    <T>T getView(int id, Class<T> clazz);
    TitleView getTitleView();
    void setTotal(int total);

    /**
     * 页面出错,点击页面,重新加载
     */
    void initGlobalFrameLayout(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener);



}
