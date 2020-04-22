package com.lanshi.utils.base.iml;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by admin on 2018/7/23.
 */

public interface IPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner);
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory(LifecycleOwner owner);
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event);
}
