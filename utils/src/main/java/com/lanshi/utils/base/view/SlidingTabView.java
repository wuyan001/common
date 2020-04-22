package com.lanshi.utils.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanshi.utils.R;


public class SlidingTabView extends LinearLayout {

  private static final float DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0.5f;
  private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
  private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
  private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

  private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
  private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
  private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;

  private final float mBottomBorderThickness;
  private final Paint mBottomBorderPaint;

  private final int mSelectedIndicatorThickness;
  private final Paint mSelectedIndicatorPaint;

  private final Paint mDividerPaint;
  private final float mDividerHeight;

  private int mSelectedPosition;
  private float mSelectionOffset;

  public SlidingTabView(Context context) {
    this(context, null);
  }

  public SlidingTabView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
    final float density = getResources().getDisplayMetrics().density;

    TypedValue outValue = new TypedValue();
    getContext().getTheme()
                .resolveAttribute(android.R.attr.colorForeground, outValue, true);
    final int themeForegroundColor = outValue.data;

    mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
    mBottomBorderPaint = new Paint();
    mBottomBorderPaint.setColor(getResources().getColor(R.color.c109));

    mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
    mSelectedIndicatorPaint = new Paint();
    mSelectedIndicatorPaint.setColor(getResources().getColor(R.color.c100));

    mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
    mDividerPaint = new Paint();
    mDividerPaint.setStrokeWidth((int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density));
    mDividerPaint.setColor(getResources().getColor(R.color.c100));
  }

  void viewPagerChange(int position, float offset) {
    this.mSelectedPosition = position;
    this.mSelectionOffset = offset;
    if (offset == 0) {
      for (int i = 0; i < getChildCount(); i++) {
        TextView child = (TextView) getChildAt(i);
        child.setTextColor(getContext().getResources()
                                       .getColor(R.color.c103));
      }

      TextView selectedTitle = (TextView) getChildAt(mSelectedPosition);
      selectedTitle.setTextColor(getContext().getResources()
                                             .getColor(R.color.c101));
    }

    invalidate();
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    final int height = getHeight();
    final int childCount = getChildCount();

    if (childCount > 0) {
      TextView selectedTitle = (TextView) getChildAt(mSelectedPosition);
      int left = selectedTitle.getLeft();
      int right = selectedTitle.getRight();
      selectedTitle.setTextColor(blendColors(getContext().getResources()
                                                         .getColor(R.color.c103),
                                             getContext().getResources()
                                                         .getColor(R.color.c101),
                                             mSelectionOffset));

      if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {

        TextView nextTitle = (TextView) getChildAt(mSelectedPosition + 1);
        left = (int) (mSelectionOffset * nextTitle.getLeft() + (1.0f - mSelectionOffset) * left);
        right = (int) (mSelectionOffset * nextTitle.getRight() + (1.0f - mSelectionOffset) * right);
        nextTitle.setTextColor(blendColors(getContext().getResources()
                                                       .getColor(R.color.c101),
                                           getContext().getResources()
                                                       .getColor(R.color.c103),
                                           mSelectionOffset));
      } else {
        for (int i = 0; i < childCount; i++) {
          if (i == mSelectedPosition) {
            continue;
          }

          TextView otherTitle = (TextView) getChildAt(i);
          otherTitle.setTextColor(blendColors(getContext().getResources()
                                                          .getColor(R.color.c101),
                                              getContext().getResources()
                                                          .getColor(R.color.c103),
                                              mSelectionOffset));
        }
      }

      canvas.drawRect(left,
                      height - mSelectedIndicatorThickness,
                      right,
                      height,
                      mSelectedIndicatorPaint);
    }

    canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);

     /*
      * Tab 之间分割线
      *
      * int dividerHeightPx = (int) (Math.min(Math.max(0f, mDividerHeight), 1f) * height);
      * int separatorTop = (height - dividerHeightPx) / 2;
      * for (int i = 0; i < childCount - 1; i++) {
      *   View child = getChildAt(i);
      *   canvas.drawLine(child.getRight(),
      *                   separatorTop,
      *                   child.getRight(),
      *                   separatorTop + dividerHeightPx,
      *                   mDividerPaint);
      * }
      */

  }

  private static int blendColors(int color1, int color2, float ratio) {
    final float inverseRation = 1f - ratio;
    float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
    float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
    float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
    return Color.rgb((int) r, (int) g, (int) b);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }
}