package com.lanshi.utils.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lanshi.utils.R;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by admin on 2018/7/2.
 */

public class LoadingFrameLayout extends FrameLayout {
    public LoadingFrameLayout(@NonNull Context context) {
        super(context);
        initView();

    }

    public LoadingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public LoadingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        setId(R.id.dataContent_fail);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_loading,this);



    }
}
