package com.lanshi.utils.base.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanshi.utils.R;
import com.lanshi.utils.base.utils.DensityUtils;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class SlidingTabLayout extends HorizontalScrollView {
  private static final int TITLE_OFFSET_DIPS = 8;
  private static final int TAB_VIEW_PADDING_DIPS_TB = 6;
  private static final int TAB_VIEW_PADDING_DIPS = 16;
  private static final int TAB_VIEW_TEXT_SIZE_SP = 14;
  private static final int OFFSET_DP = 8;

  private int mTitleOffset;
  private int offset;
  private boolean isFull = true;

  private int mTabViewLayoutId;
  private int mTabViewTextViewId;

  private ViewPager mViewPager;
  private final SlidingTabView mTabStrip;
  private OnTabClickListener onTabClickListener;

  private int mScreenWidth;
  /**
   * 防止标题长度不一致导致抖动-因为标题对应中线的时候会由于长度变化
   */
  private int tempWidth;

  public SlidingTabLayout(Context context) {
    this(context, null);
  }

  public SlidingTabLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    setHorizontalScrollBarEnabled(false);

    setFillViewport(true);

    mTitleOffset = DensityUtils.dp2px(getContext(), TITLE_OFFSET_DIPS);
    offset = DensityUtils.dp2px(getContext(), OFFSET_DP);

    mTabStrip = new SlidingTabView(context);
    addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager()
                        .getDefaultDisplay()
                        .getMetrics(displayMetrics);
    mScreenWidth = (int) (displayMetrics.widthPixels);
  }

  public void setViewPager(ViewPager viewPager, OnTabClickListener onTabClickListener) {
    this.onTabClickListener = onTabClickListener;
    mTabStrip.removeAllViews();

    mViewPager = viewPager;
    if (viewPager != null) {
      viewPager.addOnPageChangeListener(new InternalViewPagerListener());
      populateTabStrip();
    }
  }

  public void populateTabStrip() {
    if (mViewPager != null) {
      final PagerAdapter adapter = mViewPager.getAdapter();
      final OnClickListener tabClickListener = new TabClickListener();
      /*通过 viewPager 的 item 来确定tab 的个数*/
      int count = adapter.getCount();
      for (int i = 0; i < count; i++) {
        View tabView = null;
        TextView tabTitleView = null;

        if (mTabViewLayoutId != 0) {
          // If there is a custom tab view layout id set, try and inflate it
          tabView = LayoutInflater.from(getContext())
                                  .inflate(mTabViewLayoutId, mTabStrip, false);
          tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
        }

        if (tabView == null) {
          /*创建textView*/
          tabView = createDefaultTabView(getContext());
        }

        if (tabTitleView == null && TextView.class.isInstance(tabView)) {
          tabTitleView = (TextView) tabView;
        }

        if (tabTitleView != null) {
          tabTitleView.setText(adapter.getPageTitle(i));
        }

        if (tabView != null) {
          tabView.setOnClickListener(tabClickListener);
          tabView.setBackgroundResource(R.color.white);
        }
        /*把 textView 放入到自定义的 tab 栏中*/
        mTabStrip.addView(tabView);
      }
    }
  }

  protected TextView createDefaultTabView(Context context) {
    TextView textView = new TextView(context);
    textView.setGravity(Gravity.CENTER);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);

    TypedValue outValue = new TypedValue();
    getContext().getTheme()
                .resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
    textView.setBackgroundResource(outValue.resourceId);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      textView.setAllCaps(true);
    }

    textView.setPadding(offset, offset, offset, offset);

    int height = (int) (45 * getResources().getDisplayMetrics().density);
    LinearLayout.LayoutParams params;
    if (isFull) {
      params = new LinearLayout.LayoutParams(0, height, 1f);
    } else {
      params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
    }

    textView.setLayoutParams(params);
    return textView;
  }

  public void setFull(boolean isFull) {
    this.isFull = isFull;
  }

  /**
   * 这个方法是关键
   * 滚动 scrollview
   */
  private void scrollToTab(int tabIndex, int positionOffset) {
    final int tabStripChildCount = mTabStrip.getChildCount();
    if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
      return;
    }

    /*获取当前选中的 item*/
    View selectedChild = mTabStrip.getChildAt(tabIndex);
    if (selectedChild != null) {
      /*item 的宽度*/
      int width = selectedChild.getWidth();
      /*获取当前 item 的偏移量*/
      int targetScrollX = selectedChild.getLeft() + positionOffset;
      width = tempWidth > 0
              ? tempWidth
              : width;
      tempWidth = width;
      /*item 距离正中间的偏移量*/
      mTitleOffset = (int) ((mScreenWidth - width) / 2.0f);

      if (tabIndex > 0 || positionOffset > 0) {
        /*计算出正在的偏移量*/
        targetScrollX -= mTitleOffset;
      }

      /*这个时候偏移的量就是屏幕的正中间*/
      scrollTo(targetScrollX, 0);
    }
  }

  private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
    private int mScrollState;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      int tabStripChildCount = mTabStrip.getChildCount();
      if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
        return;
      }

      mTabStrip.viewPagerChange(position, positionOffset);

      View selectedTitle = mTabStrip.getChildAt(position);
      int extraOffset = (selectedTitle != null)
                        ? (int) (positionOffset * selectedTitle.getWidth())
                        : 0;
      scrollToTab(position, extraOffset);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
      mScrollState = state;
    }

    @Override
    public void onPageSelected(int position) {
      if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
        mTabStrip.viewPagerChange(position, 0f);
        scrollToTab(position, 0);
      }

      if (onTabClickListener != null) {
        onTabClickListener.onTabClick(position);
      }
    }
  }

  private class TabClickListener implements OnClickListener {
    @Override
    public void onClick(View v) {
      for (int i = 0; i < mTabStrip.getChildCount(); i++) {
        if (v == mTabStrip.getChildAt(i)) {
          mViewPager.setCurrentItem(i);
          return;
        }
      }
    }
  }

  public interface OnTabClickListener {
    void onTabClick(int position);
  }
}