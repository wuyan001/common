package com.lanshi.utils.base;


import android.view.View;

import com.lanshi.utils.base.iml.IPresenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;


/**
 * Created by admin on 2017/5/9.
 */
public abstract class BaseHolder<T>implements IPresenter {

    public View mRootView;
    public T data;

    public BaseHolder() {
        mRootView = initView();
    }

    public abstract View initView();

    public View getHolderView() {
        return mRootView;
    }


    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onDestory(LifecycleOwner owner) {

    }

    @Override
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {

    }
}
