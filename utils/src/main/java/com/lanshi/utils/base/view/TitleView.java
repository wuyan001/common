package com.lanshi.utils.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanshi.utils.R;

import androidx.annotation.Nullable;


/**
 * Created by admin on 2018/7/12.
 */

public class TitleView extends LinearLayout implements View.OnClickListener {

    private  int mBackGroundColor;
    private String mTitle;
    private int mTextColor;
    private int mTextSize;
    private Context context;
    private boolean mIsShowBack;
    private TextView mTv_title;


    public TitleView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initattrs(context,attrs);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initattrs(context,attrs);
    }
  private  void initattrs(Context context, @Nullable AttributeSet attrs){
      this.context = context;
      TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
      mTitle = typedArray.getString(R.styleable.TitleView_title);
      mTextColor = typedArray.getColor(R.styleable.TitleView_textcolor,
              context.getResources().getColor(R.color.white));
      mBackGroundColor = typedArray.getColor(R.styleable.TitleView_backgroundcolor,
              context.getResources().getColor(R.color.c101));
      //默认36px
      mTextSize = typedArray.getDimensionPixelOffset(R.styleable.TitleView_textsize,
              14);
      mIsShowBack = typedArray.getBoolean(R.styleable.TitleView_showback, false);


      typedArray.recycle();
      initView();

  }
    public void initView(){
        setId(R.id.titleview);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.view_title, this);
        mTv_title = view.findViewById(R.id.tv_title);
        RelativeLayout rootView = view.findViewById(R.id.rl_root);
        ImageView iv_back = view.findViewById(R.id.iv_back);
        mTv_title.setText(mTitle);
        mTv_title.setTextColor(mTextColor);
        mTv_title.setTextSize(mTextSize);
        rootView.setBackgroundColor(mBackGroundColor);
        iv_back.setVisibility(mIsShowBack ? View.VISIBLE : View.GONE);
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
                ((Activity)context).finish();
        }
    }
    public void setTitle(String text){
        mTv_title.setText(text);

    }

}
