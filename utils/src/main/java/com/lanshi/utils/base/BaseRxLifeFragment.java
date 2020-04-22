package com.lanshi.utils.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanshi.utils.R;
import com.lanshi.utils.base.iml.IFailLayout;
import com.lanshi.utils.base.iml.ViewHelper;
import com.lanshi.utils.base.interfaces.IViewHelper;
import com.lanshi.utils.base.utils.KeyBoardUtils;
import com.trello.rxlifecycle2.components.RxFragment;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * Created by admin on 2018/7/7.
 */

public abstract  class BaseRxLifeFragment extends RxFragment implements IViewHelper {

    private LayoutInflater mInflater;
    private View mRootView;

    ViewHelper mViewHelper = new ViewHelper() {
        @Override
        public <T> T getView(int id, Class<T> clazz) {
            if (viewMap.containsKey(id)) {
                return (T) viewMap.get(id);
            } else {
                View view = mRootView.findViewById(id);
                viewMap.put(id, view);
                return (T) view;
            }
        }
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInflater = LayoutInflater.from(context);

    }
    @Override
    public void initGlobalFrameLayout(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        mViewHelper.initGlobalFrameLayout(onClickReloadButtonListener);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void inject(int layoutId) {
        this.mRootView = mInflater.inflate(layoutId, null);
        mRootView.setBackgroundColor(getBackGroundColor());

    }

   public  void gotoActivity(Intent intent){
       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
   }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private int getBackGroundColor() {
        return getContext().
                getResources()
                .getColor(R.color.c108);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initParmers();
        initViews();

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initValues();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.closeKeybord(getActivity());
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
        return mViewHelper.getView(id,clazz);
    }



    protected abstract void initParmers();
    protected abstract void initViews();
    protected abstract void initValues();

}




















