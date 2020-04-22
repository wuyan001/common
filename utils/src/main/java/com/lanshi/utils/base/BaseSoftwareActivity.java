package com.lanshi.utils.base;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.lanshi.utils.base.interfaces.IViewControl;
import com.lanshi.utils.base.interfaces.IViewHelper;
import com.lanshi.utils.base.interfaces.SoftwareInputLayoutListener;
import com.lanshi.utils.base.utils.KeyBoardUtils;
import com.lanshi.utils.base.view.TextWatcherAdapter;


public abstract class BaseSoftwareActivity  extends BaseActivity implements IViewControl, IViewHelper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initsoftView();
        setDefaultEditName();
    }




    private  void initsoftView(){
        globalSoftwareListener();
        initSoftwareInput();

    }
    private void initSoftwareInput() {
        softwareInputEdit.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (softwareInputLayoutListener != null) {
                    softwareInputLayoutListener.onEditTextChange(s);
                }
            }
        });

        softwareInputOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (softwareInputLayoutListener != null) {
                    softwareInputLayoutListener.onOptionClick(softwareInputEdit.getText()
                            .toString());
                }
            }
        });
    }

    private int lastHeight;
    private int number;
    private void globalSoftwareListener() {
        final View view = getWindow().getDecorView();
        view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        view.getWindowVisibleDisplayFrame(rect);
                        int displayHeight = rect.bottom - rect.top;
                        int maxHeight = view.getHeight();
                        int height = maxHeight - displayHeight;
                        if (height <= 200 && height != lastHeight && number >= 1) {
                            softwareInputLayout.setVisibility(View.GONE);
                            if (softwareInputLayoutListener != null) {
                                softwareInputLayoutListener.onSoftwareDimiss();
                            }
                        }
                        lastHeight = height;
                        number++;
                    }
                });
    }

    /**
     * 截取点击输入布局事件，防止软键盘消失
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (softwareInputLayout == null || softwareInputLayout.getVisibility() == View.GONE) {
            return super.dispatchTouchEvent(event);
        }
        // 获取相对于屏幕左上角的 x 坐标值
        float x = event.getRawX();
        // 获取相对于屏幕左上角的 y 坐标值
        float y = event.getRawY();

        RectF softwareInputNameRectF = calcViewScreenLocation(softwareInputName);
        if (softwareInputNameRectF.contains(x, y)) {
            return true;
        }

        RectF softwareInputEditRectF = calcViewScreenLocation(softwareInputEdit);
        if (softwareInputEditRectF.contains(x, y)) {
            softwareInputEdit.selectAll();
            return true;
        }

        RectF softwareInputClearRectF = calcViewScreenLocation(softwareInputClear);
        if (softwareInputClearRectF.contains(x, y)) {
            softwareInputEdit.setText("");
            return true;
        }

        RectF softwareInputOptionRectF = calcViewScreenLocation(softwareInputOption);
        if (softwareInputOptionRectF.contains(x, y)) {
            if (softwareInputLayoutListener != null && event.getAction() == MotionEvent.ACTION_UP) {

                softwareInputLayoutListener.onOptionClick(softwareInputEdit.getText()
                        .toString());
            }
            return true;
        }

        return super.dispatchTouchEvent(event);
    }
    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0],
                location[1],
                location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView()
                .getWindowToken(), 0);
        this.finish();
    }




    public  void setSoftwareInputLayoutListener(SoftwareInputLayoutListener softwareInputLayoutListener2) {
        softwareInputLayoutListener = softwareInputLayoutListener2;
    }

    public void showSoftware(String initText) {
        softwareInputLayout.setVisibility(View.VISIBLE);
        lastHeight = 0;
        number = 0;
        softwareInputEdit.requestFocus();

        KeyBoardUtils.openKeybord(softwareInputEdit, this);
        softwareInputEdit.setText(initText);
        softwareInputEdit.setSelection(initText == null
                ? 0
                : initText.length());
        softwareInputEdit.selectAll();
    }

    public void setEdittexts(String text) {
        if (softwareInputEdit != null) {
            softwareInputEdit.setText(text);
            softwareInputEdit.setSelection(text == null
                    ? 0
                    : text.length());
            softwareInputEdit.selectAll();
        }
    }
    public void setEditName(String editName){
        softwareInputName.setText(editName);
    }

    public boolean getIsShowing() {
        return softwareInputLayout.getVisibility() == View.VISIBLE;
    }

    public void hideSoftware() {
        softwareInputEdit.clearFocus();
        KeyBoardUtils.closeKeybord(softwareInputEdit, this);
    }

    /**
     * 布局填存完成,可以给editext设置内容
     */
    protected abstract void setDefaultEditName();
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
