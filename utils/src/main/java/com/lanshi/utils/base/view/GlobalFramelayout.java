package com.lanshi.utils.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lanshi.utils.R;
import com.lanshi.utils.base.iml.IFailLayout;
import com.lanshi.utils.base.iml.INodata;
import com.lanshi.utils.base.utils.AnimationUtils;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by admin on 2018/7/2.
 */

public class GlobalFramelayout extends FrameLayout implements INodata {
    Context mContext;
    private LoadingFrameLayout mLoadingFrameLayout;
    private EmptyFrameLayout mEmptyFrameLayout;
    private FailFrameLayout mFailFrameLayout;
    private View mRealContent;
    private LayoutParams match_parent_layoutParams = new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    private ImageView ivProgress;

    public GlobalFramelayout(@NonNull Context context) {
        super(context);
        setId(R.id.globalframelayout);
        init();
    }

    public GlobalFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setId(R.id.globalframelayout);
        init();
    }


    public GlobalFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setId(R.id.globalframelayout);
        init();

    }

    private void init() {
        initField();
        initView();
    }

    private void initView() {
        mLoadingFrameLayout = new LoadingFrameLayout(getContext());
        mLoadingFrameLayout.setLayoutParams(match_parent_layoutParams);
        ivProgress = (ImageView) mLoadingFrameLayout.findViewById(R.id.iv_progress);

        mEmptyFrameLayout = new EmptyFrameLayout(getContext());
        mEmptyFrameLayout.setLayoutParams(match_parent_layoutParams);
        mEmptyFrameLayout.setLoadingView(mLoadingFrameLayout, ivProgress);

        mFailFrameLayout = new FailFrameLayout(getContext());
        mFailFrameLayout.setLayoutParams(match_parent_layoutParams);
        mFailFrameLayout.setLoadingView(mLoadingFrameLayout, ivProgress);
    }

    /**
     * 可以初始化一个动画
     */
    private void initField() {

    }






    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fillLayout();
        ininValues();
    }

    private void fillLayout() {
        mRealContent = getRealContent();
        try {
            addView(mRealContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addView(mLoadingFrameLayout);
        addView(mEmptyFrameLayout);
        addView(mFailFrameLayout);

    }

    private void ininValues() {
        mLoadingFrameLayout.setVisibility(View.GONE);
        mEmptyFrameLayout.setVisibility(View.GONE);
        mFailFrameLayout.setVisibility(View.GONE);
    }

    protected View getRealContent() {
        View view = getChildAt(0);
        return view;
    }

    @Override
    public void showLoadingView() {
        mLoadingFrameLayout.setVisibility(View.VISIBLE);
        AnimationUtils.startImageAnim(ivProgress);
        mEmptyFrameLayout.setVisibility(View.GONE);
        mFailFrameLayout.setVisibility(View.GONE);
        mRealContent.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        mLoadingFrameLayout.setVisibility(View.GONE);
        mEmptyFrameLayout.setVisibility(View.VISIBLE);
        mFailFrameLayout.setVisibility(View.GONE);
        mRealContent.setVisibility(View.GONE);
    }

    @Override
    public void showFailView() {
        mLoadingFrameLayout.setVisibility(View.GONE);
        mEmptyFrameLayout.setVisibility(View.GONE);
        mFailFrameLayout.setVisibility(View.VISIBLE);
        mRealContent.setVisibility(View.GONE);
    }

    @Override
    public void showRealView() {
        mLoadingFrameLayout.setVisibility(View.GONE);
        mEmptyFrameLayout.setVisibility(View.GONE);
        mFailFrameLayout.setVisibility(View.GONE);
        mRealContent.setVisibility(View.VISIBLE);
    }

    /**
     * 空数据,错误数据,点击重新加载
     * @param onClickReloadButtonListener
     */
    public void setOnClickReloadButton(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        mFailFrameLayout.setOnClickReloadButton(onClickReloadButtonListener);
        mEmptyFrameLayout.setOnClickReloadButton(onClickReloadButtonListener);
    }
}
