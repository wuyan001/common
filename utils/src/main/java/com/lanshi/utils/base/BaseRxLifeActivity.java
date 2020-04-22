package com.lanshi.utils.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.lanshi.utils.R;
import com.lanshi.utils.base.iml.IFailLayout;
import com.lanshi.utils.base.iml.Intractor;
import com.lanshi.utils.base.iml.ViewHelper;
import com.lanshi.utils.base.interfaces.IViewControl;
import com.lanshi.utils.base.interfaces.IViewHelper;
import com.lanshi.utils.base.utils.KeyBoardUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.LayoutRes;


/**
 * Created by admin on 2018/7/7.
 */

public abstract class BaseRxLifeActivity extends RxAppCompatActivity implements IViewControl, IViewHelper {


    ViewHelper mViewHelper = new ViewHelper() {
        @Override
        public <T> T getView(int id, Class<T> clazz) {
            if (viewMap.containsKey(id)) {
                return (T) viewMap.get(id);
            } else {
                View view = findViewById(id);
                viewMap.put(id, view);
                return (T) view;
            }
        }
    };
    Intractor mIntractor = new Intractor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inParameter();
        setContentView(getLlayoutId());
        initViewCreate();
        initValue();


    }
    @Override
    public void initGlobalFrameLayout(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        mViewHelper.initGlobalFrameLayout(onClickReloadButtonListener);
    }
    /**
     *点击软件盘消失
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (mIntractor.isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout parent_view = (FrameLayout) inflater.inflate(R.layout.layout_base_parent, null);
        View rootView = inflater.inflate(layoutResID, null);
        rootView.getRootView().setBackgroundColor(getBackgroundColor());
        parent_view.addView(rootView);
        super.setContentView(parent_view);
    }

    public int getBackgroundColor() {
        return getResources().getColor(R.color.white);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyBoardUtils.closeKeybord(this);

    }

    @Override
    public void showEmptyView() {
        mViewHelper.showEmptyView();
    }

    @Override
    public void showFailView() {
        mViewHelper.showFailView();
    }

    @Override
    public void showLoadingView() {
        mViewHelper.showLoadingView();
    }

    @Override
    public void showRealView() {
        mViewHelper.showRealView();
    }

    @Override
    public void setGone(int... id) {
        mViewHelper.setGone(id);
    }

    @Override
    public void setVisble(int... id) {
        mViewHelper.setVisble(id);
    }

    @Override
    public <T> T getView(int id, Class<T> clazz) {
        return mViewHelper.getView(id, clazz);
    }

    /**
     *软键盘显示/消失监听
     */
    public void setSoftChangeListener( Intractor.IKeyBoardVisibleListener listener){
        mIntractor.addOnSoftKeyBoardVisibleListener(this,listener);
    }

}
