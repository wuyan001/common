package com.lanshi.utils.base;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/7/7.
 */

public abstract class BaseRxBindingFragment extends BaseFragment {

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCompositeDisposable = new CompositeDisposable();
    }
    /**
     * 增加订阅
     */
    public void addDisposable(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 消除订阅
     */
    public void clearDisposable(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearDisposable();
    }
}
