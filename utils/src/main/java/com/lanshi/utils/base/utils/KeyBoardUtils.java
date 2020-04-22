package com.lanshi.utils.base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

//打开或关闭软键盘
public class KeyBoardUtils {

  private KeyBoardUtils() {
  }

  /**
   * 打开软键盘
   */
  public static void openKeybord(EditText mEditText, Context mContext) {
    InputMethodManager imm =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
  }

  /**
   * 关闭软键盘
   */
  public static void closeKeybord(EditText mEditText, Context mContext) {
    InputMethodManager imm =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

    imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
  }

  /**
   * 关闭软键盘
   */
  public static void closeKeybord(Context mContext, IBinder windowToken) {
    InputMethodManager imm =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

    if (imm != null) {
      imm.hideSoftInputFromWindow(windowToken, 0);
    }
  }

  public static void closeKeybord(View v, Context mContext) {
    InputMethodManager imm =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
  }
  public static void closeKeybord(Activity activity) {
    if (isOpen(activity)){
    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
    }
  }
  public static boolean isOpen(Activity activity){
    if(activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE){
     return  true;
    }else {
      return false;
    }
  }
  /**
   * show -> hide
   * hide -> show
   */
  public static void toggleKeybordAlways(Context context) {
    if (context == null) {
      return;
    }

    InputMethodManager inputMgr =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (inputMgr != null) {
      inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
    }
  }


}
