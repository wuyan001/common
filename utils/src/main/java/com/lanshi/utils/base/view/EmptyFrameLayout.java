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

public class EmptyFrameLayout extends FrameLayout implements View.OnClickListener{
    private IFailLayout.OnClickReloadButtonListener onReloadButtonListener;
    private LoadingFrameLayout loadingView;
    private ImageView ivProgress;

    public EmptyFrameLayout(@NonNull Context context) {
        super(context);
        initView();


    }

    public EmptyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public EmptyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setId(R.id.dataContent_empety);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this);
        ImageView iv_empty = view.findViewById(R.id.iv_empty);
        iv_empty.setOnClickListener(this);

    }

    public void setOnClickReloadButton(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        this.onReloadButtonListener = onClickReloadButtonListener;
    }

    public void setLoadingView(LoadingFrameLayout loadingFrameLayout, ImageView ivProgress) {
        this.loadingView = loadingFrameLayout;
        this.ivProgress = ivProgress;

    }
    /**
     * 当前页面,点击重新加载
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_empty) {
            if (onReloadButtonListener != null) {
                loadingView.setVisibility(VISIBLE);
                AnimationUtils.startImageAnim(ivProgress);
                onReloadButtonListener.onClick(v);
            }
        }
    }

}
