package com.lanshi.utils.base;


import com.lanshi.utils.base.iml.IPresenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by admin on 2018/7/23.
 */

public class BasePresenter implements IPresenter {

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
