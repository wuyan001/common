package com.lanshi.utils.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanshi.utils.R;
import com.lanshi.utils.base.iml.IFailLayout;
import com.lanshi.utils.base.iml.Intractor;
import com.lanshi.utils.base.iml.ViewHelper;
import com.lanshi.utils.base.interfaces.IViewControl;
import com.lanshi.utils.base.interfaces.IViewHelper;
import com.lanshi.utils.base.interfaces.SoftwareInputLayoutListener;
import com.lanshi.utils.base.utils.KeyBoardUtils;
import com.lanshi.utils.base.view.TitleView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by admin on 2018/7/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements IViewControl, IViewHelper {

    TextView softwareInputName;
    EditText softwareInputEdit;
    TextView softwareInputClear;
    TextView softwareInputOption;
    LinearLayout softwareInputLayout;

    SoftwareInputLayoutListener softwareInputLayoutListener;

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
    public void setContentView( int layoutResID) {


        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout parent_view = (FrameLayout) inflater.inflate(R.layout.layout_base_parent, null);
        View rootView = inflater.inflate(layoutResID, null);
       // rootView.getRootView().setBackgroundColor(getBackgroundColor());
        parent_view.addView(rootView);
        if (getClass().getSuperclass() == BaseSoftwareActivity.class){
            View softView = inflater.inflate(R.layout.layout_edittext_header, null);
            softwareInputName = softView.findViewById(R.id.software_input_name);
            softwareInputEdit = softView.findViewById(R.id.software_input_edit);
            softwareInputClear = softView.findViewById(R.id.software_input_clear);
            softwareInputOption = softView.findViewById(R.id.software_input_option);
            softwareInputLayout = softView.findViewById(R.id.software_input_layout);
            parent_view.addView(softView);
        }

        super.setContentView(parent_view);
    }

    public int getBackgroundColor() {
        return getResources().getColor(R.color.cbg);
    }

    @Override
    public void initGlobalFrameLayout(IFailLayout.OnClickReloadButtonListener onClickReloadButtonListener) {
        mViewHelper.initGlobalFrameLayout(onClickReloadButtonListener);
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

    @Override
    public TitleView getTitleView() {
      return   mViewHelper.getTitleView();
    }


    @Override
    public void setTotal(int total) {
        mViewHelper.setTotal(total);
    }
}
