package com.lanshi.utils.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanshi.utils.R;

import androidx.annotation.Nullable;


/**
 * Created by admin on 2018/7/21.
 */

public class TotalView extends LinearLayout {
    private int total_color;
    private int text_color;
    private TextView mTv_total;
    private int total;
    private TextView mTv_1;
    private TextView mTv_2;

    public TotalView(Context context) {
        super(context);
        initView();
    }



    public TotalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initattrs(context, attrs);
    }

    public TotalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initattrs(context, attrs);
    }

    private void initattrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TotalView);

        total_color = typedArray.getColor(R.styleable.TotalView_color_total,
                context.getResources().getColor(R.color.c100));
        text_color = typedArray.getColor(R.styleable.TotalView_color,
                context.getResources().getColor(R.color.c100));

        total = typedArray.getInt(R.styleable.TotalView_num,
                0);

        typedArray.recycle();
        initView();
    }

    private void initView() {
        setId(R.id.totalview);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.view_total, this);
        mTv_total = view.findViewById(R.id.tv_total);
        mTv_1 = view.findViewById(R.id.tv_1);
        mTv_2 = view.findViewById(R.id.tv_2);
        initColor();
        mTv_total.setText(total+"");


    }

    private void initColor() {
        mTv_1.setTextColor(text_color);
        mTv_2.setTextColor(text_color);
        mTv_total.setTextColor(total_color);
    }

    public void setTotal(int total) {
        if (mTv_total != null) {
            mTv_total.setText(total + "");
        }
    }
}


































