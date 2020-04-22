package com.lanshi.utils.base.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


public class AnimationUtils {

  /**
   * 给试图添加点击效果,让背景变深
   */
  public static void addTouchDrak(View view, boolean isClick) {
    view.setOnTouchListener(VIEW_TOUCH_DARK);

    if (!isClick) {
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
      });
    }
  }

  /**
   * 给试图添加点击效果,让背景变暗
   */
  public static void addTouchLight(View view, boolean isClick) {
    view.setOnTouchListener(VIEW_TOUCH_LIGHT);

    if (!isClick) {
      view.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
        }
      });
    }
  }

  /**
   * 让控件点击时，颜色变深
   */
  public static final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {

    public final float[] BT_SELECTED = new float[] {
        1, 0, 0, 0, -50, 0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0
    };
    public final float[] BT_NOT_SELECTED = new float[] {
        1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        if (v instanceof ImageView) {
          ImageView iv = (ImageView) v;
          iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
        } else {
          v.getBackground()
           .setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
          v.setBackgroundDrawable(v.getBackground());
        }
      } else if (event.getAction() == MotionEvent.ACTION_UP) {
        if (v instanceof ImageView) {
          ImageView iv = (ImageView) v;
          iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
        } else {
          v.getBackground()
           .setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
          v.setBackgroundDrawable(v.getBackground());
        }
      }
      return true;
    }
  };

  /**
   * 让控件点击时，颜色变暗
   */
  public static final OnTouchListener VIEW_TOUCH_LIGHT = new OnTouchListener() {

    public final float[] BT_SELECTED = new float[] {
        1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0
    };
    public final float[] BT_NOT_SELECTED = new float[] {
        1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        if (v instanceof ImageView) {
          ImageView iv = (ImageView) v;
          iv.setDrawingCacheEnabled(true);

          iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
        } else {
          v.getBackground()
           .setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
          v.setBackgroundDrawable(v.getBackground());
        }
      } else if (event.getAction() == MotionEvent.ACTION_UP) {
        if (v instanceof ImageView) {
          ImageView iv = (ImageView) v;
          iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
          BLog.i("变回来");
        } else {
          v.getBackground()
           .setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
          v.setBackgroundDrawable(v.getBackground());
        }
      }
      return true;
    }
  };

  public static void show(final View view) {
    if (view != null) {
      view.animate()
          .translationY(0.0f)
          .alpha(1.0f)
          .setDuration(300)
          .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              view.setVisibility(View.VISIBLE);
            }
          });
    }
  }

  public static void hide(final View view) {
    if (view != null) {
      view.animate()
          .translationY(view.getHeight())
          .alpha(0.0f)
          .setDuration(300)
          .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              view.setVisibility(View.GONE);
            }
          });
    }
  }

  public static void startImageAnim(ImageView ivProgress) {
    if (ivProgress == null) {
      return;
    }

    final AnimationDrawable animationDrawable = (AnimationDrawable) ivProgress.getDrawable();

    //注意这里，如果你的图片控件用的是setImageResource ,你这里应该使用getDrawable();
    ivProgress.getViewTreeObserver()
              .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                  if (animationDrawable != null) {
                    animationDrawable.start();
                  }
                  //必须要有这个true返回
                  return true;
                }
              });
  }

  public static void scaleFav(View view) {
    if (view instanceof ImageView) {
      scaleFav((ImageView) view);
    }
  }

  public static void scaleFav(final ImageView ivStar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      ivStar.animate()
            .scaleY(1.25f)
            .scaleX(1.25f)
            .withEndAction(new Runnable() {
              @Override
              public void run() {
                ivStar.animate()
                      .scaleY(1.0f)
                      .scaleX(1.0f)
                      .start();
              }
            })
            .start();
    }
  }
}