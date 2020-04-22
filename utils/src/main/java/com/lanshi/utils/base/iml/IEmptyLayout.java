package com.lanshi.utils.base.iml;

import android.view.View;

/**
 * Created by admin on 2019/1/25.
 */

public interface IEmptyLayout {
    interface OnClickEmptyLayoutListener {
        void onClick(View v);
    }

    void setOnClickReloadButton(OnClickEmptyLayoutListener onClickReloadButtonListener);

    void setEmptyView(View view);

    void setEmptyText(String buttonText);
}
