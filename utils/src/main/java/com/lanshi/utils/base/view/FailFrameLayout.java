package com.lanshi.utils.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lanshi.utils.R;
import com.lanshi.utils.base.iml.IFailLayout;
import com.lanshi.utils.base.utils.AnimationUtils;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by admin on 2018/7/2.
 */

public class FailFrameLayout extends FrameLayout implements View.OnClickListener{
    private IFailLayout.OnClickReloadButtonListener onReloadButtonListener;
    private LoadingFrameLayout loadingView;
    private ImageView ivProgress;

    public FailFrameLayout(@NonNull Context context) {
        super(context);
        initView();


    }

    public FailFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public FailFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setId(R.id.dataContent_fail);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_fail, this);
        ImageView iv_fail = view.findViewById(R.id.iv_fail);
        iv_fail.setOnClickListener(this);

    }

    /**
     * 当前页面,点击重新加载
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_fail) {
            if (onReloadButtonListener != null) {
                loadingView.setVisibility(VISIBLE);
                AnimationUtils.startImageAnim(ivProgress);
                onReloadButtonListener.onClick(v);
            }
        }
    }

    /**
     * 出错重新加载
     * @param onClickReloadButtonListener
     */
    public void setOnClickReloadButton(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        this.onReloadButtonListener = onClickReloadButtonListener;
    }

    public void setLoadingView(LoadingFrameLayout loadingView, ImageView ivProgress) {
        this.loadingView = loadingView;
        this.ivProgress = ivProgress;
    }
}
