package com.lanshi.utils.base.iml;

import android.view.View;

/**
 * Created by admin on 2019/1/25.
 */

public interface IFailLayout {
    interface OnClickReloadButtonListener{
        void onClick(View v);
    }

    void setOnClickReloadButton(OnClickReloadButtonListener onClickReloadButtonListener);

    void setFailView(int failImageResId);

}
